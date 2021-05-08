package org.jarvis.repository;

import org.jarvis.entity.Coffee;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * 接口继承于CrudRepository 或者 PagingAndSortingRepository，JpaRepository,Repository
 * 或者利用注释的方式表名继承于JpaRepository
 * 这两种是等价的
 * author:tennyson date:2020/7/5
 **/
@RepositoryDefinition(domainClass = Coffee.class, idClass = Long.class)
public interface CoffeeRepository extends BaseReposity<Coffee, Long> {
}
