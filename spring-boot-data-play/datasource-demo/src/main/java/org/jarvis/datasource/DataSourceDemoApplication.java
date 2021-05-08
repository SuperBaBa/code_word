package org.jarvis.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

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
public class DataSourceDemoApplication implements CommandLineRunner {
    //    @Autowired
//    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DataSourceDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showConnection();
        showData();

    }

    /**
     * 获取自动装配的数据源
     *
     * @throws SQLException
     */
    private void showConnection() throws SQLException {
//        log.info(dataSource.toString());
//        Connection connection = dataSource.getConnection();
//        // 自动配置Hikari的数据库连接池
//        log.info("数据源资源信息=>{}", connection.toString());
//        connection.close();
    }

    /**
     * due to {@link DatabasePopulatorUtils#execute(DatabasePopulator, DataSource)} will
     * execute {@link ResourceDatabasePopulator#populate(Connection)}, so schema-druid.sql/schema-all.sql/data.sql is called
     * by {@link ScriptUtils#executeSqlScript(Connection, Resource)}. The in-memory database H2 will have data
     */
    private void showData() {
        /*jdbcTemplate.queryForList("SELECT * FROM `T_STUDENTS`").forEach(row -> {
            log.info(row.toString());
        });*/
    }
}
