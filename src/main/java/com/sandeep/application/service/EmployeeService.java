package com.sandeep.application.service;

import java.util.List;  // or java.util.*;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import com.sandeep.application.model.Employee;
import com.sandeep.application.model.EmployeeDetails;
import com.sandeep.application.model.EmployeeDetailsResponse;
import com.sandeep.application.repository.EmployeeRepository;
import org.springframework.http.HttpMethod;
import org.springframework.core.ParameterizedTypeReference;
import com.sandeep.application.model.ThirdPartyUser;
import java.util.Collections;
import com.sandeep.application.genericexceptions.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
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

public List<EmployeeDetails> getEmployeeDetailsListFromExternalService() {
    // http://localhost:9090/api/employees-details/{empId} to be called with empId as path variable and also query params for pagination and sorting
    Map<String, Object> pathParams = new HashMap<>();
    pathParams.put("empId", "1");
    // pagination and sorting k params bhi add kr skte ho yaha

/*
this String wala is the best way to check the reposne
we can sop the the string so api response ko string me 
convert karo and sop kr do and format check kro uske baad idr resonseobject class bnao.

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
    // o/p : Employee details from external service: {"empId":"1","familyName":"satish","hike":20.0,"id":1}
   return null; // or return employeeDetailsList;
*/
/*
phle string me cast kr rahe the 
bhi humne custom class bna liya EmployeeDetailsResponse accroding to response come 
jisme List<EmployeeDetails> 
data field hai to usme cast krna padega taki hume employee details ki list mil jaye 
*/

/*
EmployeeDetailsResponse response = restClientService.sendRequest(
        "http://localhost:9090/api/employees-details/{empId}",
        HttpMethod.GET,
        null,   // it will be for body 
        null,  // it will be for header params
        pathParams,
        null,   // it will be for query params
        new ParameterizedTypeReference<EmployeeDetailsResponse>() {}
);
System.out.println("Employee details from external service: " + response);

List<EmployeeDetails> employeeDetailsList = response.getData();
return employeeDetailsList;
*/
return null;
}

public EmployeeDetails getEmployeeDetailsFromExternalService() {
Map<String, Object> pathParams = new HashMap<>();
    pathParams.put("empId", "1");

EmployeeDetails response = restClientService.sendRequest(
        "http://localhost:9090/api/employees-details/{empId}",
        HttpMethod.GET,
        null,   // it will be for body 
        null,  // it will be for header params
        pathParams,// it will be for path params
        null,   // it will be for query params
        new ParameterizedTypeReference<EmployeeDetails>() {}
);
System.out.println("Employee details from external service: " + response);


return response;

}

public List<ThirdPartyUser> getExternalUsers() {

try {
            List<ThirdPartyUser> users = restClientService.sendRequest(
                    // "https://jsonplaceholder.typicode.com/usenrs", to test 404 
                    //  500 k liye db close kr do 
                    "https://jsonplaceholder.typicode.com/users",
                    HttpMethod.GET,
                    null,
                    Collections.emptyMap(),   // headers
                    Collections.emptyMap(),   // path params
                    Collections.emptyMap(),   // query params
                    new ParameterizedTypeReference<List<ThirdPartyUser>>() {}
            );

            if(users != null) {
                System.out.println("External users: " + users);
                users.forEach(user -> System.out.println("User: " + user.getName() + ", Email: " + user.getEmail()));
            } else {
                System.out.println("Failed to fetch external users.");
            }
            return users != null ? users : Collections.emptyList();
    }catch (HttpClientErrorException.NotFound ex) {
                throw new ResourceNotFoundException("External API: Resource not found");

            } catch (HttpServerErrorException ex) {
                throw new ExternalServiceException("External API is down");

            } catch (Exception ex) {
                throw new ExternalServiceException("Failed to fetch data from external API");
            }
}
}