package org.jarvis.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
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
@EnableConfigurationProperties(H2ConsoleProperties.class)
public class DataSourceConfiguration {
    @Bean("firstDataSourceProperties")
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
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        try {
            SortedResourcesFactoryBean factory = new SortedResourcesFactoryBean(Collections.singletonList("classpath*:schema-druid.sql"));
            factory.afterPropertiesSet();
            populator.addScripts(Objects.requireNonNull(factory.getObject()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DatabasePopulatorUtils.execute(populator, druidDataSource);
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
        return hikariDataSource;
    }

    @Bean
    public PlatformTransactionManager secondTxManager(HikariDataSource secondDataSource) {
        return new DataSourceTransactionManager(secondDataSource);
    }

    @Bean
    public ServletRegistrationBean<WebServlet> h2Console(H2ConsoleProperties properties,
                                                         @Qualifier("secondDataSource") ObjectProvider<DataSource> dataSource) {
        String path = properties.getPath();
        String urlMapping = path + (path.endsWith("/") ? "*" : "/*");
        ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<>(new WebServlet(), urlMapping);
        H2ConsoleProperties.Settings settings = properties.getSettings();
        if (settings.isTrace()) {
            registration.addInitParameter("trace", "");
        }
        if (settings.isWebAllowOthers()) {
            registration.addInitParameter("webAllowOthers", "");
        }
        dataSource.ifAvailable((available) -> {
            try (Connection connection = available.getConnection()) {
                log.info("H2 console available at '" + path + "'. Database available at '"
                        + connection.getMetaData().getURL() + "'");
            } catch (SQLException ex) {
                // Continue
            }
        });
        return registration;
    }

    @Bean
    public ServletRegistrationBean<WebServlet> h3Console(H2ConsoleProperties properties,
                                                         @Qualifier("firstDataSource") ObjectProvider<DataSource> dataSource) {
        String path = properties.getPath();
        String urlMapping = path + (path.endsWith("/") ? "*" : "/*");
        ServletRegistrationBean<WebServlet> registration = new ServletRegistrationBean<>(new WebServlet(), urlMapping);
        H2ConsoleProperties.Settings settings = properties.getSettings();
        if (settings.isTrace()) {
            registration.addInitParameter("trace", "");
        }
        if (settings.isWebAllowOthers()) {
            registration.addInitParameter("webAllowOthers", "");
        }
        dataSource.ifAvailable((available) -> {
            try (Connection connection = available.getConnection()) {
                log.info("H2 console available at '" + path + "'. Database available at '"
                        + connection.getMetaData().getURL() + "'");
            } catch (SQLException ex) {
                // Continue
            }
        });
        return registration;
    }

    /**
     * 配置druid管理页面的访问控制
     * 访问网址: http://127.0.0.1:8080/druid
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean<Servlet> druidServlet() {
        log.info("init Druid Servlet Configuration");
        ServletRegistrationBean<Servlet> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new StatViewServlet());  //配置一个拦截器
        servletRegistrationBean.addUrlMappings("/druid/*");    //指定拦截器只拦截druid管理页面的请求
        HashMap<String, String> initParam = new HashMap<String, String>();
        initParam.put("loginUsername", "admin");    //登录druid管理页面的用户名
        initParam.put("loginPassword", "admin");    //登录druid管理页面的密码
        initParam.put("resetEnable", "true");       //是否允许重置druid的统计信息
        initParam.put("allow", "");         //ip白名单，如果没有设置或为空，则表示允许所有访问
        servletRegistrationBean.setInitParameters(initParam);
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

}
