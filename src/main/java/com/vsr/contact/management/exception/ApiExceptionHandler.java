package com.vsr.contact.management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler{
	
	@ExceptionHandler(org.springframework.web.multipart.support.MissingServletRequestPartException.class)
	public ResponseEntity<ExceptionResponse> handleMissingServletRequestPartException(org.springframework.web.multipart.support.MissingServletRequestPartException ex){
		return new ResponseEntity<>(getResponse(ex), HttpStatus.BAD_REQUEST);
	}
	
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
	
	private ExceptionResponse getResponse(Throwable ex) {
		ExceptionResponse res = new ExceptionResponse();
		res.setStatusCode(400001);
		res.setMessage(ex.getMessage());
		res.setReason(ex.toString());
		return res;
	}
}
