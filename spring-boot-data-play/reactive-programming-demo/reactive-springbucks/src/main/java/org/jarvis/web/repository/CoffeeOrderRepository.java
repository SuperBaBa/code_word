package org.jarvis.web.repository;

import org.jarvis.consumer.model.CoffeeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

/**
 * @author marcus
 * @date 2020/8/9-8:06
 */
@Repository
public class CoffeeOrderRepository {
    @Autowired
    private DatabaseClient databaseClient;

    /**
     * 新建coffee订单并关联order与coffee
     *
     * @param order
     * @return
     */
    public Mono<Long> save(CoffeeOrder order) {
        //TODO 如果出现事务问题，是否会回滚
        return databaseClient.insert().into("T_COFFEE_ORDER")
                .value("customer", order.getCustomer())
                .value("state", order.getState().ordinal())
                .value("create_time", new Timestamp(order.getCreateTime().getTime()))
                .value("update_time", new Timestamp(order.getUpdateTime().getTime()))
                .fetch()
                .first()
                .flatMap(m -> Mono.just((Long) m.get("ID")))
                .flatMap(id -> Flux.fromIterable(order.getItems())
                        .flatMap(c -> databaseClient.insert().into("t_order_coffee")
                                .value("coffee_order_id", id)
                                .value("items_id", c.getId())
                                .then()).then(Mono.just(id)));
    }
}
