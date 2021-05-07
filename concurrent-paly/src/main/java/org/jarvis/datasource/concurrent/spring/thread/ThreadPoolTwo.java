package org.jarvis.consumer.concurrent.spring.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class ThreadPoolTwo {
//可以自已定义Bean对象注入，只要类型是Executor 即可，ThreadPoolTask最终实现的也是这个
    @Bean("taskPool") // bean的名称，默认为首字母小写的方法名
    public <T> Executor taskPool() {
//    public <T> ExecutorService taskPool() {
        Executor threadPool =  Executors.newFixedThreadPool(30);
        return threadPool;
    }
}
