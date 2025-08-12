package com.sandeep.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sandeep.application.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}