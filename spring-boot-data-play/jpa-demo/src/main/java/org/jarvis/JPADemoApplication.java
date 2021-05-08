package org.jarvis;

import org.jarvis.service.CoffeeOrderService;
import org.jarvis.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

/**
 * 本模块使用spring jpa实现java持久化，依然采用H2内存数据库，hibernate进行表生成
 * 1、通过hibernate生成表
 * 2、使用Hibernate的语法定义查询语句，e.g：find...by..
 * 3、repository的初始化为bean
 * 4、使用jpa注解继承BaseEntity 同时定义 BaseRepository
 * author:tennyson date:2020/7/5
 **/
@SpringBootApplication
@EnableJpaRepositories
public class JPADemoApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(JPADemoApplication.class, args);
    }
    @Autowired
    private CoffeeService coffeeService;
    @Autowired
    private CoffeeOrderService orderService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        coffeeService.initCoffee();
        orderService.initCoffeeOrder();
        orderService.findOrders();
    }
}
