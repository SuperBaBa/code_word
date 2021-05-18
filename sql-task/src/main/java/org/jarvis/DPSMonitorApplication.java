package org.jarvis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author marcus
 * @date 2020/11/1-21:18
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, JdbcTemplateAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class})
@EnableScheduling
public class DPSMonitorApplication {
    private final static Logger log = LoggerFactory.getLogger(DPSMonitorApplication.class);

    public static void main(String[] args) {
        log.info("spring上下文开始初始化");
        SpringApplication.run(DPSMonitorApplication.class, args);
    }
}
