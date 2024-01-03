/**
 * 
 */
package com.vsr.contact.management.entity;

import lombok.Data;

/**
 * @author SREEDHAR
 *
 */
@Data
public class DataResponse {

	private String id;
	private String employeeName;
	private String phoneNumber;
	private String email;
	private String reportsTo;
}
