package org.jarvis.consumer.ssl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
@EnableOpenApi
public class SSLPlayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SSLPlayApplication.class, args);
    }
}
