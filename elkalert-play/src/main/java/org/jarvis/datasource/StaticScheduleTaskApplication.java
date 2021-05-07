package org.jarvis.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * author:tennyson date:2020/6/15
 **/
@EnableScheduling
@SpringBootApplication
public class StaticScheduleTaskApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(StaticScheduleTaskApplication.class, args);
        try {
            System.out.println("SpringBoot name is " + run.getEnvironment().getProperty("springboot.name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("===================================================");

        // 运行结束进行关闭操作
//        run.close();
    }
}
