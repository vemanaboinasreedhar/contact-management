package com.vsr.contact.management.exception;

import lombok.Data;

@Data
public class ExceptionResponse {
	
	private int statusCode;
	private String message;
	private String reason;

}
