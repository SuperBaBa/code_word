package org.jarvis.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 * 配置自定义的
 * author:tennyson date:2020/7/4
 **/
@Configuration
public class CustomizedJdbcTemplateConfiguration {
    @Bean
    public JdbcTemplate jdbcTemplate(HikariDataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public SimpleJdbcInsert simpleJdbcInsert(JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("T_STUDENTS")
                .usingGeneratedKeyColumns("ID");
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DruidDataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
