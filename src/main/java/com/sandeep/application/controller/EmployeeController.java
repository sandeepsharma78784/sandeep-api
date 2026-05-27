package com.sandeep.application.controller;

import java.util.Collections;
import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sandeep.application.model.Employee;
import com.sandeep.application.model.EmployeeDetails;
import com.sandeep.application.service.EmployeeService;

import com.sandeep.application.model.ThirdPartyUser;

import com.sandeep.application.dto.APIResponseFormat;

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
        /*
        List<EmployeeDetails> employeeDetailsList = employeeService.getEmployeeDetailsListFromExternalService();
        if(employeeDetailsList == null || employeeDetailsList.isEmpty()) {
            System.out.println("No employee details found from external service.");
        } else {
            System.out.println("Employee details fetched from external service:");
            employeeDetailsList.forEach(emp -> System.out.println("Employee Details: " + emp.getEmpId() + ", " + emp.getFamilyName() + ", " + emp.getHike()));
        }
        */
         /* just to test the external call
        testing k liye abhi is ms ki list call me dusri ms ki 
        getEmployeeDetails wala endpoint call kiya he */
       EmployeeDetails employeeDetails = employeeService.getEmployeeDetailsFromExternalService();
       System.out.println("Fetched Employee Details: " + employeeDetails.getEmpId() + ", " + employeeDetails.getFamilyName() + ", " + employeeDetails.getHike());
        
       if(employeeDetails.getEmpId()==null){
        return Collections.emptyList();
       }
        
         return employeeService.getAllEmployees();
    } 

    // @GetMapping("/external", produces = "application/json") // is pr 406 aaya tha dont got the reason.
    @GetMapping("/external")
    public ResponseEntity<APIResponseFormat<List<ThirdPartyUser>>> getAllThirdPartyUsers(){
        // call third party api
        List<ThirdPartyUser> thirdPartyUsers = employeeService.getExternalUsers();
         return ResponseEntity.ok(
            new APIResponseFormat<>(200, "Users fetched successfully", thirdPartyUsers, null)
        );
    }
}