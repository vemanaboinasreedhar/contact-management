package com.vsr.contact.management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vsr.contact.management.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID>{

}
