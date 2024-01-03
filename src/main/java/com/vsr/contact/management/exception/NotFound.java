package com.vsr.contact.management.exception;

public class NotFound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6590728642334324044L;
	private Long id;
	public NotFound(String message)
	{
		super(message);
	} 
	
	public NotFound(Long id, String message)
	{
		super(message);
		this.id = id;
	} 
}
