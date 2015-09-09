package com.helpchat.tests.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.helpchat.consumers.model.Address_;
import com.helpchat.tests.dto.Responsedto;
import com.helpchat.tests.dto.Resultdto;
import com.helpchat.tests.services.UserAddressServiceTest;
//import com.helpchat.tests.testdata.entities.AddressApi;
import com.helpchat.tests.util.ExcelUtil;
import com.helpchat.tests.util.PropertiesUtil;
import com.helpchat.tests.comparator.*;
@RestController
@RequestMapping("/")
public class UserAddressControllerTest {

//	@Autowired
//	ApplicationContext context;

	@Autowired
	UserAddressServiceTest userAddressServiceTest;
	
	@Autowired
	private PropertiesUtil properties;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@RequestMapping(value="/test/v1/customers/me/addresses",method=RequestMethod.POST)
	public  ResponseEntity<Object> testPostAddress(){
		
		//PropertiesUtil properties=context.getBean(PropertiesUtil.class);
		//UserAddressServiceTest userAddressServiceTest=context.getBean(UserAddressServiceTest.class);
		Resultdto resultDto = new Resultdto();
		List<Responsedto> responseDtoList = new ArrayList<Responsedto>();
//		HashMap<String,ResponseEntity<Address_>> responseMap=new HashMap<String, ResponseEntity<Address_>>();
//		HashMap<String, String> result = new HashMap<String, String>();
//		AddressApi addressApi=new AddressApi();
		Address_ address_=new Address_();
		logger.info("In testPostAddress method");
		logger.info("user id:"+properties.getProperty("user_id"));
		Long userId=Long.valueOf(properties.getProperty("user_id"));
		HashMap<String, HashMap<String, String>> dataMap = ExcelUtil.readXlsFile(properties.getProperty("testData"));
		
		Iterator<String> iterData = dataMap.keySet().iterator();
	
		while (iterData.hasNext()) {
			Responsedto responseDto = new Responsedto();
			String rowKey = iterData.next();
			HashMap<String, String> rowMap = dataMap.get(rowKey);
			responseDto.setTestID(rowMap.get("tcId"));
			responseDto.setTestName(rowMap.get("description"));
			responseDto.setApiUrl(rowMap.get("apiUrl"));
			logger.info(responseDto.getTestID());
			//responseDtoList.add(resposenFdto);
//			responseDto.setExpectedResponse(rowMap.get("expectedResponse"));
			Address_ addressJson = mapToAddressApiEntity(rowMap);
			HashMap<String, String> result=new HashMap<String, String>();
			ResponseEntity<Address_> response = null;
			try {
				response=userAddressServiceTest.postUserAddress(addressJson);
				if (response.getStatusCode().toString().contentEquals(rowMap.get("expectedResponse"))) {
					switch (response.getStatusCode().toString()) {
					case "201":
						
//						System.out.println("Inside switch case");
						address_=userAddressServiceTest.getUserAddress(userId, response.getBody().getId());
						Address address=new Address();
						HashMap<String, String> comparatorResult=  address.comparator(response.getBody(), address_);
						System.out.println("Validation result:"+comparatorResult.get("validation"));
						if (comparatorResult.get("Validation").toLowerCase().contains("success")){
							result.put("Status", "Pass");
							result.put("Message", "api verfied successfully");

						}else{
							result.put("Status", "Fail");
							
							String message="Data mismatch for:";
							for(String key:comparatorResult.keySet()){
								String value=comparatorResult.get(key);
								if(value.contains("mismatch")){
									message=message+key+",";
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
				} else{
					result.put("Status", "Fail");
					result.put("Message", " Unexpected response from api."
							+ "Actual Response:"+response.getStatusCode().toString()
							+ "Expected Response:"+rowMap.get("expectedResponse"));		
					responseDto.setResult(result);
					responseDto.setExceptions("No exceptions occured");
					responseDtoList.add(responseDto);
//					return new ResponseEntity<Object>(responseDto,HttpStatus.EXPECTATION_FAILED);
				}			
		} catch(NullPointerException npe){
				result.put("Status", "Fail");
				result.put("Message", " API Response is null"+response.getStatusCode());		
				responseDto.setResult(result);
				responseDto.setExceptions(npe.getMessage());
				responseDtoList.add(responseDto);
//				return new ResponseEntity<Object>(responseDto,HttpStatus.EXPECTATION_FAILED);				
			}catch (Exception e) {				
				result.put("Status", "Fail");
				result.put("Message", "Exceptions occured");
				responseDto.setExceptions(e.getMessage());
//				e.printStackTrace();
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
