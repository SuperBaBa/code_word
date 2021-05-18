package org.jarvis.sqltask;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;

/**
 * @author marcus
 * @date 2020/11/13-16:52
 */
@SpringBootApplication(exclude = {DruidDataSourceAutoConfigure.class, DataSourceAutoConfiguration.class, TransactionAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class DataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataSourceApplication.class, args);
    }
}
