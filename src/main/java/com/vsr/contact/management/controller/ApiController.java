package com.vsr.contact.management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsr.contact.management.entity.ApiRequest;
import com.vsr.contact.management.service.ApiService;

@RestController
public class ApiController {

	@Autowired
	private ApiService apiService;

	@PostMapping("/submit")
	public String create(
			@RequestPart("file") MultipartFile file,
			@RequestPart("request") String request) throws JsonMappingException, JsonProcessingException
			{
		ObjectMapper objectMapper = new ObjectMapper();
		ApiRequest requests = objectMapper.readValue(request, ApiRequest.class);
		
		return apiService.create(file, requests);
	}

}
