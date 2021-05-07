package org.jarvis.web.repository;

import org.jarvis.consumer.model.Coffee;
import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
/** @author marcus **/
public interface CoffeeRepository extends ReactiveCrudRepository<Coffee, Long> {
    /**
     * 根据姓名查找t_coffee表中的数据
     * @param name name of coffee
     * @return The coffee sequence from 0 to 1 {@link Flux}
     */
    @Query("select * from t_coffee where name = $1")
    Flux<Coffee> findByName(String name);
}
