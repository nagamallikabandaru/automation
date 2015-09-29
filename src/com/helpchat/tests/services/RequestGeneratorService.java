package com.helpchat.tests.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.helpchat.tests.util.PropertiesUtil;

@Service
public class RequestGeneratorService<U,V> {
	private static final String X_HELPCHAT_AUTH = "X-HELPCHAT-AUTH";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PropertiesUtil properties;
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<V> post(U payLoad,String url) throws Exception{
//		ResponseEntity<Address_> postResponse = null;
		ResponseEntity<V> postResponse = null;
		try{
			HttpHeaders requestHeaders=setHeaders();
//		    HttpHeaders requestHeaders=new HttpHeaders();
//		    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//		    requestHeaders.add(X_HELPCHAT_AUTH,properties.getProperty(X_HELPCHAT_AUTH));		    		   
		    HttpEntity<U> entity = new HttpEntity<U>(payLoad, requestHeaders);
		      
			RestTemplate restTemplate=new RestTemplate();
			restTemplate.setMessageConverters(getMessageConverters());
//			String url=properties.getProperty("consumers.service.url")+"addresses";
//			postResponse= restTemplate.exchange(url, HttpMethod.POST, entity, Address_.class);
			postResponse= (ResponseEntity<V>) restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
			return postResponse;

		}catch(RestClientException rce){
			logger.error("RestClientException occured:"+rce.getMessage());
			throw new Exception(rce.getMessage());
		}catch (Exception e){
			logger.error("Exception occured:"+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}
	
	@SuppressWarnings("unchecked")
	public ResponseEntity<V> get(HashMap<String, String> params) throws Exception{
//		ResponseEntity<Address_> postResponse = null;
		ResponseEntity<V> getResponse = null;
		try{
		    HttpHeaders requestHeaders=setHeaders();	    		   
		    HttpEntity<String> entity = new HttpEntity<String>("parameters", requestHeaders);		      
			RestTemplate restTemplate=new RestTemplate();
			restTemplate.setMessageConverters(getMessageConverters());
//			String url="http://172.16.1.113:8080/masterdata/v4/city/name/{0}";
			String url="http://172.16.1.113:8080/masterdata/v4/city/{id}";
			getResponse= (ResponseEntity<V>) restTemplate.exchange(url, HttpMethod.GET, entity, Object.class,params);
			return getResponse;

		}catch(RestClientException rce){
			logger.error("RestClientException occured:"+rce.getMessage());
			throw new Exception(rce.getMessage());
		}catch (Exception e){
			logger.error("Exception occured:"+e.getMessage());
			throw new Exception(e.getMessage());
		}		
	}	
	
	private HttpHeaders setHeaders() throws Exception{
		try{
		    HttpHeaders requestHeaders=new HttpHeaders();
		    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		    logger.info("X-Helpchat-Auth:"+properties.getProperty(X_HELPCHAT_AUTH));
		    requestHeaders.add(X_HELPCHAT_AUTH,properties.getProperty(X_HELPCHAT_AUTH));
		    return requestHeaders;
		}catch(Exception e){
			throw new Exception (e.getMessage());
		}
	}
	private static List<HttpMessageConverter<?>> getMessageConverters() {
	    List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	    converters.add(new MappingJackson2HttpMessageConverter());
	    return converters;
	}

}
