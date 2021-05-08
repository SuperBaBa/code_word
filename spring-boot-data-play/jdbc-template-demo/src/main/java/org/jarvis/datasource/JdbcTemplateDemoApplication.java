package org.jarvis.datasource;

import lombok.extern.slf4j.Slf4j;
import org.jarvis.datasource.dao.FirstDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

/**
 * 原本打算配置多个数据源，每个数据源针对不同的数据库进行操作。 FirstDao=> FIST SecondDao=> SECOND
 * 由于DataSource暂时不知道如何指定加载schema.sql文件(初始化内嵌数据库)，故无法查到数据//TODO
 * 还是使用springboot自动配置
 * 内嵌数据库初始化 => DataSource自动装配(按照类型byType) => 使用JDBC进行操作
 * author:tennyson date:2020/7/4
 **/
@SpringBootApplication(
        exclude = {DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class,
                DataSourceTransactionManagerAutoConfiguration.class}
)
@Slf4j
public class JdbcTemplateDemoApplication implements CommandLineRunner {
    @Autowired
    private FirstDao firstDao;

    public static void main(String[] args) {
        SpringApplication.run(JdbcTemplateDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        firstDao.insertData();
        firstDao.batchInsert();
        firstDao.listData();
    }
}
