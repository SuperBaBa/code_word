package org.jarvis.consumer.nacos_paly;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@NacosPropertySource(dataId = "jarvis",autoRefreshed = true)
public class NacosPalyApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosPalyApplication.class, args);
    }

}
