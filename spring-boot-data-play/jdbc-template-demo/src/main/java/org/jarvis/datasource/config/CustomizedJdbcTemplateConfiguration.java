package org.jarvis.datasource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;

/**
 * author:tennyson date:2020/7/4
 **/
@Configuration
public class CustomizedJdbcTemplateConfiguration {
    @Bean
    public SimpleJdbcInsert simpleJdbcInsert( JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("FIRST")
                .usingGeneratedKeyColumns("ID");
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate( DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
