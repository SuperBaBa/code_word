package org.jarvis.web.service;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.consumer.dao.CoffeeOrderRepository;
import org.jarvis.consumer.model.Coffee;
import org.jarvis.consumer.model.CoffeeOrder;
import org.jarvis.consumer.model.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author marcus
 * @date 2020/8/24-14:55
 */
@Slf4j
@Service
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public CoffeeOrder get(Long id) {
        return coffeeOrderRepository.getOne(id);
    }

    public CoffeeOrder createOrder(String customer, Coffee... coffee) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffee)))
                .orderState(OrderState.INIT)
                .build();
        CoffeeOrder saved = coffeeOrderRepository.save(order);
        log.info("New Order: {}", saved);
        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getOrderState()) <= 0) {
            log.warn("Wrong State order: {}, {}", state, order.getOrderState());
            return false;
        }
        order.setOrderState(state);
        coffeeOrderRepository.save(order);
        log.info("Updated Order: {}", order);
        return true;
    }
}
