package org.jarvis.consumer.seckill.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

/**
 * @author tennyson
 * @date 2020/10/5-15:43
 */
@Configuration
public class PersistenceConfiguration {
    //    @Bean
    public DataSource initHikariCPDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("");
        hikariConfig.setUsername("");
        hikariConfig.setPassword("");
        return new HikariDataSource(hikariConfig);
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
        System.out.println(context.getBean("dataSource"));
    }

}
