package com.helpchat.tests.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.helpchat.consumers.model.Address_;
import com.helpchat.tests.dto.Responsedto;
import com.helpchat.tests.entities.CityMaster;
import com.helpchat.tests.services.RequestGeneratorService;
import com.helpchat.tests.services.StateCityServiceTest;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(value="/")
public class CityDataControllerTest {
	
	@Autowired
	RequestGeneratorService<CityMaster, CityMaster> cityMasterServiceTest;
	ApplicationContext context;
	private static final Logger logger=Logger.getLogger(CityDataControllerTest.class.getName());

	@RequestMapping(value="test/city/pincode/{id}",method=RequestMethod.POST)

	public void getCityByPincodeTest(@PathVariable String id){
		try{
//			StateCityServiceTest stateCityServiceTest=context.getBean(StateCityServiceTest.class);
			HashMap<String, String> params=new HashMap<String, String>();
			params.put("id", id);
			ResponseEntity<CityMaster> response= cityMasterServiceTest.get(params);
			
			if(response!=null){
				ObjectMapper mapper = new ObjectMapper();
				ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
				String json = ow.writeValueAsString(response.getBody());
				logger.info("Json string"+json);
				logger.info("Json string"+new JSONObject(json).toString());
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				CityMaster resp=mapper.readValue(new JSONObject(json).toString(), CityMaster.class);
				logger.info("resp"+resp);
				logger.info("City ID:"+resp.getCityName());
				logger.info(resp.getId().toString());
//				logger.info(resp.getPriorityDisplay().toString());
				logger.info(resp.getState().getStateName().toString());
				logger.info("State ID:"+resp.getState().getId());
			}
		}
		catch(Exception e){
			logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@RequestMapping(value="test/city/name/{name}",method=RequestMethod.GET)

	public void getCityByNameTest(@PathVariable String name){
		try{
//			System.out.println("In getCityByNameTest");
			StateCityServiceTest stateCityServiceTest=context.getBean(StateCityServiceTest.class);
			CityMaster response= (CityMaster) stateCityServiceTest.apifindCityByName(name);
			
			if(response!=null){
				CityMaster cityMaster=stateCityServiceTest.findCityByName(name);
//				System.out.println("CityMaster data frm db:"+cityMaster.getCityName().toString());
				if(response.getCityName().toString().contains(cityMaster.getCityName().toString())){
					logger.info("api verified successfully");
				//	System.out.println("getCityByNameTest is verified successfully");					
				}
			}
//				return new ResponseEntity<>(cityDto,HttpStatus.OK);
//			else{
//				CityMaster cityMaster=stateCityServiceTest.findCityByName(name);
//				if(cityMaster!=null){
//					cityDto=objectMapperService.map(cityMaster, CityDto.class);
//					cacheClient.put("/v4/city/name/"+name, cityDto,1000*60*60*24);
//					return new ResponseEntity<>(cityDto,HttpStatus.OK);
//				}
//				else
//					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//				
//			}
		}
		catch(Exception e){
			logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();
			//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value="/post/city/name/{name}",method=RequestMethod.POST)
	//@ResponseBody
	public  ResponseEntity<Object> testCityByName(@PathVariable String name){		
		HttpStatus status = null;
		Responsedto responseDto=new Responsedto();
		HashMap<String, String> result = new HashMap<String, String>();
		responseDto.setTestName("getCityByName");
		responseDto.setTestID("001");;
		try{
//			System.out.println("In getCityByNameTest");
			StateCityServiceTest stateCityServiceTest=context.getBean(StateCityServiceTest.class);
			CityMaster response= (CityMaster) stateCityServiceTest.apifindCityByName(name);
			
			if(response!=null){
				CityMaster cityMaster=stateCityServiceTest.findCityByName(name);
				System.out.println("CityMaster data frm db:"+cityMaster.getCityName().toString());
				if(response.getCityName().toString().contains(cityMaster.getCityName().toString())){
					logger.info("api verified successfully."+HttpStatus.OK);
					status=HttpStatus.OK;
					result.put("Status", "Pass");
					result.put("Message", "Test Case verified successfully");
					responseDto.setResult(result);
					return new ResponseEntity<Object>(responseDto,HttpStatus.OK);
				//	System.out.println("getCityByNameTest is verified successfully");					
				}
			}else{
				logger.info("Api response is null:"+HttpStatus.BAD_REQUEST);
				status=HttpStatus.BAD_REQUEST;
				result.put("Status", "Fail");
				result.put("Message", "Api response is null");
				responseDto.setResult(result);
				
				return new ResponseEntity<Object>(responseDto,HttpStatus.BAD_REQUEST);
			//	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e){
			logger.log(Level.SEVERE,e.getMessage());
			e.printStackTrace();				
			result.put("Status", "Fail");
			result.put("Message", "Unknown exception occured");
			responseDto.setResult(result);
			responseDto.setExceptions(e.toString());
			logger.info("Unknown exception occured:"+HttpStatus.INTERNAL_SERVER_ERROR);
			status=HttpStatus.INTERNAL_SERVER_ERROR;
			return new ResponseEntity<Object>(responseDto,HttpStatus.INTERNAL_SERVER_ERROR);
			//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		
		//return "getCityByname verified. Status:"+status.name();
	}
}