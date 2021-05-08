package org.jarvis.datasource.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tennyson
 * @date 2021/5/7-23:54
 */
@ConditionalOnBean(value = HikariDataSource.class)
@Configuration
public class HikariDataSourceConfigration {
}
