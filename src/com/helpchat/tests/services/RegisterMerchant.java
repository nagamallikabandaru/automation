package com.helpchat.tests.services;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.*;

//import org.apache.commons.io.FileUtils;
//import org.json.JSONObject;


public class RegisterMerchant {
	
	WebTarget target;
	Client client;
	Response response ;
	
	public void registerMerchant() throws IOException{

		client=ClientBuilder.newClient();
		target=client.target("https://api.helpchat.in/v1/merchants/me/login");
		Invocation.Builder invBuilder=target.request(MediaType.APPLICATION_JSON);
		File json=new File("D:/helpchat/merchantsapi/merchants-tests/src/test/resources/json");
//		String payload=FileUtils.readFileToString(json);
		/*JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("first_name", "Test");
		jsonObject.put("last_name", "Jersey");
		jsonObject.put("mobile", "9999999901");*/
//		response = invBuilder.post(Entity.json(payload));
		//System.out.println(response.getStatus());
	
	}
}
