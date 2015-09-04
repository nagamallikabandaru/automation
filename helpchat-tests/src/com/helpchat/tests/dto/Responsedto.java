package com.helpchat.tests.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class Responsedto implements Serializable{

  
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private List<TestDetailsdto> testDetailsdto;
	private String testName;
  private String apiUrl;
  private String testID;
  private HashMap<String, String> result;
  //private Resultdto resultDto;
  private String exceptions;
  
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String getTestID() {
		return testID;
	}
	public void setTestID(String testID) {
		this.testID = testID;
	}
	public HashMap<String, String> getResult() {
		return result;
	}
	public void setResult(HashMap<String, String> result) {
		this.result = result;
	}
	public String getExceptions() {
		return exceptions;
	}
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
  
}

