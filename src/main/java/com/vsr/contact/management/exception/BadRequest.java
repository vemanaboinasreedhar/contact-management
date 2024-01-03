package com.vsr.contact.management.exception;

public class BadRequest extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3467327540811561412L;
	private Long id;
	
	public BadRequest(String message)
	{
		super(message);
	} 
	
	public BadRequest(Long id, String message)
	{
		super(message);
		this.id = id;
	} 

}
