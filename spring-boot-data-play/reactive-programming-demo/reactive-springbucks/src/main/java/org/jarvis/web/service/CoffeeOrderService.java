package org.jarvis.web.service;

import org.jarvis.consumer.model.CoffeeOrder;
import org.jarvis.web.repository.CoffeeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author marcus
 * @date 2020/8/9-9:05
 */
@Service
public class CoffeeOrderService {
    @Autowired
    private CoffeeOrderRepository repository;
    @Autowired
    private DatabaseClient client;

    /**
     * 新增一个coffee订单，并返回一个订单ID
     *
     * @param order
     * @return
     */
    public Mono<Long> create(CoffeeOrder order) {
        return repository.save(order);
    }
}
