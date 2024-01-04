package com.vsr.contact.management.entity;

import lombok.Data;

@Data
public class ApiRequest {

    private String employeeName;
    private String phoneNumber;
    private String email;
    private String reportsTo;

}
