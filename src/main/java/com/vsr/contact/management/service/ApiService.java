package com.vsr.contact.management.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vsr.contact.management.entity.ApiRequest;
import com.vsr.contact.management.entity.DataResponse;
import com.vsr.contact.management.entity.Employee;
import com.vsr.contact.management.exception.BadRequest;
import com.vsr.contact.management.exception.NotFound;
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
            throw new BadRequest("Error converting file to byte array");
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

	public List<DataResponse> getData() {
		List<Employee> result = employeeRepository.findAll();
		List<DataResponse> response = new  ArrayList<>();
		for (Employee emp :result) {
			response.add(getResponse(emp));
		}
		
		return response;
	}

	public DataResponse update(String id, MultipartFile file, ApiRequest request) {
		byte[] fileBytes = null;
		try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's error handling strategy
            throw new BadRequest("Error converting file to byte array");
        }
		UUID uuid = UUID.fromString(id);
		Optional<Employee> employee = employeeRepository.findById(uuid);
		if(!employee.isEmpty()) {
			Employee emp = employee.get();
		
			if(request.getEmployeeName() != null) {
				emp.setEmployeeName(request.getEmployeeName());
			}
			if(request.getPhoneNumber()!= null) {
				emp.setPhoneNumber(request.getPhoneNumber());
			}
			if(request.getEmail() != null) {
				emp.setEmail(request.getEmail());
			}if(request.getReportsTo() != null) {
				emp.setReportsTo(request.getReportsTo());
			}if(fileBytes != null) {
				emp.setProfileImage(fileBytes);
			}
				Employee updatedEmployee = employeeRepository.saveAndFlush(emp);
			
			return getResponse(updatedEmployee);
			
		}else {
			throw new NotFound("not found");
		}
		
	}
	
	public DataResponse delete(String id) {
		UUID uuid = UUID.fromString(id);
		Optional<Employee> employee = employeeRepository.findById(uuid);
		if(!employee.isEmpty()) {
			Employee emp = employee.get();
			employeeRepository.deleteById(uuid);
			return getResponse(emp);
		}else {
			throw new NotFound("not found");
		}
	}
	
	private DataResponse getResponse(Employee emp) {
		DataResponse res = new  DataResponse();
		res.setId(emp.getId());
		res.setEmployeeName(emp.getEmployeeName());
		res.setPhoneNumber(emp.getPhoneNumber());
		res.setEmail(emp.getEmail());
		res.setReportsTo(emp.getReportsTo());
		return res;
	}

}
