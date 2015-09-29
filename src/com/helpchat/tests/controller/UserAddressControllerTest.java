package com.helpchat.tests.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;







//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.ObjectWriter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.helpchat.consumers.model.Address_;
import com.helpchat.tests.dto.*;
import com.helpchat.tests.services.UserAddressServiceTest;
import com.helpchat.tests.util.ExcelUtil;
import com.helpchat.tests.util.PropertiesUtil;
import com.helpchat.tests.comparator.*;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value = "address", description = "Operations to test the address APIs")
@RequestMapping("/")
public class UserAddressControllerTest {

//	@Autowired
//	ApplicationContext context;

	@Autowired
	UserAddressServiceTest<Address_, Address_> userAddressServiceTest;
	
	@Autowired
	private PropertiesUtil properties;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@ApiOperation(value = "Test for post address api")
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/test/v1/customers/me/addresses",method=RequestMethod.POST)
	public  ResponseEntity<Object> testPostAddress(){
		
		//PropertiesUtil properties=context.getBean(PropertiesUtil.class);
		//UserAddressServiceTest userAddressServiceTest=context.getBean(UserAddressServiceTest.class);
		Resultdto resultDto = new Resultdto();
		List<Responsedto> responseDtoList = new ArrayList<Responsedto>();
//		HashMap<String,ResponseEntity<Address_>> responseMap=new HashMap<String, ResponseEntity<Address_>>();
//		AddressApi addressApi=new AddressApi();
		Address_ address_=new Address_();
		Responsedto responseDtoPre = new Responsedto();
		logger.info("In testPostAddress method");
		logger.info("user id:"+properties.getProperty("user_id"));
		Long userId=Long.valueOf(properties.getProperty("user_id"));
		HashMap<String, HashMap<String, String>> dataMap;
		
		ResponseEntity<Address_> response = null;
//		UserAddressServiceTest<Address_, Address_> userAddressServiceTest=new UserAddressServiceTest<Address_, Address_>();
	
		try{
			dataMap = ExcelUtil.readXlsFile(properties.getProperty("testData"));
		}catch (Exception e) {				
//			result.put("Status", "Fail");
//			result.put("Message", "Exceptions occured");
			responseDtoPre.setExceptions(e.getMessage());
//			responseDto.setResult(result);
			responseDtoList.add(responseDtoPre);
			return new ResponseEntity<Object>(responseDtoPre,HttpStatus.PRECONDITION_FAILED);
		}
			Iterator<String> iterData = dataMap.keySet().iterator();
		
			while (iterData.hasNext()) {
  				String rowKey = iterData.next();
				HashMap<String, String> result=new HashMap<String, String>();
				HashMap<String, String> rowMap = dataMap.get(rowKey);
				Responsedto responseDto = new Responsedto();
				responseDto.setTestID(rowMap.get("tcId"));
				responseDto.setTestName(rowMap.get("description"));
				responseDto.setApiUrl(rowMap.get("apiUrl"));
				logger.debug("************************************");
				logger.info(responseDto.getTestID());
				logger.info(responseDto.getTestName());
				logger.info(responseDto.getApiUrl());
				//responseDtoList.add(resposenFdto);
	//			responseDto.setExpectedResponse(rowMap.get("expectedResponse"));
				Address_ addressJson = mapToAddressApiEntity(rowMap);
				
//				String url=properties.getProperty("consumers.service.url")+"addresses";
				try {
					logger.info("Calling postUserAddress method:");
					response=userAddressServiceTest.postUserAddress(addressJson);
					ObjectMapper mapper = new ObjectMapper();
					if (response.getStatusCode().toString().contentEquals(rowMap.get("expectedResponse"))) {
						switch (response.getStatusCode().toString()) {
						case "201":
//							logger.info("Response Body:"+response.getBody().getClass().getName());
							ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
							String json = ow.writeValueAsString(response.getBody());
//							JSONObject jsonObject=new JSONObject(json);
							logger.info("Json string"+json);
							logger.info("Json string"+new JSONObject(json).toString());
							mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
							Address_ resp=mapper.readValue(new JSONObject(json).toString(), Address_.class);
							logger.info("resp"+resp);
							logger.info("Calling getUserAddress method:"+resp.getCity());
							address_=userAddressServiceTest.getUserAddress(userId, resp.getId());
							logger.info("address_ from DB:"+address_.getCity());
							Address address=new Address();
							logger.info("API response is 201. Validating the data");
							HashMap<String, String> comparatorResult=  address.comparator(resp, address_,rowMap.get("fieldsToValidate"));
							System.out.println("Validation result:"+comparatorResult.get("validation"));
							if (comparatorResult.get("Validation").toLowerCase().contains("success")){
								result.put("Status", "Pass");
								result.put("Message", "api verfied successfully");
		
							}else{
								result.put("Status", "Fail");
								
								String message="";
								for(String key:comparatorResult.keySet()){
									String value=comparatorResult.get(key);
									if(value.contains("mismatch")){
										message=message+key+":"+value+"||";
									}
									
									result.put("Message", message);
								}
							}
							responseDto.setResult(result);
							responseDto.setExceptions("No exceptions occured");
							responseDtoList.add(responseDto);
		//						return new ResponseEntity<Object>(responseDto,HttpStatus.OK);
		//					case 200:
		//						break;	
						}
						
					logger.info(responseDto.getResult().toString());
					logger.info(responseDto.getExceptions());
				} else{
					result.put("Status", "Fail");
					result.put("Message", " Unexpected response from api."
							+ "Actual Response:"+response.getStatusCode().toString()
							+ "Expected Response:"+rowMap.get("expectedResponse"));		
					logger.info(result.toString()+" when expected result is different from actual response");
					responseDto.setResult(result);
					responseDto.setExceptions("No exceptions occured");
					responseDtoList.add(responseDto);
	//					return new ResponseEntity<Object>(responseDto,HttpStatus.EXPECTATION_FAILED);

			}
			}catch(NullPointerException npe){
				result.put("Status", "Fail");
				result.put("Message", " API Response is null"+response.getStatusCode());		
				responseDto.setResult(result);
				responseDto.setExceptions(npe.getMessage());
				responseDtoList.add(responseDto);
				npe.printStackTrace();
//				return new ResponseEntity<Object>(responseDto,HttpStatus.EXPECTATION_FAILED);				
			}catch (Exception e) {				
				result.put("Status", "Fail");
				result.put("Message", "Exceptions occured");
				responseDto.setExceptions(e.getMessage());
				e.printStackTrace();
				responseDto.setResult(result);
				responseDtoList.add(responseDto);
//				return new ResponseEntity<Object>(responseDto,HttpStatus.EXPECTATION_FAILED);
			}				
		}
			resultDto.setResponseDto(responseDtoList);
			return new ResponseEntity<Object>(resultDto,HttpStatus.OK);			
	}
	
	
	private Address_ mapToAddressApiEntity(HashMap<String,String> dataMap){
		
		if(dataMap ==null){
			return null;
		}		
		Address_ addressJson=new Address_();
		boolean isDefault;
		
		if(dataMap.get("default").contains("true")){
			isDefault=true;
		}else if(dataMap.get("default").contains("false")){
			isDefault=false;
		}else{
			isDefault=false;
		}
//		addressApi.setAddressId(dataMap.get("addressId"));
//		addressApi.setLine1(dataMap.get("line1"));
//		addressApi.setLine2(dataMap.get("line2"));
//		addressApi.setFirst_name(dataMap.get("firstName"));
//		addressApi.setLast_name(dataMap.get("lastName"));
//		addressApi.setCity(dataMap.get("city"));
//		addressApi.setState(dataMap.get("state"));
//		addressApi.setZipcode(dataMap.get("zipcode"));
//		addressApi.setContact_number(dataMap.get("contactNumber"));
//		addressApi.set_default(dataMap.get("default"));
//		addressApi.setTag(dataMap.get("tag"));
		
		if(dataMap.get("line1").isEmpty() || dataMap.get("line1").contains("null")){
			addressJson.setLine1(null);
		}else{
			addressJson.setLine1(dataMap.get("line1"));
		}		
		addressJson.setLine2(dataMap.get("line2"));
		addressJson.setFirstName(dataMap.get("firstName"));
		addressJson.setLastName(dataMap.get("lastName"));
		addressJson.setCity(dataMap.get("city"));
		addressJson.setState(dataMap.get("state"));
		addressJson.setZipcode(dataMap.get("zipcode"));
		addressJson.setContactNumber(dataMap.get("contactNumber"));
		addressJson.setDefault(isDefault);
		addressJson.setTag(dataMap.get("tag"));
		return addressJson;
		
	}

	
}
