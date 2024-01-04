package com.vsr.contact.management.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionHandlerTest {
	
	private final ApiExceptionHandler exceptionHandler = new ApiExceptionHandler();

	 @Test
	  public void testHandleBadRequest() {
		 BadRequest ex  = new BadRequest ("Test BadRequest Exception");
		 BadRequest ex1  = new BadRequest (400000L, "Test BadRequest Exception");

	        ResponseEntity<ExceptionResponse> response = exceptionHandler.handleBadRequest(ex);
	        ResponseEntity<ExceptionResponse> response1 = exceptionHandler.handleBadRequest(ex1);

	         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	         assertEquals("Test BadRequest Exception", response1.getBody().getMessage());
	    }
	 
	 @Test
	  public void testHandleNotFound() {
		 NotFound ex  = new NotFound ("Test NotFound Exception");
		 NotFound ex1  = new NotFound (404001L, "Test NotFound Exception");

	        ResponseEntity<ExceptionResponse> response = exceptionHandler.handleNotFound(ex);
	        ResponseEntity<ExceptionResponse> response1 = exceptionHandler.handleNotFound(ex1);

	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertEquals("Test NotFound Exception", response1.getBody().getMessage());
	    }
	 
	 @Test
	  public void testHandleInternalServerException() {
		 InternalServerException ex  = new InternalServerException ("Test Runtime Exception");

	        ResponseEntity<ExceptionResponse> response = exceptionHandler.handleInternalServerException(ex);

	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	        assertEquals("Test Runtime Exception", response.getBody().getMessage());
	    }
	 
	 @Test
	  public void testhandleMissingServletRequestPartException() {
		 org.springframework.web.multipart.support.MissingServletRequestPartException ex  = new org.springframework.web.multipart.support.MissingServletRequestPartException ("attribute");

	        ResponseEntity<ExceptionResponse> response = exceptionHandler.handleMissingServletRequestPartException(ex);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        assertEquals("Required part 'attribute' is not present.", response.getBody().getMessage());
	    }
	 @Test
	  public void testExceptionResponse() {
		 ExceptionResponse response = new ExceptionResponse();
		 response.setStatusCode(40001);
		 response.setReason("reason");
		 assertEquals(40001, response.getStatusCode());
		 assertEquals("reason", response.getReason());
	 }
}
