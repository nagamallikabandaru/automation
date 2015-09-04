package com.helpchat.tests.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.helpchat.consumers.model.Address_;
import com.helpchat.tests.dto.Responsedto;
import com.helpchat.tests.services.UserAddressServiceTest;
import com.helpchat.tests.testdata.entities.AddressApi;
import com.helpchat.tests.util.ExcelUtil;
import com.helpchat.tests.util.PropertiesUtil;

@RestController
@RequestMapping("/")
public class UserAddressControllerTest {

//	@Autowired
//	ApplicationContext context;

	@Autowired
	UserAddressServiceTest userAddressServiceTest;
	
	@Autowired
	private PropertiesUtil properties;
	
	@RequestMapping(value="/test/v1/customers/me/addresses",method=RequestMethod.POST)
	public  ResponseEntity<Object> testPostAddress(){
		
		//PropertiesUtil properties=context.getBean(PropertiesUtil.class);
		//UserAddressServiceTest userAddressServiceTest=context.getBean(UserAddressServiceTest.class);
		Responsedto responseDto=new Responsedto();
		HashMap<String, String> result = new HashMap<String, String>();
		AddressApi addressApi=new AddressApi();
		Address_ address_=new Address_();
		System.out.println("In testPostAddress method");
		System.out.println("user id value:"+properties.getProperty("user_id"));
		Long userId=Long.valueOf(properties.getProperty("user_id"));
		HashMap<String, HashMap<String, String>> dataMap = ExcelUtil.readXlsFile(properties.getProperty("testData"));
		
		Iterator<String> iterData = dataMap.keySet().iterator();
	
		while (iterData.hasNext()) {
			String rowKey = iterData.next();
			HashMap<String, String> rowMap = dataMap.get(rowKey);
			addressApi=mapToAddressApiEntity(rowMap);
//			responseDto.setTestID(addressApi.getTcId());
	//		responseDto.setTestName(addressApi.getDescription());
			String response=userAddressServiceTest.postUserAddress(addressApi);
			System.out.println("Response from api:"+response);
			try {
				address_=userAddressServiceTest.getUserAddress(userId, Long.valueOf(5350));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<Object>(responseDto,HttpStatus.OK);
		}
	
		return null;		
	}
	
	
	private AddressApi mapToAddressApiEntity(HashMap<String,String> dataMap){
		
		AddressApi addressApi=new AddressApi();
		
		if(dataMap ==null){
			return null;
		}
//		addressApi.setTcId(dataMap.get("tcId"));
//		addressApi.setDescription(dataMap.get("description"));
//		addressApi.setApiUrl(dataMap.get("apiUrl"));
		//addressApi.setAddressId(dataMap.get("addressId"));
		addressApi.setLine1(dataMap.get("line1"));
		addressApi.setLine2(dataMap.get("line2"));
		addressApi.setFirst_name(dataMap.get("firstName"));
		addressApi.setLast_name(dataMap.get("lastName"));
		addressApi.setCity(dataMap.get("city"));
		addressApi.setState(dataMap.get("state"));
		addressApi.setZipcode(dataMap.get("zipcode"));
		addressApi.setContact_number(dataMap.get("contactNumber"));
		addressApi.set_default(dataMap.get("default"));
		addressApi.setTag(dataMap.get("tag"));
//		addressApi.setExpectedResponse(dataMap.get("expectedResponse"));
		
		return addressApi;
		
	}

	
}
