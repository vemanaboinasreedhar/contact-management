package com.vsr.contact.management.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vsr.contact.management.entity.ApiRequest;
import com.vsr.contact.management.entity.Employee;
import com.vsr.contact.management.repository.EmployeeRepository;

@Service
public class ApiService {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public String create(MultipartFile file, ApiRequest request) {
		byte[] fileBytes = null;
		try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's error handling strategy
            return "Error converting file to byte array";
        }
		System.out.println(fileBytes.length);
		Employee emp = new Employee();
		emp.setEmployeeName(request.getEmployeeName());
		emp.setPhoneNumber(request.getPhoneNumber());
		emp.setEmail(request.getEmail());
		emp.setReportsTo(request.getReportsTo());
		emp.setProfileImage(fileBytes);
		Employee createdEmployee = employeeRepository.saveAndFlush(emp);
		return "employeeId : " + createdEmployee.getId();
	}

}
