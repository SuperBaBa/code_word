package org.jarvis;

import com.alicp.jetcache.anno.config.EnableCreateCacheAnnotation;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import lombok.extern.slf4j.Slf4j;
import org.jarvis.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * <一句话概述功能>
 *
 * @author Marcus
 * @date 2021/4/29-9:51
 * @description this is function description
 */
@SpringBootApplication
@EnableMethodCache(basePackages = "org.jarvis")
@EnableCreateCacheAnnotation
@Slf4j(topic = "jarvis")
@EnableJpaRepositories
public class CacheFrameApp implements ApplicationRunner {
    @Autowired
    UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(CacheFrameApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
