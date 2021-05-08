package org.jarvis.consumer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

/**
 * author:marcus date:2020/8/6
 **/
@Configuration
public class ReactiveRedisTemplateConfiguration {
    //    @Bean(value = "reactiveRedisTemplate")
    public ReactiveStringRedisTemplate reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveStringRedisTemplate(factory);
    }
}
