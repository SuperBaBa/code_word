package org.jarvis.consumer;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.consumer.mapper.CoffeeMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author:marcus date:2020/7/9
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.jarvis.mapper")
@Slf4j
public class PageHelperDemo implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(PageHelperDemo.class, args);
    }

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        coffeeMapper.findAllWithParam(1, 3).forEach(c -> log.info("Page(1) Coffee {}", c));
    }
}
