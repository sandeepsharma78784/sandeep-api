
package com.sandeep.application.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateBeanConfig {

    @Bean
    public RestTemplate restTemplate() {

        //  abhi humne simple bean bnayi he 
        // isme or bhi kaam ho skte he

        return new RestTemplate();
    }
}

/*
restTemplate.getInterceptors().add((request, body, execution) -> {
                    String correlationId = MDC.get("X-Correlation-ID");  // ye filter me add kri thi vo he 
                    request.getHeaders().add("X-Correlation-ID", correlationId);
                    return execution.execute(request, body);
                });
*/