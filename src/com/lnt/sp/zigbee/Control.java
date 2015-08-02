/**************************************************************************************************
  Filename:       Main.java
  Revised:        $$
  Revision:       $$

  Description:    Main

  Copyright (C) {2014} Texas Instruments Incorporated - http://www.ti.com/


   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions
   are met:

     Redistributions of source code must retain the above copyright
     notice, this list of conditions and the following disclaimer.

     Redistributions in binary form must reproduce the above copyright
     notice, this list of conditions and the following disclaimer in the
     documentation and/or other materials provided with the
     distribution.

     Neither the name of Texas Instruments Incorporated nor the names of
     its contributors may be used to endorse or promote products derived
     from this software without specific prior written permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
   OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
   SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
   LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
   DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
   THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
   OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 
**************************************************************************************************/

package com.lnt.sp.zigbee;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

import com.google.protobuf.ByteString;
import com.hagdriver.HagatewayClient;
import com.hagdriver.NwkMngrClient;
import com.hagdriver.HagatewayClient.HagatewayZclCallback;
import com.hagdriver.NwkMngrClient.NwkMngrCallbacks;
import com.hagdriver.hagatewayPb.hagatewayPb.gwOnOffState_t;
import com.hagdriver.hagatewayPb.hagatewayPb.gwStatus_t;
import com.hagdriver.hagatewayPb.hagatewayPb.gwThermostatSetpointMode_t;
import com.hagdriver.hagatewayPb.hagatewayPb.gwZclAttributeDataTypes_t;
import com.hagdriver.nwkmngrPb.nwkmgrPb.nwkDeviceInfo_t;
import com.hagdriver.nwkmngrPb.nwkmgrPb.nwkDeviceStatus_t;
import com.hagdriver.nwkmngrPb.nwkmgrPb.nwkSimpleDescriptor_t;

public class Control {
	
	private static HagatewayClient haGatewayClient;
	private static NwkMngrClient nwkmgrClinet;						
	
	private static String attrTypesString =
			"1 � Boolean\n" +
			"2 � uint8\n" +
			"3 � uint16\n" +
			"4 � uint32\n" +
			"5 � int8\n" +
			"6 � int16\n" +
			"7 � int32\n";
	
	private static final int ATTR_TYPE_BOOLEAN = 1;
	private static final int ATTR_TYPE_UINT8 = 2;
	private static final int ATTR_TYPE_UINT16 = 3;
	private static final int ATTR_TYPE_UINT32 = 4;
	private static final int ATTR_TYPE_INT8 = 5;
	private static final int ATTR_TYPE_INT16 = 6;
	private static final int ATTR_TYPE_INT32 = 7;
			
	public void openConnection(String srvrIp) throws Exception {					

		//Set IP addr of nwk and hagw clients
        haGatewayClient = new HagatewayClient(srvrIp);
        nwkmgrClinet = new NwkMngrClient(srvrIp);        
        
		nwkmgrClinet.nwkMngrCallbacks = new NwkMngrCallbacks() {			
			@Override
			public void nwkMngrUpdateDeviceList(final List<nwkDeviceInfo_t> list) {
				nwkMngrUpdateDeviceListCb(list);
			}		
			@Override
			public void nwkMngrUpdateDevice(nwkDeviceInfo_t device) {
				nwkMngrupdateDeviceCb(device);				
			}
		};
		
        boolean clientsConnected = false;
        //waiting for connection
        while(!clientsConnected)
        {
        	if(nwkmgrClinet.isConnected() && haGatewayClient.isConnected())
        	{
        		clientsConnected = true;
        	}
        	else
        	{
        		Thread.sleep(500);
        	}        	
        }
       
        return;
	}
	
	
	
/************************************
 * CMD Processing functions: The command line class will call these call backs 
 * to act on the command line command and parameters entered by the user	
 */
//	static CommandLineCmdIf clNwkPermitJoinReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {
//			int paramIdx = 0;
//			
//			int openType = cl.getIntParam(params.get(paramIdx++).paramValue);
//			int openDuration = cl.getIntParam(params.get(paramIdx++).paramValue);
//			
//			nwkmgrClinet.nwkMngrOpenNetworkReq(openType, openDuration);				
//		}			
//	};
//	
//	static CommandLineCmdIf clNwkGetDevListReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			nwkmgrClinet.nwkMngrDevListReq();			
//		}			
//	};	
		
	public void changeDeviceStatus(String devIeee, String ep, String bState) {			
			
			long devIeeeAdd = Integer.parseInt(devIeee);
			int endPoint = Integer.parseInt(ep);
			byte stateByte = Byte.parseByte(bState);
			
			final gwOnOffState_t state;
			if(stateByte == 0)
			{
				state = gwOnOffState_t.OFF_STATE;
			}
			else
			{
				state = gwOnOffState_t.ON_STATE;
			}
			
			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){

				@Override
				public void zclGenericGetSetRspInd(byte[] data,
						gwStatus_t status) {			
					
				}				
			};
			
			int cnf = haGatewayClient.hagwSetDevStateReq(devIeeeAdd, endPoint, state, 
					zclCallback);
					
			if(cnf != -1)
			{
				
			}
			else
			{
				
			}
		}			
	
	
	
//	static CommandLineCmdIf clHagwGetStateReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//						
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.print("DEV_GET_ONOFF_STATE_REQ: Status " + status.toString());
//					if(data.length > 0)
//					{
//						cl.print(" - State =  " + data[0]);
//					}
//					cl.print("\n");
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwGetDevStateReq(devIeee, ep, 
//					zclCallback);
//					
//			if(cnf != -1)
//			{
//				cl.printLn("DEV_SET_ONOFF_STATE_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("DEV_SET_ONOFF_STATE_REQ send failed");
//			}
//		}			
//	};
//	
//	
//	static CommandLineCmdIf clHagwSetLevelReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//			int level = cl.getIntParam(params.get(paramIdx++).paramValue);
//			
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.printLn("DEV_SET_LEVEL_REQ: " + status.toString());					
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwSetDevLevelReq(devIeee, ep, level, 
//					zclCallback);
//					
//			if(cnf != -1)
//			{
//				cl.printLn("DEV_SET_LEVEL_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("DEV_SET_LEVEL_REQ send failed");
//			}
//		}			
//	};
//	
//	static CommandLineCmdIf clHagwGetLevelReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//						
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.print("DEV_GET_LEVEL_REQ: Status " + status.toString());
//					if(status == gwStatus_t.STATUS_SUCCESS)
//					{
//						cl.print(" - level =  " + HagatewayClient.byteArrayToUint(data));
//					}
//					cl.print("\n");
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwGetDevLevelReq(devIeee, ep, 
//					zclCallback);
//					
//			if(cnf != -1)
//			{
//				cl.printLn("DEV_SET_LEVEL_STATE_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("DEV_SET_LEVEL_STATE_REQ send failed");
//			}
//		}			
//	};
//
//	static CommandLineCmdIf clHagwSetColorReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//			int hue = cl.getIntParam(params.get(paramIdx++).paramValue);
//			int sat = cl.getIntParam(params.get(paramIdx++).paramValue);
// 
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.printLn("DEV_SET_COLOR_REQ: " + status.toString());					
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwSetDevColorReq(devIeee, ep, hue, sat, 
//					zclCallback);
//					
//			if(cnf != -1)
//			{
//				cl.printLn("DEV_SET_COLOR_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("DEV_SET_COLOR_REQ send failed");
//			}
//		}			
//	};
//	
//	static CommandLineCmdIf clHagwGetColorReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//						
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.print("DEV_GET_COLOR_REQ: Status " + status.toString());
//					if(status == gwStatus_t.STATUS_SUCCESS)
//					{						
//						cl.print(" - Hue =  " + (data[0] & 0xFFL) + ", ");
//						cl.print(" - Saturation =  " + (data[1] & 0xFFL));
//					}
//					cl.print("\n");
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwGetDevColorReq(devIeee, ep, 
//					zclCallback);
//					
//			if(cnf != -1)
//			{
//				cl.printLn("DEV_GET_COLOR_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("DEV_GET_COLOR_REQ send failed");
//			}
//		}			
//	};
//
//	static CommandLineCmdIf clHagwGetTempReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//						
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.print("DEV_GET_TEMP_REQ: Status " + status.toString());
//					if(status == gwStatus_t.STATUS_SUCCESS)
//					{						
//						cl.print(" - Temperature =  " + HagatewayClient.byteArrayToUint(data));
//					}
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwGetDevTempReq(devIeee, ep, 
//					zclCallback);
//					
//			if(cnf != -1)
//			{
//				cl.printLn("DEV_SET_TEMP_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("DEV_SET_TEMP_REQ send failed");
//			}
//		}			
//	};
//	
//	static CommandLineCmdIf clHagwSetSetPointReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//			int setPointChange = cl.getShortParam(params.get(paramIdx++).paramValue);
//			int iMode = cl.getIntParam(params.get(paramIdx++).paramValue);
//			gwThermostatSetpointMode_t mode;
//			
//			if(iMode == 1)
//			{
//				mode = gwThermostatSetpointMode_t.HEAT_SETPOINT;
//			}
//			else if(iMode == 2)
//			{
//				mode = gwThermostatSetpointMode_t.COOL_SETPOINT;
//			}
//			else
//			{
//				mode = gwThermostatSetpointMode_t.BOTH_SETPOINTS;
//			}								
//			
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.printLn("DEV_THERMOSTAT_SETPOINT_CHANGE_REQ: " + status.toString());					
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwSetPointChangeReq(devIeee, ep, 
//					setPointChange, mode, zclCallback);
//					
//			if(cnf != -1)
//			{
//				cl.printLn("DEV_THERMOSTAT_SETPOINT_CHANGE_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("DEV_THERMOSTAT_SETPOINT_CHANGE_REQ send failed");
//			}
//		}			
//	};	
//	
//	static CommandLineCmdIf clHagwReadAttrReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//			short attrReqClusterId = cl.getShortParam(params.get(paramIdx++).paramValue);
//			short attrReqAttrId = cl.getShortParam(params.get(paramIdx++).paramValue);			
//			final int attrType  = cl.getShortParam(params.get(paramIdx++).paramValue);	
//			
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.printLn("GW_READ_DEVICE_ATTRIBUTE_RSP: " + status.toString());
//					
//					if(status == gwStatus_t.STATUS_SUCCESS)
//					{
//						cl.printLn("Attr Value:");
//						
//						switch(attrType)
//						{
//						case ATTR_TYPE_BOOLEAN:
//							boolean bAttr = HagatewayClient.byteToBool(data[0]); 
//							cl.printLn(Boolean.toString(bAttr));
//							break;
//						case ATTR_TYPE_INT8:
//							//no conversion needed, correct type.
//							if(data.length > 0)
//							{
//								byte byteAttr = data[0];
//								cl.printLn(Byte.toString(byteAttr));
//							}							
//							break;
//						case ATTR_TYPE_INT16:
//							short int16Attr = HagatewayClient.byteArrayToInt16(data);
//							//print to console
//							cl.printLn(Short.toString(int16Attr));
//							break;
//						case ATTR_TYPE_INT32:
//							int int32Attr = HagatewayClient.byteArrayToInt32(data);
//							//print to console
//							cl.printLn(Integer.toString(int32Attr));
//							break;
//						case ATTR_TYPE_UINT8:
//						case ATTR_TYPE_UINT16:
//						case ATTR_TYPE_UINT32:
//							//make container lager than an int to so it is always unsigned
//							long uIntAttr = HagatewayClient.byteArrayToUint(data);									
//							//print to console
//							cl.printLn(Long.toString(uIntAttr));
//							break;							
//						default:
//							//unknown type
//							cl.printLn("Attr type not supported");
//							break;									
//						}
//					}					
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwReadAttrReq(devIeee, ep, attrReqClusterId, 
//					attrReqAttrId, zclCallback);
//			
//			if(cnf != -1)
//			{
//				cl.printLn("GW_READ_DEVICE_ATTRIBUTE_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("GW_READ_DEVICE_ATTRIBUTE_REQ send failed");
//			}
//		}			
//	};
//	
//	static CommandLineCmdIf clHagwWriteAttrReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//			short attrReqClusterId = cl.getShortParam(params.get(paramIdx++).paramValue);
//			short attrReqAttrId = cl.getShortParam(params.get(paramIdx++).paramValue);			
//			int attrType  = cl.getShortParam(params.get(paramIdx++).paramValue);
//			ByteString attrData;
//			gwZclAttributeDataTypes_t attrDataType;			
//			
//			switch(attrType)
//			{
//			case ATTR_TYPE_BOOLEAN:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_BOOLEAN;
//				attrData = cl.getByteStringParam(params.get(paramIdx++).paramValue, 1);
//				break;
//			case ATTR_TYPE_INT8:
//			case ATTR_TYPE_UINT8:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_INT8;
//				Byte byteAttrData = (byte) cl.getIntParam(params.get(paramIdx++).paramValue);		    
//			    attrData = HagatewayClient.int8ToByteString(byteAttrData);
//				break;
//			case ATTR_TYPE_INT16:
//			case ATTR_TYPE_UINT16:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_INT16;
//				Short i16AttrData = cl.getShortParam(params.get(paramIdx++).paramValue);	
//			    attrData = HagatewayClient.int16ToByteString(i16AttrData);			    
//				break;
//			default:
//				cl.print("unknown data type, defaulting to int\n");				
//			case ATTR_TYPE_INT32:
//			case ATTR_TYPE_UINT32:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_INT32;
//				Integer i32AttrData = cl.getIntParam(params.get(paramIdx++).paramValue);
//			    attrData = HagatewayClient.int32ToByteString(i32AttrData);
//				break;
//			}
//
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.printLn("GW_WRITE_DEVICE_ATTRIBUTE_RSP: " + status.toString());					
//					}													
//			};
//									
//			int cnf = haGatewayClient.hagwWriteAttrReq(devIeee, ep, attrReqClusterId, 
//					attrReqAttrId, attrData, attrDataType, zclCallback);
//			
//			if(cnf != -1)
//			{
//				cl.printLn("GW_WRITE_DEVICE_ATTRIBUTE_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("GW_WRITE_DEVICE_ATTRIBUTE_REQ send failed");
//			}
//		}			
//	};
//
//	
//	static CommandLineCmdIf clHagwReadMspAttrReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//			int SrcEp = cl.getByteParam(params.get(paramIdx++).paramValue);
//			int manuCode = cl.getByteParam(params.get(paramIdx++).paramValue);
//			short attrReqMspProfileId = cl.getShortParam(params.get(paramIdx++).paramValue);
//			short attrReqClusterId = cl.getShortParam(params.get(paramIdx++).paramValue);
//			short attrReqAttrId = cl.getShortParam(params.get(paramIdx++).paramValue);			
//			final int attrType  = cl.getShortParam(params.get(paramIdx++).paramValue);	
//			
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.printLn("GW_READ_DEVICE_ATTRIBUTE_RSP: " + status.toString());
//					
//					if(status == gwStatus_t.STATUS_SUCCESS)
//					{
//						cl.printLn("Attr Value:");
//						
//						switch(attrType)
//						{
//						case ATTR_TYPE_BOOLEAN:
//							//convert data to a boolean
//							Boolean attrBool = ((data[0] & 0x1) != 0);
//							cl.printLn(attrBool.toString());
//							break;
//						case ATTR_TYPE_INT8:
//							//no conversion needed, correct type.
//							byte byteAttr = data[0];
//							cl.printLn(Byte.toString(byteAttr));
//							break;
//						case ATTR_TYPE_INT16:
//							//copy data into byte buffer
//							ByteBuffer int16ByteBuff = ByteBuffer.allocate(2);
//							int16ByteBuff.order(ByteOrder.LITTLE_ENDIAN);
//							int16ByteBuff.put(data[0]);
//							int16ByteBuff.put(data[1]);
//							//extract the short
//							short int16Attr = int16ByteBuff.getShort(0);
//							//print to console
//							cl.printLn(Short.toString(int16Attr));
//							break;
//						case ATTR_TYPE_INT32:
//							//copy data into byte buffer
//							ByteBuffer int32ByteBuff = ByteBuffer.allocate(4);
//							int32ByteBuff.order(ByteOrder.LITTLE_ENDIAN);
//							int32ByteBuff.put(data[0]);
//							int32ByteBuff.put(data[1]);
//							int32ByteBuff.put(data[3]);
//							int32ByteBuff.put(data[4]);
//							//extract the int
//							int int32Attr = int32ByteBuff.getInt(0);
//							//print to console
//							cl.printLn(Integer.toString(int32Attr));
//							break;
//						case ATTR_TYPE_UINT8:
//						case ATTR_TYPE_UINT16:
//						case ATTR_TYPE_UINT32:
//							//make container lager than an int to so it is always unsigned
//							long uIntAttr = 0;
//							for(int dataIdx=0; dataIdx < data.length; dataIdx++)
//							{
//								uIntAttr+= ((data[dataIdx] & 0xFF)<<(dataIdx*8)); 
//							}
//							//remove sign from int
//							uIntAttr &= 0xFFFFFFFFL;
//									
//							//print unsigned short
//							cl.printLn(Long.toString(uIntAttr));
//							break;							
//						default:
//							//unknown type
//							cl.printLn("Attr type not supported");
//							break;									
//						}
//					}					
//				}				
//			};
//			
//			int cnf = haGatewayClient.hagwReadManuSpecAttrReq(devIeee, ep, SrcEp, manuCode, 
//					attrReqMspProfileId, attrReqClusterId, attrReqAttrId, zclCallback);				
//			
//			if(cnf != -1)
//			{
//				cl.printLn("GW_READ_DEVICE_ATTRIBUTE_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("GW_READ_DEVICE_ATTRIBUTE_REQ send failed");
//			}
//		}			
//	};
//	
//	static CommandLineCmdIf clHagwWriteMspAttrReqCmdIf = new CommandLineCmdIf(){
//		@Override
//		public void cmdCb(List<CommandLineParam> params) {			
//			int paramIdx = 0;
//			long devIeee = cl.getIeeeParam(params.get(paramIdx++).paramValue);
//			int ep = cl.getByteParam(params.get(paramIdx++).paramValue);
//			int srcEp = cl.getByteParam(params.get(paramIdx++).paramValue);
//			int manuCode = cl.getByteParam(params.get(paramIdx++).paramValue);
//			short attrReqMspProfileId = cl.getShortParam(params.get(paramIdx++).paramValue);
//			short attrReqClusterId = cl.getShortParam(params.get(paramIdx++).paramValue);
//			short attrReqAttrId = cl.getShortParam(params.get(paramIdx++).paramValue);			
//			int attrType  = cl.getShortParam(params.get(paramIdx++).paramValue);
//			ByteString attrData;
//			byte[] byteArrayAttr;
//			gwZclAttributeDataTypes_t attrDataType;
//			
//			
//			switch(attrType)
//			{
//			case ATTR_TYPE_BOOLEAN:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_BOOLEAN;
//				attrData = cl.getByteStringParam(params.get(paramIdx++).paramValue, 1);
//				break;
//			case ATTR_TYPE_INT8:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_INT8;
//				Byte byteAttrData = (byte) cl.getIntParam(params.get(paramIdx++).paramValue);
//			    byteArrayAttr = new byte[1];
//			    byteArrayAttr[0] = (byte) (byteAttrData & 0xff);
//			    attrData = ByteString.copyFrom(byteArrayAttr);
//				break;
//			case ATTR_TYPE_INT16:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_INT16;
//				Short shortAttrData = cl.getShortParam(params.get(paramIdx++).paramValue);
//			    byteArrayAttr = new byte[2];
//			    byteArrayAttr[0] = (byte) (shortAttrData & 0xff);
//			    byteArrayAttr[1] = (byte) ((shortAttrData >>> 8) & 0xff);
//			    attrData = ByteString.copyFrom(byteArrayAttr);
//				break;
//			default:
//				cl.print("unknown data type, defaulting to int\n");				
//			case ATTR_TYPE_INT32:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_INT32;
//				Integer inttAttrData = cl.getIntParam(params.get(paramIdx++).paramValue);
//			    byteArrayAttr = new byte[4];
//			    byteArrayAttr[0] = (byte) (inttAttrData & 0xff);
//			    byteArrayAttr[1] = (byte) ((inttAttrData >>> 8) & 0xff);
//			    byteArrayAttr[2] = (byte) ((inttAttrData >>> 16) & 0xff);
//			    byteArrayAttr[3] = (byte) ((inttAttrData >>> 24) & 0xff);
//			    attrData = ByteString.copyFrom(byteArrayAttr);
//				break;
//			case ATTR_TYPE_UINT8:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_UINT8;
//				byte ubyteAttrData = (byte) (cl.getShortParam(params.get(paramIdx++).paramValue) & 0xFF);
//			    byteArrayAttr = new byte[1];
//			    byteArrayAttr[0] = (byte) (ubyteAttrData & 0xff);
//			    attrData = ByteString.copyFrom(byteArrayAttr);
//				break;
//			case ATTR_TYPE_UINT16:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_UINT16;
//				Short ushortAttrData = (short) (cl.getShortParam(params.get(paramIdx++).paramValue) & 0xFFFF);
//			    byteArrayAttr = new byte[2];
//			    byteArrayAttr[0] = (byte) (ushortAttrData & 0xff);
//			    byteArrayAttr[1] = (byte) ((ushortAttrData >>> 8) & 0xff);
//			    attrData = ByteString.copyFrom(byteArrayAttr);
//				break;
//			case ATTR_TYPE_UINT32:
//				attrDataType = gwZclAttributeDataTypes_t.ZCL_DATATYPE_UINT32;
//				Integer uinttAttrData = (cl.getIntParam(params.get(paramIdx++).paramValue) & 0xFFFFFFFF);
//			    byteArrayAttr = new byte[4];
//			    byteArrayAttr[0] = (byte) (uinttAttrData & 0xff);
//			    byteArrayAttr[1] = (byte) ((uinttAttrData >>> 8) & 0xff);
//			    byteArrayAttr[2] = (byte) ((uinttAttrData >>> 16) & 0xff);
//			    byteArrayAttr[3] = (byte) ((uinttAttrData >>> 24) & 0xff);
//			    attrData = ByteString.copyFrom(byteArrayAttr);
//				break;			
//			}
//
//			HagatewayZclCallback zclCallback = new HagatewayZclCallback(){
//
//				@Override
//				public void zclGenericGetSetRspInd(byte[] data,
//						gwStatus_t status) {			
//					
//					cl.printLn("GW_WRITE_DEVICE_ATTRIBUTE_RSP: " + status.toString());					
//					}													
//			};
//									
//
//			int cnf = haGatewayClient.hagwWriteManuSpecAttrReq(devIeee, ep, srcEp, manuCode, 
//					attrReqMspProfileId, attrReqClusterId, attrReqAttrId, attrData, attrDataType, zclCallback);				
//			
//			if(cnf != -1)
//			{
//				cl.printLn("GW_WRITE_DEVICE_ATTRIBUTE_REQ sent[" + cnf + "]");
//			}
//			else
//			{
//				cl.printLn("GW_WRITE_DEVICE_ATTRIBUTE_REQ send failed");
//			}
//		}			
//	};
//		
///************************************
//* Nwk Manager Callbacks	
//*/	
//	
	static void nwkMngrUpdateDeviceListCb(final List<nwkDeviceInfo_t> devList){
		
		for(int devListIdx = 0; devListIdx < devList.size(); devListIdx++)
		{			
			//printf ieee colon seperated hex bytes 
			byte[] ieeeAddr = ByteBuffer.allocate(8).putLong(devList.get(devListIdx).getIeeeAddress()).array();

		
			for(int epIdx = 0; epIdx < devList.get(devListIdx).getSimpleDescListList().size(); epIdx++)
			{
				nwkSimpleDescriptor_t ep = devList.get(devListIdx).getSimpleDescList(epIdx);
			
			}	
		}
	}
//	
	static void nwkMngrupdateDeviceCb(nwkDeviceInfo_t device) {
	
		//print ieee colon separated hex bytes with
		byte[] ieeeAddr = ByteBuffer.allocate(8).putLong(device.getIeeeAddress()).array();
		
		if(device.getDeviceStatus() == nwkDeviceStatus_t.DEVICE_ON_LINE)
		{
			//new device print its details
						
			for(int epIdx = 0; epIdx < device.getSimpleDescListList().size(); epIdx++)
			{
				nwkSimpleDescriptor_t ep = device.getSimpleDescList(epIdx);
			}	
		}
	}
}