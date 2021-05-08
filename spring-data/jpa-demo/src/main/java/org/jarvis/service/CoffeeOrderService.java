package org.jarvis.service;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.constant.OrderState;
import org.jarvis.entity.Coffee;
import org.jarvis.entity.CoffeeOrder;
import org.jarvis.repository.CoffeeOrderRepository;
import org.jarvis.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:tennyson date:2020/7/5
 **/
@Service
@Slf4j
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;
    @Autowired
    private CoffeeRepository coffeeRepository;

    /*初始化几个订单*/
    public void initCoffeeOrder() {
        Coffee espresso = coffeeRepository.findById(1L).get();
        log.info("select coffee from menu , coffee => {}", espresso);
        //新建coffee订单
        CoffeeOrder order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Collections.singletonList(espresso))
                .state(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order: {}", order);
        Coffee latte = coffeeRepository.findById(2L).get();
        log.info("select coffee from menu , coffee => {}", latte);

        order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Arrays.asList(espresso, latte))
                .state(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order: {}", order);
    }

    public void findOrders() {
        coffeeRepository
                .findAll()
                .forEach(c -> log.info("Loading {}", c));

        List<CoffeeOrder> list = coffeeOrderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("findTop3ByOrderByUpdateTimeDescIdAsc: {}", getJoinedOrderId(list));

        list = coffeeOrderRepository.findByCustomerOrderById("Li Lei");
        log.info("findByCustomerOrderById: {}", getJoinedOrderId(list));

        // 不开启事务会因为没Session而报LazyInitializationException
        //TODO 为什么会出现LazyInitializationException，必须开启事务吗
        list.forEach(o -> {
            log.info("Order {}", o.getId());
            o.getItems().forEach(i -> log.info("  Item {}", i));
        });

        list = coffeeOrderRepository.findByItems_Name("latte");
        log.info("findByItems_Name: {}", getJoinedOrderId(list));
    }

    private String getJoinedOrderId(List<CoffeeOrder> list) {
        return list.stream().map(o -> o.getId().toString())
                .collect(Collectors.joining(","));
    }
}
