package org.jarvis.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 1. 内存数据库初始化，通过resource两资源文件进行数据初始化
 * + schema 初始化表， data 初始化数据
 * 2. SpringBoot自动配置的DataSource和JdbcTemplate
 * 通过日志可以看出，默认配置的是HikariDataSource
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
        H2ConsoleAutoConfiguration.class})
@Slf4j
@EnableJpaRepositories
public class DataSourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataSourceDemoApplication.class, args);
    }

}
