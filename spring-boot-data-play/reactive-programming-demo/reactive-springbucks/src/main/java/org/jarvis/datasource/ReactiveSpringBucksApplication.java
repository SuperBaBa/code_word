package org.jarvis.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author tennyson
 * @date 2020/8/8-22:21
 */
@SpringBootApplication
@EnableR2dbcRepositories
public class ReactiveSpringBucksApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveSpringBucksApplication.class, args);
    }
}
