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

	       // ResponseEntity<ExceptionResponse> response = exceptionHandler.handleBadRequest(ex);
	        ResponseEntity<ExceptionResponse> response1 = exceptionHandler.handleBadRequest(ex1);

	        //assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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

}
