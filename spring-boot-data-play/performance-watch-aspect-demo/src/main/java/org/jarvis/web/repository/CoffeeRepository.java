package org.jarvis.web.repository;

import org.jarvis.consumer.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * author:tennyson  date:2020/8/8
 */
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
