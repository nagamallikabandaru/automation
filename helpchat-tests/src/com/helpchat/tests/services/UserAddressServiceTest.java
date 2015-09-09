package com.helpchat.tests.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.helpchat.consumers.model.Address_;
import com.helpchat.tests.chat.dao.UserAddressRepository;
import com.helpchat.tests.entities.UserAddress;
import com.helpchat.tests.testdata.entities.AddressApi;
import com.helpchat.tests.util.PropertiesUtil;

@Service
public class UserAddressServiceTest {

	private static final String X_HELPCHAT_AUTH = "X-HELPCHAT-AUTH";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private UserAddressRepository userAddressRepository;
	
	@Autowired
	private PropertiesUtil properties;
	
	
//	@Autowired
//	private AddressApi addressApi;
	
	public  Address_ getUserAddress(Long userId,Long addressId) throws Exception {
		//CityRepositoryTest cityRepositoryTest= context.getBean(CityRepositoryTest.class);
		UserAddress addressEntity=userAddressRepository.findOne(addressId);
		if (addressEntity == null || addressEntity.isDeleted()) {
			// TODO throw exception or return null????
			return null;
		}
		// check if address belongs to user
		if (!userId.equals(addressEntity.getUserId())) {
			logger.info("address id:" + addressId + " does not belong to userid:" + userId);
			// TODO throw exception or return null????
			return null;
		}
		return mapToAddress(addressEntity);
	}
    
	public ResponseEntity<Address_> postUserAddress(Address_ consumerAddress) throws Exception{
//		HashMap<String, ResponseEntity<Address_>>
		ResponseEntity<Address_> postResponse = null;
		HashMap<String,ResponseEntity<Address_>> responseMap=new HashMap<String, ResponseEntity<Address_>>();
		try{
		    HttpHeaders requestHeaders=new HttpHeaders();
		    requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		    requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		    requestHeaders.add(X_HELPCHAT_AUTH,properties.getProperty(X_HELPCHAT_AUTH));		    		   
//		    HttpEntity<AddressApi> entity = new HttpEntity<Address_>(consumerAddress, requestHeaders);
		    HttpEntity<Address_> entity = new HttpEntity<Address_>(consumerAddress, requestHeaders);
		    
			RestTemplate restTemplate=new RestTemplate();
			restTemplate.setMessageConverters(getMessageConverters());
		
			String url=properties.getProperty("consumers.service.url")+"addresses";
			logger.info("API Url to run:"+url);
			postResponse= restTemplate.exchange(url, HttpMethod.POST, entity, Address_.class);
			logger.info("Response from API:"+postResponse.getStatusCode());
			logger.info("Address ID created"+postResponse.getBody().getId());
//			responseMap.put(postResponse.getStatusCode().toString(), postResponse);
			return postResponse;
		/*	if (postResponse.getStatusCode() == HttpStatus.OK) {
				return "200";				
			} else if (postResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				return "401";
			} else if (postResponse.getStatusCode() == HttpStatus.BAD_REQUEST){
				return "400";
			}*/
		}catch(RestClientException rce){
			logger.error("RestClientException occured:"+rce.getMessage());
			throw new Exception(rce.getMessage());
//			responseMap.put(rce.getMessage(), postResponse);
//			return responseMap;			
//			throw new RuntimeException("RestClientException", rce);
		}catch (Exception e){
			logger.error("Exception occured:"+e.getMessage());
			throw new Exception(e.getMessage());
//			responseMap.put(e.getMessage(), postResponse);
//			return responseMap;
		}		
	}
	
	
	
	private static List<HttpMessageConverter<?>> getMessageConverters() {
	    List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
	    converters.add(new MappingJackson2HttpMessageConverter());
	    return converters;
	}

	
	private Address_ mapToAddress(UserAddress addressEntity) {
		try{
		Address_ consumerAddress = new Address_();
		String stateNameById = getStateNameById(addressEntity.getStateId());

		JSONObject cityNameById = getCityInfoById(addressEntity.getCityId());
		if(cityNameById!=null){
			consumerAddress.setCity(cityNameById.getString("cityName"));
			if(cityNameById.has("state") && stateNameById==null){
				stateNameById = cityNameById.getJSONObject("state").getString("stateName");
			}
		}else{
			consumerAddress.setCity(addressEntity.getCityAlias());
		}
		
		consumerAddress.setState(stateNameById);
		consumerAddress.setContactNumber(addressEntity.getMobile());
		// TODO : hard coded for now
		consumerAddress.setCountry("India");
		consumerAddress.setDefault(addressEntity.getIsDefault());
		consumerAddress.setFirstName(addressEntity.getFirstName());
		consumerAddress.setId(addressEntity.getId());
		consumerAddress.setLastName(addressEntity.getLastName());
		consumerAddress.setLine1(addressEntity.getAddress());
		consumerAddress.setLine2(addressEntity.getLocality());
		consumerAddress.setTag(addressEntity.getTag());
		consumerAddress.setZipcode(String.valueOf(addressEntity.getPincode()));
		consumerAddress.setLatitude(addressEntity.getLatitude());
		consumerAddress.setLongitude(addressEntity.getLongitude());
		return consumerAddress;
		}catch(Exception e){
//			return null;
			throw new RuntimeException("error while mapping address",e);
			
		}
	}
	
	private String getStateNameById(Integer stateId) {
		if (stateId == null) {
			return null;
		}
		try {
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(getMasterApiBasePath()).append("state/").append(stateId);
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = urlBuilder.toString();
			HttpUriRequest request = RequestBuilder.get(url).addHeader(X_HELPCHAT_AUTH, properties.getProperty(X_HELPCHAT_AUTH)).build();

			HttpResponse resp = httpClient.execute(request);
			
			logger.info("master api call : " + url + " status code:" + resp.getStatusLine().getStatusCode());
			switch (resp.getStatusLine().getStatusCode()) {
			case 404:
				logger.warn("state not found. state id:" + stateId);
				return null;
			case 200:
				break;
			default:
				throw new RuntimeException("state api failed. stateId:" + stateId + " status code:" + resp.getStatusLine().getStatusCode());
			}
			String respContent = EntityUtils.toString(resp.getEntity());
			JSONObject respJson = new JSONObject(respContent);
			return respJson.getString("stateName");
		} catch (ParseException | IOException | JSONException e) {
			throw new RuntimeException("internal service error", e);
		}
	}
	
	private JSONObject getCityInfoById(Integer cityId) {
		if (cityId == null) {
			return null;
		}
		try {
			StringBuilder urlBuilder = new StringBuilder();
			urlBuilder.append(getMasterApiBasePath()).append("city/").append(cityId);
			HttpClient httpClient = HttpClientBuilder.create().build();
			String url = urlBuilder.toString();
			HttpUriRequest request = RequestBuilder.get(url).addHeader(X_HELPCHAT_AUTH, properties.getProperty(X_HELPCHAT_AUTH)).build();

			long startTime = System.currentTimeMillis();
			HttpResponse resp = httpClient.execute(request);
			long endTime = System.currentTimeMillis();
			logger.info("master api call : " + url + " status code:" + resp.getStatusLine().getStatusCode()+" time in millis:"+(endTime-startTime));
			switch (resp.getStatusLine().getStatusCode()) {
			case 404:
				logger.warn("city not found. city id:" + cityId);
				return null;
			case 200:
				break;
			default:
				throw new RuntimeException("city api failed. cityId:" + cityId + " status code:" + resp.getStatusLine().getStatusCode());
			}
			String respContent = EntityUtils.toString(resp.getEntity());
			JSONObject respJson = new JSONObject(respContent);
			return respJson;
		} catch (ParseException | IOException | JSONException e) {
			throw new RuntimeException("internal service error", e);
		}
	}
	
	private String getMasterApiBasePath() {
		return properties.getProperty("master.service.url");
	}
	
//	private UserAddress mapToAddressEntity(Long userId, Address_ address) {
//		UserAddress userAddress = new UserAddress();
//		userAddress.setAddress(address.getLine1());
//		userAddress.setLocality(address.getLine2());
//		userAddress.setFirstName(address.getFirstName());
//		userAddress.setLastName(address.getLastName());
//		userAddress.setPincode(Integer.parseInt(address.getZipcode()));
//		userAddress.setTag(address.getTag());
//		userAddress.setUserId(userId);
//		userAddress.setMobile(StringUtils.trimToNull(address.getContactNumber()));
//		userAddress.setLatitude(address.getLatitude());
//		userAddress.setLongitude(address.getLongitude());
//		
//		// TODO need to resolve name to id mapping
//		Integer cityIdByName = getCityIdByName(address.getCity());
//		Integer stateIdByName = getStateIdByName(address.getState());
//		
//		if (stateIdByName == null) {
//			Error entity = new Error();
//			entity.setMessage("unknown state name:" + address.getState());
//			logger.info("unknown state name:" + address.getState());
//			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(entity).build());
//		}
//		userAddress.setCityId(cityIdByName);
//		userAddress.setCityAlias(address.getCity());
//		userAddress.setStateId(stateIdByName);
//		userAddress.setId(address.getId());
//		userAddress.setIsDefault(Boolean.TRUE.equals(address.getDefault()));
//		return userAddress;
//	}
	
}
