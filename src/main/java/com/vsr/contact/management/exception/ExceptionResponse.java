package com.vsr.contact.management.exception;

import lombok.Getter;
import lombok.Setter;

public class ExceptionResponse {
	@Getter @Setter
	private int statusCode;
	@Getter @Setter
	private String message;
	@Getter @Setter
	private String reason;
}
