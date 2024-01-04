package com.vsr.contact.management.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		Employee emp = new Employee();
		emp.setEmployeeName(request.getEmployeeName());
		emp.setPhoneNumber(request.getPhoneNumber());
		emp.setEmail(request.getEmail());
		emp.setReportsTo(request.getReportsTo());
		emp.setProfileImage(fileBytes);
		Employee createdEmployee = employeeRepository.saveAndFlush(emp);
		return "employeeId : " + createdEmployee.getId();
	}

	public List<DataResponse> getData(int page, int size, String sortBy) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
		Page<Employee> result = employeeRepository.findAll(pageRequest);
		List<DataResponse> response = new  ArrayList<>();
		for (Employee emp :result) {
			response.add(getResponse(emp));
		}
		
		return response;
	}

	public DataResponse update(String employeeId, MultipartFile file, ApiRequest request) {
		byte[] fileBytes = null;
		if(file != null) {
		try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception based on your application's error handling strategy
            throw new BadRequest("Error converting file to byte array");
        }
		}
		UUID uuid = UUID.fromString(employeeId);
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
			throw new NotFound("Employee not found with id : " + employeeId);
		}
		
	}
	
	public DataResponse delete(String employeeId) {
		UUID uuid = UUID.fromString(employeeId);
		Optional<Employee> employee = employeeRepository.findById(uuid);
		if(!employee.isEmpty()) {
			Employee emp = employee.get();
			employeeRepository.deleteById(uuid);
			return getResponse(emp);
		}else {
			throw new NotFound("Employee not found with id : " + employeeId);
		}
	}
	// converting to response structure
	private DataResponse getResponse(Employee emp) {
		DataResponse res = new  DataResponse();
		res.setId(emp.getId());
		res.setEmployeeName(emp.getEmployeeName());
		res.setPhoneNumber(emp.getPhoneNumber());
		res.setEmail(emp.getEmail());
		res.setReportsTo(emp.getReportsTo());
		return res;
	}
	
	// converting to convertIntToString
	private String convertIntToString(int i) {
        switch (i) {
            case 1:
                return "first";
            case 2:
                return "second";
            case 3:
                return "third";
            case 4:
                return "fourth";
            case 5:
                return "fifth";
            case 6:
                return "sixth";
            case 7:
                return "seventh";
            case 8:
                return "eighth";
            case 9:
                return "ninth";
            case 10:
                return "tenth";
            default:
                return "Unknown"; // Default value if no match is found
        }
	}

	public DataResponse getLevelManager(String employeeId, int level) {
		//creating hashMap
		Map<String, Employee> employeeMap = new HashMap<>();
		//Fetching all the records from db 
		List<Employee> employeeList = employeeRepository.findAll();
		// Adding  data into hashMap
		for(Employee employee : employeeList) {
			employeeMap.put(employee.getId().toString(), employee);
		}
		//Fetching the records from db  based on employee Id
		Optional<Employee> employee = employeeRepository.findById(UUID.fromString(employeeId));
		if(!employee.isEmpty()) {
			Employee emp = employee.get();
			//Finding the level manager
				for(int i=0; i<level;i++) {
					if(emp.getReportsTo() != null){
					 emp = employeeMap.get(emp.getReportsTo());
					}else {
						throw new NotFound("Employee not found for "+ convertIntToString(level)+ "manager" );
					}		
				}
				return getResponse(emp);
		}else {
			throw new NotFound("Employee not found with id : " + employeeId);
		}
		
	}

}
