package org.jarvis.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * author:tennyson date:2020/6/22
 **/
@SpringBootApplication
public class WebApplicaiton {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WebApplicaiton.class, args);
    }
}
