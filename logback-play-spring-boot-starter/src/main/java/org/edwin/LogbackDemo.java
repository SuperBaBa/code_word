package org.edwin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static net.logstash.logback.argument.StructuredArguments.v;

/**
 * author:marcus date:2020/7/9
 **/
//@ImportResource(locations = "classpath:*.xml")
@SpringBootApplication
public class LogbackDemo implements CommandLineRunner {
    private Logger log = LoggerFactory.getLogger(LogbackDemo.class);

    public static void main(String[] args) {
        //此处返回的是配置应用上下文，可以获取到IOC容器的所有Bean
        ConfigurableApplicationContext context = SpringApplication.run(LogbackDemo.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("this is a paragraph. I say {} you say {}", "切克", "闹");
        log.info("this is a paragraph. I say {} you say {}", v("key-first","切克"), v("key-second","闹"));
    }
}
