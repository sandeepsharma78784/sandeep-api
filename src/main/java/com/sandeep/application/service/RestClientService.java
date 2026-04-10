
package com.sandeep.application.service;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import org.springframework.util.CollectionUtils;

// to create manual multimap 
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
       /*
        if (queryParams != null && !queryParams.isEmpty()) {
            StringBuilder queryString = new StringBuilder("?");
            queryParams.forEach((k, v) ->
                    queryString.append(k).append("=").append(v).append("&")
            );
            url += queryString.substring(0, queryString.length() - 1);
        }
        Query params build karte waqt URL encoding nahi ho raha ❌
        */
       // do the step 2 using encoder 
       /*
       CollectionUtils.toMultiValueMap() expects:

        Map<String, List<Object>> ❌

        But tum de rahe ho:

        Map<String, Object> ❌

        FIX: Manual MultiValueMap banao
        import org.springframework.util.LinkedMultiValueMap;
        import org.springframework.util.MultiValueMap;
        MultiValueMap<String, String> queryParamMap = new LinkedMultiValueMap<>();

        if (queryParams != null) {
            queryParams.forEach((k, v) -> 
                queryParamMap.add(k, String.valueOf(v))
            );
        }
        */
        //  URI uri = UriComponentsBuilder.fromHttpUrl(url)
        //     .queryParams(queryParams != null ? CollectionUtils.toMultiValueMap(queryParams) : null)
        //     .buildAndExpand(pathParams != null ? pathParams : Map.of())
        //     .encode()
        //     .toUri();
        MultiValueMap<String, String> queryParamMap = new LinkedMultiValueMap<>();

        if (queryParams != null) {
            queryParams.forEach((k, v) -> 
                queryParamMap.add(k, String.valueOf(v))
            );
        }

        // Build URI (path + query)
    // is se path param and query param ka kaam ho jayega
// exchange method ko resole krne ki jarurat nahi hai kyuki uri builder khud hi resolve kr dega path param ko
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
            .queryParams(queryParamMap)
            .buildAndExpand(pathParams != null ? pathParams : Map.of())
            .encode()
            .toUri();

        // 3️⃣ Request Entity (body + headers)
        HttpEntity<T> entity = new HttpEntity<>(requestBody, httpHeaders);

        // 4️⃣ Exchange call
        ResponseEntity<R> response = restTemplate.exchange(
                uri,  // url,
                method,
                entity, // reuest body  + headers
                responseType // response type class
        );

        return response.getBody();
    }
}