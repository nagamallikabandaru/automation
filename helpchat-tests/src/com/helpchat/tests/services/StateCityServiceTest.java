package com.helpchat.tests.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.helpchat.tests.entities.CityMaster;
import com.helpchat.tests.masterdata.repository.CityRepositoryTest;


@Service
public class StateCityServiceTest {
	
//    public static void main(String args[]){
//        
//    	testStateCityService();
//        System.out.println("*****");
//
//    }
	@Autowired
//	CityRepositoryTest cityRepositoryTest;
	ApplicationContext context;
	
	public CityMaster findCityByName(String cityName) throws Exception {
		CityRepositoryTest cityRepositoryTest= context.getBean(CityRepositoryTest.class);
		return cityRepositoryTest.findByCityName(cityName);
	}
    
	public Object apifindCityByName(String name){
		
	    final String url="http://172.16.1.113:8080/masterdata/v4/city/name/{0}"; 
	    ResponseEntity<CityMaster> response = null;
	    HttpHeaders requestHeaders=new HttpHeaders();
	    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
	    requestHeaders.add("X-HELPCHAT-AUTH", 
	    		"eyJ1c2VyX25hbWUiOm51bGwsImlkIjozNjU3MDEsIm1vYmlsZSI6Ijc3OTU1NTY4OTMiLCJleHBpcmVzIjoxNzU0NTU4MDEwNjE3fQ==.fELHPFf2UP3ySNvkP4/u5a5Yg8ZNm0j2VILvWJRBi18=");
	    
	    HttpEntity<String> entity = new HttpEntity<String>("parameters", requestHeaders);
		RestTemplate restTemplate=new RestTemplate();
		restTemplate.setMessageConverters(getMessageConverters());
		CityMaster cityMaterResp = null;
		//restTemplate.g
		String params=name;	
		try{
			response= restTemplate.exchange(url, HttpMethod.GET, entity, CityMaster.class,params);
			cityMaterResp=response.getBody();
//			System.out.println("Response"+cityMaterResp.toString());
			System.out.println("Response:"+cityMaterResp.getCityName());
			System.out.println("Response:"+cityMaterResp.getId());
		}catch(RestClientException rce){
			rce.printStackTrace();
		}
		return cityMaterResp;
	}
	
	private static List<HttpMessageConverter<?>> getMessageConverters() {
	    List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	    converters.add(new MappingJackson2HttpMessageConverter());
	    return converters;
	}

}
