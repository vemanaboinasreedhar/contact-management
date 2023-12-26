package com.vsr.contact.management.entity;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
	
	@Id
	@Column
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
	@Column
    private String employeeName;
	@Column
    private String phoneNumber;
	@Column
    private String email;
	@Column
    private String reportsTo;
	@Column
    private byte[] profileImage = new byte[70000];

}
