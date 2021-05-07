package org.jarvis.consumer.concurrent;

import org.jarvis.consumer.concurrent.spring.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

/**
 * author:marcus date:2020/7/28
 **/
@SpringBootApplication
public class ConcurrentApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(ConcurrentApplication.class, args);
    }

    @Autowired
    private TaskService taskService;

    @Override
    public void run(String... args) throws Exception {
        List<String> list = Arrays.asList("yto", "zto", "sto", "yd");
        List<String> reusltlist = new ArrayList<>();
        Future<List<String>> future = null;
        for (String name : list) {
            future = taskService.asyncMethodWithReturnType(name, reusltlist);
        }
        future.get().forEach(System.out::println);
    }


}
