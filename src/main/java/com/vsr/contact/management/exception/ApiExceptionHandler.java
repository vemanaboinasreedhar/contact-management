package com.vsr.contact.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler{
	
	@ExceptionHandler(BadRequest.class)
	public ResponseEntity<ExceptionResponse> handleBadRequest(BadRequest ex){
		return new ResponseEntity<>(getResponse(ex), HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(NotFound.class)
	public ResponseEntity<ExceptionResponse> handleNotFound(NotFound ex){
		return new ResponseEntity<>(getResponse(ex), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ExceptionResponse> handleInternalServerException(InternalServerException ex){
		return new ResponseEntity<>(getResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ExceptionResponse getResponse(Exception ex) {
		ExceptionResponse res = new ExceptionResponse();
		res.setMessage(ex.getMessage());
		return res;
	}
}
