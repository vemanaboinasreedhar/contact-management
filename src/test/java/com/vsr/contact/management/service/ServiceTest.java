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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
		Page<Employee> mockedPage = new PageImpl<>(List.of(employee));
		PageRequest pageRequest = PageRequest.of(1, 10, Sort.by("name"));
		// Mocking behavior
		when(employeeRepository.findAll(pageRequest)).thenReturn(mockedPage);

		// Call the method to be tested
		List<DataResponse> result = apiService.getData(1, 10, "name");

		// Assertions
		assertNotNull(result);
	}

	@Test
	public void updateTest() {
		// Prepare test data
		byte[] fileBytes = "Some file content".getBytes();
		MockMultipartFile mockFile = new MockMultipartFile("file", "filename.txt", "text/plain", fileBytes);
		byte[] fileBytes1 = "Some file content".getBytes();
		MockMultipartFile mockFile1 = new MockMultipartFile("file", "filename.txt", "text/plain", fileBytes1);
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
	public void getLevelManagerTest() {
		// Prepare test data
		UUID uuid = UUID.randomUUID();
		List<Employee> list = new ArrayList<>();
		Employee employee = new Employee();
		employee.setId(uuid.toString());
		employee.setEmployeeName("employeeName");
		employee.setReportsTo(uuid.toString());
		list.add(employee);

		// Mocking behavior of findById
		when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));
		// Prepare test data
		// Mocking behavior
		when(employeeRepository.findAll()).thenReturn(list);

		// Call the method to be tested
		DataResponse result = apiService.getLevelManager(uuid.toString(), 1);
		// Assertions
		assertNotNull(result);

	}

	@Test
	public void getLevelManagerNegativeTest() {
		// Prepare test data
		UUID uuid = UUID.randomUUID();
		List<Employee> list = new ArrayList<>();
		Employee employee = new Employee();
		employee.setId(uuid.toString());
		employee.setEmployeeName("employeeName");

		list.add(employee);

		// Mocking behavior of findById
		when(employeeRepository.findById(uuid)).thenReturn(Optional.of(employee));
		// Prepare test data
		// Mocking behavior
		when(employeeRepository.findAll()).thenReturn(list);
		for (int i = 0; i <= 10; i++) {
			int j = 1+i;
			// Assertions for exception handling
			NotFound exception = assertThrows(NotFound.class, () -> apiService.getLevelManager(uuid.toString(), j));
			// Assertions
			assertNotNull(exception);
		}
		
		

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
		// Assertions for exception handling
		NotFound levelException = assertThrows(NotFound.class, () -> apiService.getLevelManager(uuid.toString(), 1));

		NotFound deleteException = assertThrows(NotFound.class, () -> apiService.delete(uuid.toString()));
		assertEquals("Employee not found with id : " + uuid.toString(), updateException.getMessage());
		assertEquals("Employee not found with id : " + uuid.toString(), deleteException.getMessage());
		assertEquals("Employee not found with id : " + uuid.toString(), levelException.getMessage());
	}
}
