package org.jarvis.consumer.dao;

import org.jarvis.consumer.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author marcus
 * @date 2020/8/21-14:35
 */
@Repository
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Coffee findByName(String name);

    List<Coffee> findByNameInOrderById(List<String> names);
}
