package com.sandeep.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;


//  below imports for CORS configuration
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    // new way code with lambdas
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
         .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(auth -> auth
            // .requestMatchers("/public/**").permitAll()
            // .requestMatchers("/api/**").authenticated()  //anyrequest.authenticated() conver kr lega explicitly likhne ki jarurat nhi hai.
             .requestMatchers("/public/**").permitAll()
            // https://domain:port/public/... yeh sab access hoga bina authentication ke

            //  abki saare endopints ke liye authentication jaruri hai and token aana chahiye request me
            //  humne ui se token nahi bheja isliye idr se 401 jaa raha he.
            .anyRequest().authenticated()
        )
        //.oauth2ResourceServer(oauth2 -> oauth2.jwt());
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> 
            jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
        ));
    return http.build();
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthoritiesClaimName("permissions"); // kuch bhi ho sakta hai yeh claim name
        authoritiesConverter.setAuthorityPrefix("");// since we will use hasauthority() not hasrole() so not prefix reqire.

        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        // Authenitcation converter jo ki jwt se authorities nikalta hai
        // iska objet banaya and JwtGrantedAuthoritiesConverter set kiya and return kiya.
        converter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);  // 
        return converter;
    }

      @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Angular URL
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // Authorization header included
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}