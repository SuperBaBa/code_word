package com.lovelace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * author:marcus date:2020/7/20
 **/
@EnableOpenApi
@SpringBootApplication
public class APIDesignDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(APIDesignDemoApplication.class, args);
    }
}
