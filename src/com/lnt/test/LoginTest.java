package com.lnt.test;

import java.io.IOException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.lnt.core.common.dto.ServiceProviderRegistrationDto;
import com.lnt.core.common.util.ERole;
import com.lnt.core.common.util.IConstants;
import com.lnt.sp.common.dto.UserRegistrationDto;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

//Test Client

public class LoginTest {
	public static String UrlConstant = "http://52.27.53.182:8080/iControlE-ServiceProvider/rest/";
//	public static String UrlConstant = "http://localhost:8080/iControlE-ServiceProvider/rest/";
	static Client client = Client.create();

	static String token;
	public static void main(String[] args) throws JsonGenerationException,
			JsonMappingException, IOException {

		loginUser();
		createUser(token);
//		getServPro(token);
//		getRole(token);

	}

	
	private static void loginUser() throws JsonGenerationException,
			JsonMappingException, IOException {
		System.out.println("Login user method : ");
		MultivaluedMap<String, String> inputMap = new MultivaluedMapImpl();
		inputMap.add("username", "servpro1");
		inputMap.add("password", "Newuser@123");
		System.out.println("Login user method : inputMap " + inputMap);
		WebResource webResource = client.resource(UrlConstant + "auth/login");
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
	
	private static void createUser(String token)
			throws JsonGenerationException, JsonMappingException, IOException {
		
		UserRegistrationDto reg = new UserRegistrationDto();
		
		for(int i = 500; i < 550; i++)
		{
			reg.setUserName("user"+i);
			reg.setPassword("Newuser@123");
			reg.setAddress("Munnekolala 2,Banagalore");
			reg.setPhoneNum("66555775757");
			reg.setPrimaryEmailId("atanu.niyogi11@gmail.com");
			reg.setCity("Bangalore");
			reg.setState("KA");
			reg.setCountry("India");
			reg.setsQuestion("What is your name?");
			reg.setsAnswer("Atanu");
			reg.setZipCode(560037);
			
			WebResource webResource = client.resource(UrlConstant + "registration/create");
			ObjectMapper mapper = new ObjectMapper();
			String inputData = mapper.writeValueAsString(reg);
			// String
			// inputJson="{\"userName\": \"TestUser9\",\"password\": \"1234\",\"name\": \"Murali1\", \"phoneNumber1\": \"0000000009\",\"phoneNumber2\": null,\"role\": 1,\"primaryEmailId\": \"murali.dhuli@gmail.com\",\"activationCode\": \"ABC9\",\"address\": \"Address1\"}";
	
			// String
			// callCenterInputJson="{\"userName\": \"TestUser11\",\"password\": \"1234\",\"name\": \"Murali1\",\"role\": 2,\"primaryEmailId\": \"murali.dhuli@gmail.com\",\"address\": \"Address1\"}";
			System.out.println("URL: "+UrlConstant + "registration/create");
			System.out.println("inputData : " + inputData);
			try{
				
				ClientResponse response = webResource.type("application/json")
						.header(IConstants.TOKEN_HEADER_KEY, token)
						.post(ClientResponse.class, inputData);
		
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
	}

	private static void getServPro(String token) throws JsonGenerationException,
	JsonMappingException, IOException {
		System.out.println("getGetway device method : ");

		WebResource webResource = client.resource(UrlConstant + "registration/serviceproviderlist");

		ObjectMapper mapper = new ObjectMapper();

		try{
			
			ClientResponse response = webResource.type("application/json")
					.header("lnt_access_token", token)
//					.header("gatewayid", "0x00:0x12:0x4B:0x00:0x01:0xDD:0x7B:0xBE")
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
	
	private static void getRole(String token) throws JsonGenerationException,
	JsonMappingException, IOException {
		System.out.println("getGetway device method : ");

		WebResource webResource = client.resource(UrlConstant + "registration/getrole");

		ObjectMapper mapper = new ObjectMapper();

		try{
			
			ClientResponse response = webResource.type("application/json")
					.header("lnt_access_token", token)
//					.header("gatewayid", "0x00:0x12:0x4B:0x00:0x01:0xDD:0x7B:0xBE")
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

}
