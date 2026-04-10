package com.sandeep.application.service;

import java.util.List;  // or java.util.*;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import com.sandeep.application.model.Employee;
import com.sandeep.application.model.EmployeeDetails;
import com.sandeep.application.repository.EmployeeRepository;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
@Service
public class EmployeeService {

public final EmployeeRepository employeeRepository;
private final RestClientService restClientService;

public EmployeeService(EmployeeRepository employeeRepository, RestClientService restClientService)
{
this.employeeRepository=employeeRepository;
this.restClientService=restClientService;
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


/* other methods call starts */

public List<EmployeeDetails> getEmployeeDetailsFromExternalService() {
    // http://localhost:9090/api/employees-details/{empId} to be called with empId as path variable and also query params for pagination and sorting
    Map<String, Object> pathParams = new HashMap<>();
    pathParams.put("empId", "1");
    // pagination and sorting k params bhi add kr skte ho yaha

// List<EmployeeDetails> employeeDetailsList = restClientService.sendRequest(
//         "http://localhost:9090/api/employees-details/{empId}",
//         HttpMethod.GET,
//         null,
//         null,
//         pathParams,
//         null,  //queryParams,
//         new ParameterizedTypeReference<List<EmployeeDetails>>() {}
// );
// abhi response entity bnana he and proper serialize krna he 
/*
ek wrapper classs bnegi
public class EmployeeDetailsResponse {
    private List<EmployeeDetails> data;

    and then ese serialize krna he
    EmployeeDetailsResponse response = restClientService.sendRequest(
        "http://localhost:9090/api/employees-details/{empId}",
        HttpMethod.GET,
        null,
        headers,
        pathParams,
        null,
        new ParameterizedTypeReference<EmployeeDetailsResponse>() {}
);

List<EmployeeDetails> list = response.getData();
because 
👉 Tum expect kar rahe ho:

[
  { "id": 1, "name": "A" },
  { "id": 2, "name": "B" }
]
👉 But API actually return kar rahi hai:

{
  "data": [
    { "id": 1, "name": "A" },
    { "id": 2, "name": "B" }
  ]
}
❌ Problem
new ParameterizedTypeReference<List<EmployeeDetails>>() {}

👉 Ye sirf array/list JSON ke liye hai
❌ But tumhe mil raha hai object JSON
 */
String response = restClientService.sendRequest(
        "http://localhost:9090/api/employees-details/{empId}",
        HttpMethod.GET,
        null,
        null,
        pathParams,
        null,  //queryParams,
       new ParameterizedTypeReference<String>() {}
);
    System.out.println("Employee details from external service: " + response);
    return null; // or return employeeDetailsList;
}
}