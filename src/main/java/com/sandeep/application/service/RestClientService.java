
package com.sandeep.application.service;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RestClientService {

    private final RestTemplate restTemplate;

    public RestClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

/*
Agar tumko List<EmployeeDetails> chahiye, to Class<R> kaafi nahi hai ❌
Use ParameterizedTypeReference ✔️
also method ko generic bnaya taki sabhi k liye kaam kre
 */
    public <T, R> R sendRequest(
            String url,
            HttpMethod method,
            T requestBody,
            Map<String, String> headers,
            Map<String, Object> pathParams,
            Map<String, Object> queryParams,
            ParameterizedTypeReference<R> responseType     //Class<R> responseType
    ) {

        // 1️⃣ Headers build
        HttpHeaders httpHeaders = new HttpHeaders();
        if (headers != null) {
            headers.forEach(httpHeaders::set);
        }

        // 2️⃣ Query params attach, ye url ka part hoge
        if (queryParams != null && !queryParams.isEmpty()) {
            StringBuilder queryString = new StringBuilder("?");
            queryParams.forEach((k, v) ->
                    queryString.append(k).append("=").append(v).append("&")
            );
            url += queryString.substring(0, queryString.length() - 1);
        }

        // 3️⃣ Request Entity (body + headers)
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);

        // 4️⃣ Exchange call
        ResponseEntity<R> response = restTemplate.exchange(
                url,
                method,
                entity,
                responseType,
                pathParams
        );

        return response.getBody();
    }
}