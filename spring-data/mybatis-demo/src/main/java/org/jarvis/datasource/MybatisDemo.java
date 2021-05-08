package org.jarvis.datasource;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.datasource.entity.Coffee;
import org.jarvis.datasource.mapper.CoffeeMapper;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * author:tennyson date:2020/7/7
 **/
@SpringBootApplication
@Slf4j
@MapperScan("com.jarvis.mapper")
public class MybatisDemo implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(MybatisDemo.class, args);
    }

    @Resource(type = CoffeeMapper.class)
    private CoffeeMapper coffeeMapper;

    @Override
    public void run(String... args) throws Exception {
        Coffee espresso = Coffee.builder().name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0)).build();
        coffeeMapper.save(espresso);
        log.info("findById {}query coffee{}", 1L, coffeeMapper.findById(1L));
    }
}
