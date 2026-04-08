// package com.sandeep.application.config;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import java.awt.Desktop;
// import java.net.URI;


// package com.sandeep.application.config;

// import org.springframework.context.event.EventListener;
// import org.springframework.stereotype.Component;
// import org.springframework.boot.context.event.ApplicationReadyEvent;


// import org.springframework.context.event.EventListener;
// import org.springframework.stereotype.Component;
// import org.springframework.boot.context.event.ApplicationReadyEvent;
// import java.awt.Desktop;
// import java.net.URI;

// @Component
// public class SwaggerAutoOpenConfig {

//     @EventListener(ApplicationReadyEvent.class)
//     public void openSwagger() {
//         try {
//             String url = "http://localhost:8080/swagger-ui/index.html";

//             if (Desktop.isDesktopSupported()) {
//                 Desktop.getDesktop().browse(new URI(url));
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
// @Configuration
// public class SwaggerAutoOpenConfig  {

//     @Bean
//     public CommandLineRunner openSwagger() {
//         return args -> {
//             try {
//                 String url = "http://localhost:8080/swagger-ui/index.html";

//                 if (Desktop.isDesktopSupported()) {
//                     Desktop.getDesktop().browse(new URI(url));
//                 }
//             } catch (Exception e) {
//                 e.printStackTrace();
//             }
//         };
//     }
// }

package com.sandeep.application.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SwaggerAutoOpenConfig {

    @EventListener(ApplicationReadyEvent.class)
    public void openSwagger() {
        try {
            String url = "http://localhost:8080/swagger-ui/index.html";

            Runtime.getRuntime().exec(
                "rundll32 url.dll,FileProtocolHandler " + url
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}