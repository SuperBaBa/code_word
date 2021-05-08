package org.jarvis.datasource.dao;

import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author marcus @date 2021/5/8
 **/
public class GenericImpl<T> implements GenericDao<T> {
    private JdbcTemplate jdbcTemplate;
    private JdbcAggregateTemplate aggregateTemplate;

    @Override
    public T insert(T t) {
        return aggregateTemplate.insert(t);
    }

}
