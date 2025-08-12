package com.sandeep.application.service;

import java.util.List;  // or java.util.*;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.sandeep.application.model.Employee;
import com.sandeep.application.repository.EmployeeRepository;

@Service
public class EmployeeService {

public final EmployeeRepository employeeRepository;

public EmployeeService(EmployeeRepository employeeRepository)
{
this.employeeRepository=employeeRepository;
}


// lets add method for getall and add

// create or updata
public Employee saveEmployee(Employee employee)
{
return employeeRepository.save(employee);
}

//read all
public List<Employee> getAllEmployees() {

return employeeRepository.findAll();
}

}