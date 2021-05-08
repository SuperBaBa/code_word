package org.jarvis.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.config.SortedResourcesFactoryBean;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author marcus
 * @date 2021/5/8
 **/
@Configuration
public class DataSourceConfiguration {
    private final static Log log = LogFactory.getLog(DataSourceConfiguration.class);

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(DruidDataSource.class)
    static class DruidDataSourceProvider {
        @Bean
        @Resource
        public DruidDataSource firstDataSource(@Qualifier("firstDataSourceProperties") DataSourceProperties properties) {
            DruidDataSource druidDataSource = new DruidDataSource();
            properties.setSqlScriptEncoding(StandardCharsets.UTF_8);
            druidDataSource.setUrl(properties.getUrl());
            druidDataSource.setUsername(properties.getUsername());
            druidDataSource.setPassword(properties.getPassword());
            druidDataSource.setTestWhileIdle(false);
            runScript(druidDataSource, Collections.singletonList("classpath*:schema-druid.sql"));
            return druidDataSource;
        }

        void runScript(DataSource dataSource, List<String> locations) {
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

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(HikariDataSource.class)
    static class HikariDataSourceProvider {
        @Bean
        @Resource
        public HikariDataSource firstDataSource(DataSourceProperties properties) {
            HikariDataSource hikariDataSource = new HikariDataSource();
            hikariDataSource.setJdbcUrl(properties.getUrl());
            hikariDataSource.setUsername(properties.getUsername());
            hikariDataSource.setPassword(properties.getPassword());
            runScript(hikariDataSource, Collections.singletonList("classpath*:schema-druid.sql"));
            return hikariDataSource;
        }

        void runScript(DataSource dataSource, List<String> locations) {
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

}
