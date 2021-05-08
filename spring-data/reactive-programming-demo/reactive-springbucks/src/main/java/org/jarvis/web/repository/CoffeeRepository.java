package org.jarvis.web.repository;

import org.jarvis.consumer.model.Coffee;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.query.Query;
import reactor.core.publisher.Mono;

/**
 * author:marcus date:2020/8/9
 **/
public interface CoffeeRepository extends R2dbcRepository<Coffee, Long> {
    /**
     * 根据名称查询coffee
     *
     * @param name
     * @return
     */
    @Query("select * from t_coffee where name=$1")
    Mono<Coffee> findByName(String name);
}
