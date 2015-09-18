package com.helpchat.tests.services;

/*
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
*/
import java.util.Arrays;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import com.helpchat.tests.entities.Merchants;

public class TestMerchantApi {
	
	
    public static void main(String args[]){
        
    	testRegisterMerchant();
        System.out.println("*****");

    }
	public static void testRegisterMerchant(){
		
	    final String url="http://172.16.1.113:8080/merchantsapi/v1/merchants/me/login";
/*
	    MultiMap headers = new MultiHashMap();
	    headers.put("Content-Type",MediaType.APPLICATION_JSON);
	    headers.put("Accept",MediaType.APPLICATION_JSON);
	    headers.put("X-HELPCHAT-AUTH", 
	    		"eyJ1c2VyX25hbWUiOm51bGwsImlkIjozNjU3MDEsIm1vYmlsZSI6Ijc3OTU1NTY4OTMiLCJleHBpcmVzIjoxNzU0NTU4MDEwNjE3fQ==.fELHPFf2UP3ySNvkP4/u5a5Yg8ZNm0j2VILvWJRBi18=");
	*/    		
	    		
	    HttpHeaders requestHeaders=new HttpHeaders();
	    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	    requestHeaders.add("X-HELPCHAT-AUTH", 
	    		"eyJ1c2VyX25hbWUiOm51bGwsImlkIjozNjU3MDEsIm1vYmlsZSI6Ijc3OTU1NTY4OTMiLCJleHBpcmVzIjoxNzU0NTU4MDEwNjE3fQ==.fELHPFf2UP3ySNvkP4/u5a5Yg8ZNm0j2VILvWJRBi18=");

	    
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", requestHeaders);
		RestTemplate restTemplate=new RestTemplate();
		
		Merchants merchant=new Merchants();
		merchant.setFirstName("mallika");
		merchant.setLastName("mallika");
		merchant.setMobile("9989881634");
		merchant.setEmail("test@gmail.com");
		

		ResponseEntity<Merchants> response= restTemplate.exchange(url, HttpMethod.POST, entity, Merchants.class);
//		Merchants response = restTemplate.postForObject("http://172.16.1.113:8080/merchantsapi/v1/merchants/me/login",merchant, Merchants.class);
		
		System.out.println("Response"+response);
	}

}

/*
protected String getHTTPconection(String endpoint, String method, Map<String,String> headersMap){
	
	
	URL url = new URL(endpoint);
    HttpURLConnection myURLConnection = (HttpURLConnection) url.openConnection();
    myURLConnection.setRequestMethod(method);
    for(String header: headersMap.keySet()){
    	
    }
    
    InputStream ordersStream = myURLConnection.getInputStream();
    BufferedReader in = new BufferedReader(new InputStreamReader(ordersStream));
    StringBuilder responseStrBuilder = new StringBuilder();
    String inputStr;
    while ((inputStr = in.readLine()) != null) {
        responseStrBuilder.append(inputStr);
    }
        return responseStrBuilder.toString();
}
*/
