package com.helpchat.tests.comparator;

import java.util.HashMap;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.helpchat.consumers.model.Address_;

public class Address {

	public HashMap<String, String> comparator(Address_ addressJsonFromAPI, Address_ addressJsonFromDB , String fieldsToValidate) throws Exception {
		HashMap<String, String> cmpResult = new HashMap<String, String>();
		try{
//			System.out.println("FieldToValidate"+fieldsToValidate);
			int cnt = 0;
						
			BeanMap map = new BeanMap(addressJsonFromAPI);
	
	        PropertyUtilsBean propUtils = new PropertyUtilsBean();
//	        propUtils.getNestedProperty(addressJsonFromAPI, name)
	       
	        for (Object propNameObject : map.keySet()) {
	            String propertyName = (String) propNameObject;
	            System.out.println("Property Namme:"+propertyName);
	            if(!(propertyName.equals("class")|| propertyName.equals("additionalProperties"))){
		            Object dbProperty = propUtils.getProperty(addressJsonFromDB, propertyName);
		            Object apiProperty = propUtils.getProperty(addressJsonFromAPI, propertyName);
		            System.out.println("property1:"+dbProperty);
		            System.out.println("property2:"+apiProperty);
		            if(null!=apiProperty && null!=dbProperty){
		            	if(apiProperty.equals(dbProperty)){
		            		cnt++;
		            	}else{
		            		System.out.println("Data mismatch:"+propertyName+"\n"+"DB Value:"+dbProperty+"\n"+"API Value:"+apiProperty);
		            		cmpResult.put(propertyName, "Data mismatch for :"+"DB Value:"+dbProperty+"\n"+"API Value:"+apiProperty);
		            	}			            
		            }else if(null==apiProperty && null!=dbProperty){
		            	System.out.println("Property:"+propertyName+" is null in DB");
		            	cmpResult.put(propertyName, "Data mismatch:"+"DB Value:"+dbProperty+"-API Value:"+apiProperty);
		            }else if(null!=apiProperty && null==dbProperty){
		            	System.out.println("Property:"+propertyName+" is null in DB");
		            	cmpResult.put(propertyName, "Data mismatch:"+"DB Value:"+dbProperty+"-API Value:"+apiProperty);		            			            
		         }
	           }	            
	        }
//			System.out.println("Count value from the comparator"+cnt);
			if(cnt==Integer.parseInt(fieldsToValidate)){
				
				cmpResult.put("Validation","Success");
			}else{
				cmpResult.put("Validation","Failed");
			}
	
			return cmpResult;
	}catch(Exception e){
		throw new Exception(e.getMessage());
	}
	}
}
	        
	        



