package org.jarvis.consumer.aspectj.config;

import org.jarvis.consumer.model.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author marcus
 * @date 2020/9/6-16:22
 */
@EnableAspectJAutoProxy
@Configuration
public class AspectJConfiguration {
    @Bean
    public TestBean testBeanX() {
        return new TestBean("testBeanXX");
    }

    @Bean
    public TestBean testBeanY() {
        return new TestBean("testBeanYYY");
    }
}
