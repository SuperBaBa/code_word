package org.jarvis.repository;

import org.jarvis.entity.CoffeeOrder;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 按照spring-jpa语法定义三个查询方法
 */
@Repository
public interface CoffeeOrderRepository extends PagingAndSortingRepository<CoffeeOrder, Long> {
    /**
     *
     * @return
     */
    List<CoffeeOrder> findTop3ByOrderByUpdateTimeDescIdAsc();
    /**
     * 根据客户查询订单，并通过
     * @param customer
     * @return
     */
    List<CoffeeOrder> findByCustomerOrderById(String customer);

    /**
     *根据Items的Name进行查询，如查询cappuccino，然后查询出所有含有cappuccino的订单
     * 为了消除不确定性，可以在方法名内使用下划线“_”手动定义隔断点。
     * @param name Items的name
     * @return 订单集合
     */
    List<CoffeeOrder> findByItems_Name(String name);

    /**
     *
     * @param id
     * @return
     */
    @Async
    Optional<CoffeeOrder> findById(Long id);

}
