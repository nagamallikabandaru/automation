package com.helpchat.tests.dto;

import java.io.Serializable;
import java.util.List;
public class Resultdto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4512856642707348484L;
	
	private List<Responsedto> responseDto;
//	private String status;
//	private String message;
//	public String getMessage() {
//		return message;
//	}
//	public void setMessage(String message) {
//		this.message = message;
//	}
//	public String getStatus() {
//		return status;
//	}
//	public void setStatus(String status) {
//		this.status = status;
//	}

	public List<Responsedto> getResponseDto() {
		return responseDto;
	}

	public void setResponseDto(List<Responsedto> responseDto) {
		this.responseDto = responseDto;
	}
}
