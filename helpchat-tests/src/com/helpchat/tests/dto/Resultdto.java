package com.helpchat.tests.dto;

import java.io.Serializable;

public class Resultdto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4512856642707348484L;
	
	private String status;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
