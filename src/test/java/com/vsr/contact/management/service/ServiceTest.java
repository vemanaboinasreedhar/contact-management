package com.vsr.contact.management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.vsr.contact.management.entity.ApiRequest;
import com.vsr.contact.management.entity.DataResponse;
import com.vsr.contact.management.entity.Employee;
import com.vsr.contact.management.exception.BadRequest;
import com.vsr.contact.management.exception.NotFound;
import com.vsr.contact.management.repository.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

	@InjectMocks
	private ApiService apiService;

	@Mock
	private EmployeeRepository employeeRepository;

	@Test
	public void createTest() {
		// Prepare test data
		byte[] fileBytes = "Some file content".getBytes();
		MockMultipartFile mockFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileBytes);
		ApiRequest request = new ApiRequest();
		request.setEmployeeName("employeeName");
		Employee emp = new Employee();
		emp.setEmployeeName("employeeName");
		Employee createdEmployee = new Employee();
		createdEmployee.setId("1234567899asdf1234");
		// Mocking behavior
		when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(createdEmployee);

		// Call the method to be tested
		String result = apiService.create(mockFile, request);
		// Assertions
		assertNotNull(result);
	}

	@Test
	public void createFailureTest() throws IOException {
		// Prepare test data
		MultipartFile file = spy(
				new MockMultipartFile("file", "filename.txt", "text/plain", "Mocked file content".getBytes()));

		// Mock specific behavior inside the method
		doThrow(new IOException("Mocked IOException")).when(file).getBytes();

		// Assertions for exception handling
		BadRequest exception = assertThrows(BadRequest.class, () -> apiService.create(file, mock(ApiRequest.class)));

		assertEquals("Error converting file to byte array", exception.getMessage());

	}

	@Test
	public void getDataTest() {
		// Prepare test data
		List<Employee> list = new ArrayList<>();
		Employee employee = new Employee();
		employee.setId("1234567899asdf1234");
		list.add(employee);
		// Mocking behavior
		when(employeeRepository.findAll()).thenReturn(list);

		// Call the method to be tested
		List<DataResponse> result = apiService.getData();

		// Assertions
		assertNotNull(result);
	}

	@Test
	public void updateTest() {
		// Prepare test data
		byte[] fileBytes = "Some file content".getBytes();
		MockMultipartFile mockFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileBytes);
		ApiRequest request = new ApiRequest();
		request.setEmployeeName("employeeName");
		request.setEmail("sdfghj");
		request.setPhoneNumber("123456789");
		request.setReportsTo("sdfghj56756325");
		Employee emp = new Employee();

		// Mocking behavior of findById
		UUID uuid = UUID.randomUUID();
		when(employeeRepository.findById(uuid)).thenReturn(Optional.of(emp));

		// Mocking behavior
		when(employeeRepository.saveAndFlush(any(Employee.class))).thenReturn(emp);

		// Call the method to be tested
		DataResponse result = apiService.update(uuid.toString(), mockFile, request);
		DataResponse negativeResult = apiService.update(uuid.toString(), mockFile, mock(ApiRequest.class));
		// Assertions
		assertNotNull(result);
		assertNotNull(negativeResult);
	}

	@Test
	public void updateFailureTest() throws IOException {
		// Prepare test data
		MultipartFile file = spy(
				new MockMultipartFile("file", "filename.txt", "text/plain", "Mocked file content".getBytes()));
		UUID uuid = UUID.randomUUID();
		// Mock specific behavior inside the method
		doThrow(new IOException("Mocked IOException")).when(file).getBytes();

		// Assertions for exception handling
		BadRequest exception = assertThrows(BadRequest.class,
				() -> apiService.update(uuid.toString(), file, mock(ApiRequest.class)));

		assertEquals("Error converting file to byte array", exception.getMessage());

	}

	@Test
	public void deleteTest() {
		// Prepare test data
		Employee emp = new Employee();
		emp.setEmployeeName("employeeName");
		// Mocking behavior of findById
		UUID uuid = UUID.randomUUID();
		when(employeeRepository.findById(uuid)).thenReturn(Optional.of(emp));
		// Call the method to be tested
		DataResponse result = apiService.delete(uuid.toString());

		// Assertions
		assertNotNull(result);

	}

	@Test
	public void notFoundExceptionTest() throws IOException {
		// Prepare test data
		byte[] fileBytes = "Some file content".getBytes();
		MockMultipartFile mockFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileBytes);
		UUID uuid = UUID.randomUUID();
		// Mocking behavior of findById
		when(employeeRepository.findById(uuid)).thenReturn(Optional.empty());

		// Assertions for exception handling
		NotFound updateException = assertThrows(NotFound.class,
				() -> apiService.update(uuid.toString(), mockFile, mock(ApiRequest.class)));

		NotFound deleteException = assertThrows(NotFound.class, () -> apiService.delete(uuid.toString()));
		assertEquals("not found", updateException.getMessage());
		assertEquals("not found", deleteException.getMessage());

	}
}
