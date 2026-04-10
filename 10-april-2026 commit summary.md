task: A service running on localhost:9090 and task is to call its endpoint 
        api/employees-details/{empId} , so the actual url will be 
        http://localhost:9090/api/employees-details/{empId}
Solution: 
    it can be done in two ways
    Spring Boot approach using RestTemplate (blocking) and a modern approach using WebClient (reactive).

    In a Spring Cloud-based microservices architecture, you typically use RESTful HTTP calls for communication between microservices. Spring Cloud provides various components to facilitate this communication, such as Spring Cloud Netflix's Feign, RestTemplate, WebClient, or Spring Cloud OpenFeign.
    RestTemplate and Feign are both widely used in Spring applications for making HTTP requests to RESTful services. Each has its strengths and weaknesses, and the choice between them depends on various factors such as ease of use, flexibility, and specific requirements of your application.
    'Use Cases:
        Use RestTemplate When:
            You need fine-grained control over HTTP requests and responses.
            Your application requires synchronous and asynchronous HTTP calls.
            You are working on an existing codebase where RestTemplate is already used extensively.
        Use Feign When:
            You want to reduce boilerplate code and define REST clients declaratively.
            Your application is built on a microservices architecture and can benefit from integration with Ribbon and Hystrix.
            You prefer a more opinionated approach and are willing to sacrifice some flexibility for productivity.
*****************************************************
Abhi hum RestTemplate use krege
1. a) Define RestTemplate bean 
2. b) Service method to call your endpoint, ek service class bnayege jisme ek method hogi sendRequest 
        naam ki.
        Resttemplate ki 2 methods he getforobject and exchange dono ka diff
        | Feature         | getForObject | exchange |
        | --------------- | ------------ | -------- |
        | HTTP Methods    | Only GET     | ALL      |
        | Headers         | ❌            | ✅        |
        | Request Body    | ❌            | ✅        |
        | Response Status | ❌            | ✅        |
        | Flexibility     | Low          | High     |
        💣 Real Production Advice (Important for YOU)
            Tum microservices bana rahe ho, to:
                👉 Direct getForObject() avoid karo
                👉 Always use:
                exchange() OR
                WebClient
3. ek service bnayege RestClientService jo ki dusri microservice ki method call krne k liye use hogi
    service generic hogi and parameterized hogi.

************************************
Basically humko 2 kaam krna he
1. basically DB + external API data ko merge (combine) karna chahte ho.
        Ye real microservices pattern hai 🔥 (Aggregator pattern)
2. external API data k basis pr kuch decision leke idr db me operation ko decid krna he.


************




note: how we resolved serialized error: for the time being string liya he 
next phase me proper resonse entity bna k kaam krege
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


 // abhi is baar exception handling krte he.
 ek api response naam ki clas bnayege and 
 ek proper response format bna k response ko uska data wale part me map kiya
