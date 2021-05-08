package org.jarvis.service;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.entity.Coffee;
import org.jarvis.repository.CoffeeOrderRepository;
import org.jarvis.repository.CoffeeRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:tennyson date:2020/7/5
 **/
@Service
@Slf4j
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    CoffeeOrderRepository orderRepository;

    /*初始化部分咖啡存入菜单*/
    public void initCoffee() {
        //浓咖啡存入菜单
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        coffeeRepository.save(espresso);
        log.info("Coffee: {}", espresso);
        //拿铁存入菜单
        Coffee latte = Coffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .build();
        coffeeRepository.save(latte);
        log.info("Coffee: {}", latte);
        //cappuccino
        Coffee cappuccino = Coffee.builder()
                .name("cappuccino")
                .price(Money.of(CurrencyUnit.of("CNY"), 18.9))
                .build();
        log.info("Coffee: {}", cappuccino);
    }
}
