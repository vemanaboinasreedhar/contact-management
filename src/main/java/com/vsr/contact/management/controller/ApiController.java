package com.vsr.contact.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsr.contact.management.entity.ApiRequest;
import com.vsr.contact.management.entity.DataResponse;
import com.vsr.contact.management.service.ApiService;

@RestController
@RequestMapping("api/v1")
public class ApiController {

	@Autowired
	private ApiService apiService;

	@PostMapping("/submit")
	public ResponseEntity<String> create(
			@RequestPart(name = "file") MultipartFile file,
			@RequestPart(name = "request") String request) throws JsonMappingException, JsonProcessingException
			{
		ObjectMapper objectMapper = new ObjectMapper();
		ApiRequest requests = objectMapper.readValue(request, ApiRequest.class);
		String response = apiService.create(file, requests);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getdata")
	public ResponseEntity<List<DataResponse>> getData(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy){
		List<DataResponse> response = apiService.getData(page, size, sortBy);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<DataResponse> update(
			@RequestPart(name = "id") String id,
			@RequestPart(name = "file", required = false) MultipartFile file,
			@RequestPart(name = "request",  required = false) String request) throws JsonMappingException, JsonProcessingException
			{
		ObjectMapper objectMapper = new ObjectMapper();
		ApiRequest requests = objectMapper.readValue(request, ApiRequest.class);
		DataResponse response = apiService.update(id, file, requests);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<DataResponse> delete(@RequestParam("id") String id){
		DataResponse response = apiService.delete(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	
	}
	
	@GetMapping("/getLevelManager")
	public ResponseEntity<DataResponse> getLevelManager(@RequestParam ("id") String employeeId, @RequestParam ("level") int level){
		DataResponse response = apiService.getLevelManager(employeeId, level);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
