package org.jarvis.datasource.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * 配置多个数据源，并使用不同数据源的链接进行操作
 * Springboot数据源自动配置会有三个，优先级依次降低(在出现ClassNoSuch异常时)
 * {@link com.zaxxer.hikari.HikariDataSource}=>
 * {@link org.apache.tomcat.jdbc.pool.DataSource}=>
 * {@link org.apache.commons.dbcp2.BasicDataSource}
 * author:tennyson date:2020/7/4
 **/
@Configuration
public class DataSourceConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "first.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 因为关闭了{@link DataSourceAutoConfiguration} 导致无法自动执行schema.sql/data.sql
     * 因此在自定义{@link DataSource}实例化时，同时获取资源，并执行sql
     *
     * @return
     */
    @Bean(name = "firstDataSource")
    @Primary
    public DataSource firstDataSource() {
        DataSourceProperties firstDataSourceProperties = firstDataSourceProperties();
        firstDataSourceProperties.setSqlScriptEncoding(StandardCharsets.UTF_8);
        firstDataSourceProperties.setSchema(Arrays.asList("classpath:schema.sql"));
        firstDataSourceProperties.setData(Arrays.asList("classpath:data.sql"));
        DataSource dataSource = firstDataSourceProperties.initializeDataSourceBuilder().build();
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        try {
            SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(Collections.singletonList("classpath:schema.sql"));
            factory.afterPropertiesSet();
            populator.addScripts(Objects.requireNonNull(factory.getObject()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DatabasePopulatorUtils.execute(populator, dataSource);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager firstTxManager() {
        return new DataSourceTransactionManager(firstDataSource());
    }

    @Bean(name = "firstJdbcTemplate")
    @Primary
    JdbcTemplate firstJdbcTemplate() {
        return new JdbcTemplate(firstDataSource());
    }

    @Bean
    @ConfigurationProperties(value = "second.datasource")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource secondDataSource() {
        DataSourceProperties secondDataSourceProperties = secondDataSourceProperties();
        secondDataSourceProperties.setSqlScriptEncoding(StandardCharsets.UTF_8);
        return secondDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public PlatformTransactionManager secondTxManager() {
        return new DataSourceTransactionManager(secondDataSource());
    }

    @Bean
    JdbcTemplate secondJdbcTemplate() {
        return new JdbcTemplate(secondDataSource());
    }
}
