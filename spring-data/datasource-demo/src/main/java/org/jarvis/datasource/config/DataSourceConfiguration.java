package org.jarvis.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


/**
 * 默认情况下，spring-boot-autoconfigure使用的是{@link HikariDataSource}数据源
 * 配置两个数据源，分别配置成{@link DruidDataSource}{@link HikariDataSource}两个数据源
 *
 * @author Marcus
 * @date 2021/5/7-15:03
 */
@Configuration
@Slf4j
public class DataSourceConfiguration {
    @Bean
    @ConfigurationProperties("first.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Resource
    public DruidDataSource firstDataSource(@Qualifier("firstDataSourceProperties") DataSourceProperties properties) {
        log.info("first datasource: {}", properties.getUrl());
        DruidDataSource druidDataSource = new DruidDataSource();
        properties.setSqlScriptEncoding(StandardCharsets.UTF_8);
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());
        druidDataSource.setTestWhileIdle(false);
        runScript(druidDataSource, Collections.singletonList("classpath*:schema-druid.sql"));
        return druidDataSource;
    }

    @Bean
    @Resource
    public PlatformTransactionManager firstTxManager(DruidDataSource firstDataSource) {
        return new DataSourceTransactionManager(firstDataSource);
    }

    @Bean("secondDataSourceProperties")
    @ConfigurationProperties("second.datasource")
    public DataSourceProperties secondDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public HikariDataSource secondDataSource(@Qualifier("secondDataSourceProperties") DataSourceProperties properties) {
        log.info("second datasource: {}", properties.getUrl());
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(properties.getUrl());
        hikariDataSource.setUsername(properties.getUsername());
        hikariDataSource.setPassword(properties.getPassword());
        runScript(hikariDataSource, Collections.singletonList("classpath*:schema-druid.sql"));
        return hikariDataSource;
    }

    @Bean
    public PlatformTransactionManager secondTxManager(HikariDataSource secondDataSource) {
        return new DataSourceTransactionManager(secondDataSource);
    }

    private void runScript(DataSource dataSource, List<String> locations) {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(Collections.singletonList("classpath*:schema-druid.sql"));
        try {
            factory.afterPropertiesSet();
            populator.addScripts(Objects.requireNonNull(factory.getObject()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DatabasePopulatorUtils.execute(populator, dataSource);
    }
}
