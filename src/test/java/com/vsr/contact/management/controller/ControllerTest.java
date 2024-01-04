package com.vsr.contact.management.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vsr.contact.management.entity.ApiRequest;
import com.vsr.contact.management.entity.DataResponse;
import com.vsr.contact.management.service.ApiService;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

	@InjectMocks
	private ApiController apiController;

	@Mock
	private ApiService apiService;

	@Test
	public void createTest() throws JsonMappingException, JsonProcessingException {
		// Prepare test data
		String request = "{\"employeeName\": \"value1\", \"phoneNumber\": \"value2\", \"email\": \"value2\", \"reportsTo\": \"value2\"}";
		ObjectMapper objectMapper = new ObjectMapper();
		ApiRequest requests = objectMapper.readValue(request, ApiRequest.class);
		byte[] fileBytes = "Some file content".getBytes();
		MockMultipartFile mockFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileBytes);

		List<DataResponse> list = new ArrayList<>();
		DataResponse dataResponse = new DataResponse();
		dataResponse.setEmployeeName("value1");
		dataResponse.setPhoneNumber("value2");
		dataResponse.setEmail("value2");
		dataResponse.setReportsTo("value2");
		list.add(dataResponse);

		// Mocking create method behavior
		when(apiService.create(mockFile, requests)).thenReturn("created");

		// Mocking getData method behavior
		when(apiService.getData(1, 10, "name")).thenReturn(list);

		// Mocking update method behavior
		when(apiService.update("id", mockFile, requests)).thenReturn(dataResponse);

		// Mocking delete method behavior
		when(apiService.delete("id")).thenReturn(dataResponse);

		// Mocking getLevelManager method behavior
		when(apiService.getLevelManager("id", 0)).thenReturn(dataResponse);

		// Call the create method to be tested
		ResponseEntity<String> createResponse = apiController.create(mockFile, request);
		// Call the getData method to be tested
		ResponseEntity<List<DataResponse>> getDataResponse = apiController.getData(1, 10, "name");
		// Call the update method to be tested
		ResponseEntity<DataResponse> updateResponse = apiController.update("id", mockFile, request);
		// Call the delete method to be tested
		ResponseEntity<DataResponse> deleteResponse = apiController.delete("id");
		// Call the getLevelManager method to be tested
		ResponseEntity<DataResponse> getLevelManagerResponse = apiController.getLevelManager("id", 0);

		// Assertions
		assertNotNull(createResponse);
		assertNotNull(getDataResponse);
		assertNotNull(updateResponse);
		assertNotNull(deleteResponse);
		assertNotNull(getLevelManagerResponse);
	}

}
