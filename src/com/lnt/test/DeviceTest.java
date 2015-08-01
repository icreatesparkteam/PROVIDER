package com.lnt.test;

import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

//import com.lnt.core.common.dto.SmartDeviceDto;
//import com.lnt.core.common.dto.SmartDeviceDto;
//import com.lnt.core.common.dto.SmartDeviceDto;
//import com.lnt.core.common.util.IConstants;
import com.lnt.sp.common.dto.UserRegistrationDto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//Test Client

public class DeviceTest {
//	public static String UrlConstant = "http://52.27.53.182:8080/iControlE-ServiceProvider/rest/";
	public static String UrlConstant = "http://localhost:8080/iControlE-ServiceProvider/rest/";
	static Client client = Client.create();

	static String token;
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		loginUser();
//		createDevice(token);
		getDevice(token);
		getCluster(token);
		getManufacturer(token);

	}

	private static void getManufacturer(String token) throws JsonGenerationException,
	JsonMappingException, IOException {
		System.out.println("getDevice device method : ");

		WebResource webResource = client.resource(UrlConstant + "manufacturer/manufacturerlist");

		ObjectMapper mapper = new ObjectMapper();

		try{
			
			ClientResponse response = webResource.type("application/json")
					.header("lnt_access_token", token)
					.get(ClientResponse.class);
	
	
			System.out.println("Output from Server .... \n" + response.getStatus());
			String output1 = response.getEntity(String.class);
			System.out.println(output1);
		}catch(Exception e){
			System.out.println("Exception: "+e.toString());
			e.printStackTrace();
		}
	}
	
	private static void loginUser() throws JsonGenerationException,
			JsonMappingException, IOException {
		System.out.println("Login device method : ");
		MultivaluedMap<String, String> inputMap = new MultivaluedMapImpl();
		inputMap.add("username", "user1");
		inputMap.add("password", "Newuser@123");
		System.out.println("Login user method : inputMap " + inputMap);
		WebResource webResource = client.resource(UrlConstant + "auth/login");
		System.out.println("URL -  " + UrlConstant + "auth/devicelogin");
		ClientResponse response = webResource.type(
				MediaType.APPLICATION_FORM_URLENCODED).post(
				ClientResponse.class, inputMap);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		System.out.println("Output from Server .... \n" + response.getStatus());
		String output = response.getEntity(String.class);
		System.out.println("login method : token " + output);
		token = output;
	}
	
	private static void getGetway(String token) throws JsonGenerationException,
	JsonMappingException, IOException {
		System.out.println("getGetway device method : ");

		WebResource webResource = client.resource(UrlConstant + "gateway/getgatewaybyuser");

		ObjectMapper mapper = new ObjectMapper();

		try{
			
			ClientResponse response = webResource.type("application/json")
					.header("lnt_access_token", token)
					.header("gatewayid", "newgateway111")
					.get(ClientResponse.class);
	
//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatus());
//			}
	
			System.out.println("Output from Server .... \n" + response.getStatus());
			String output1 = response.getEntity(String.class);
			System.out.println(output1);
		}catch(Exception e){
			System.out.println("Exception: "+e.toString());
			e.printStackTrace();
		}
	}
	
	private static void getDevice(String token) throws JsonGenerationException,
	JsonMappingException, IOException {
		System.out.println("getDevice device method : ");

		WebResource webResource = client.resource(UrlConstant + "gateway/devicelist");

		ObjectMapper mapper = new ObjectMapper();

		try{
			
			ClientResponse response = webResource.type("application/json")
					.header("lnt_access_token", token)
					.header("gatewayid", "newgateway111")
					.get(ClientResponse.class);
	
//			if (response.getStatus() != 200) {
//				throw new RuntimeException("Failed : HTTP error code : "
//						+ response.getStatus());
//			}
	
			System.out.println("Output from Server .... \n" + response.getStatus());
			String output1 = response.getEntity(String.class);
			System.out.println(output1);
		}catch(Exception e){
			System.out.println("Exception: "+e.toString());
			e.printStackTrace();
		}
	}
	
	private static void getCluster(String token) throws JsonGenerationException,
	JsonMappingException, IOException {
		System.out.println("getDevice device method : ");

		WebResource webResource = client.resource(UrlConstant + "cluster/clusterlist");

		ObjectMapper mapper = new ObjectMapper();

		try{
			
			ClientResponse response = webResource.type("application/json")
					.header("lnt_access_token", token)
					.get(ClientResponse.class);
	
	
			System.out.println("Output from Server .... \n" + response.getStatus());
			String output1 = response.getEntity(String.class);
			System.out.println(output1);
		}catch(Exception e){
			System.out.println("Exception: "+e.toString());
			e.printStackTrace();
		}
	}

	private static void createDevice(String token)
			throws JsonGenerationException, JsonMappingException, IOException {
//		SmartDeviceDto reg = new SmartDeviceDto();
//		reg.setGatewayID(1);
//		reg.setDeviceID("0x0200");
//		reg.setManufacturerID("1");
//		reg.setDeviceStatus(1);
//		reg.setEndpoint("0x0104");
//		reg.setCluster("0x0006");
//		
//		WebResource webResource = client.resource(UrlConstant + "gateway/addsmartdevice");
//		ObjectMapper mapper = new ObjectMapper();
//		String inputData = mapper.writeValueAsString(reg);
//
//		// String
//		// inputJson="{\"userName\": \"TestUser9\",\"password\": \"1234\",\"name\": \"Murali1\", \"phoneNumber1\": \"0000000009\",\"phoneNumber2\": null,\"role\": 1,\"primaryEmailId\": \"murali.dhuli@gmail.com\",\"activationCode\": \"ABC9\",\"address\": \"Address1\"}";
//
//		// String
//		// callCenterInputJson="{\"userName\": \"TestUser11\",\"password\": \"1234\",\"name\": \"Murali1\",\"role\": 2,\"primaryEmailId\": \"murali.dhuli@gmail.com\",\"address\": \"Address1\"}";
//		System.out.println("URL: "+UrlConstant + "gateway/addsmartdevice");
//		System.out.println("inputData : " + inputData);
//		try{
//			
//			ClientResponse response = webResource.type("application/json")
//					.header("lnt_access_token", token)
//					.header("gatewayid", "newgateway111")
//					.post(ClientResponse.class, inputData);
//	
////			if (response.getStatus() != 200) {
////				throw new RuntimeException("Failed : HTTP error code : "
////						+ response.getStatus());
////			}
//	
//			System.out.println("Output from Server .... \n" + response.getStatus());
//			String output1 = response.getEntity(String.class);
//			System.out.println(output1);
//		}catch(Exception e){
//			System.out.println("Exception: "+e.toString());
//			e.printStackTrace();
//		}
	}

}
