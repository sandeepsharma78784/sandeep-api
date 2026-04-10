package com.sandeep.application.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sandeep.application.model.Employee;
import com.sandeep.application.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Create
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {

        return employeeService.saveEmployee(employee);
    }

    // Read all
    @GetMapping
    public List<Employee> getAllEmployees() {
        System.out.println("Fetching all employees");
        employeeService.getEmployeeDetailsFromExternalService(); // just to test the external call
        // testing k liye abhi is ms ki list call me dusri ms ki 
        // getEmployeeDetails wala endpoint call kiya he
         return employeeService.getAllEmployees();
    } 
}