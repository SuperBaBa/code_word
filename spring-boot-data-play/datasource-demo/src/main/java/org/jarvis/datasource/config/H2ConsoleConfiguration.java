package org.jarvis.datasource.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 当应用是web应用时，并且数据库驱动是{@link org.h2.Driver}才进行H2-Console控制台配置
 *
 * @author tennyson
 * @date 2021/5/8-0:05
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(H2ConsoleProperties.class)
@ConditionalOnClass(value = {WebServlet.class, org.h2.Driver.class})
@AutoConfigureAfter(DataSourceConfiguration.class)
public class H2ConsoleConfiguration {

    private static final Log log = LogFactory.getLog(H2ConsoleConfiguration.class);


    @Bean
    @ConditionalOnBean(value = DruidDataSource.class)
    public ServletRegistrationBean<WebServlet> h2ConsoleForDruid(H2ConsoleProperties properties,
                                                                 @Qualifier("secondDataSource") ObjectProvider<DruidDataSource> dataSource) {
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
            //使用try-with-resource方式进行数据库链接获取，编译时会在finally处加上connection.close()
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
    @ConditionalOnBean(value = HikariDataSource.class)
    public ServletRegistrationBean<WebServlet> h2ConsoleForHikari(H2ConsoleProperties properties,
                                                                  @Qualifier("firstDataSource") ObjectProvider<HikariDataSource> dataSource) {
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
}
