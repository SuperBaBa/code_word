package org.jarvis.datasource;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * springboot默认使用的HikariCP因此需要现在springboot-jdbc中需要排除HikariCP依赖
 * 定制Druid数据库连接池Filter三个步骤
 * 1. 引入druid-spring-boot-starter，能够配置druid参数并能调用FilterEvent
 * 2. 集成FilterAdapter实现Filter，在这个抽象类中还定义了很多前后的方法，可以进行自定义修改
 * 3. 在META-INF/druid-filter.properties中增加自定义的Filter配置
 * 在后面使用SpringBoot自动配置数据源时，无论在数据源配置还是链接建立都会调用druid以及自定义的Filter
 * <p>
 * 在本模块使用了Druid的数据库公钥和密钥加解密
 * 使用以下代码获取公私钥{@code java -cp druid-1.0.26.jar com.alibaba.druid.filter.config.ConfigTools 123456}
 * 当然解密也是用这个工具{@link com.alibaba.druid.filter.config.ConfigTools#decrypt(String)}
 * author:tennyson date:2020/7/4
 **/
@SpringBootApplication
@Slf4j
@EnableEncryptableProperties
public class DruidDemoApplication implements CommandLineRunner {
    @Autowired
    private DataSourceProperties dataSourceProperties;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DruidDemoApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info(dataSourceProperties.toString());
        log.info(dataSource.toString());
        List<Map<String, Object>> resultMap = jdbcTemplate.queryForList("SELECT * FROM T_STUDENTS");
        resultMap.forEach(record -> log.info(record.toString()));
    }
}
