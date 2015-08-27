package com.helpchat.tests.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.helpchat.tests.entities.CityMaster;
import com.helpchat.tests.services.StateCityServiceTest;

@RestController
public class CityDataControllerTest {
	
	@Autowired
	private StateCityServiceTest stateCityServiceTest;
	
	@RequestMapping(value="test/city/name/{name}",method=RequestMethod.GET)

	public void getCityByNameTest(@PathVariable String name){
		try{
			
			CityMaster response= (CityMaster) stateCityServiceTest.apifindCityByName(name);
			
			if(response!=null){
				CityMaster cityMaster=stateCityServiceTest.findCityByName(name);
		
				if(response.getCityName().toString() == cityMaster.getCityName().toString()){
					System.out.println("getCityByNameTest is verified successfully");					
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
			e.printStackTrace();
			//return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
