package org.jarvis.consumer.concurrent.spring.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Async 类上添加，该类下的所有方法都会支持异步，(测试不行，不可以在类上添加，否则无法启动配置线程池，没有提交到线程池) 暂时未试，不配置自定义线程池的情况
 * 需在调用类上加上 @EnableAsync 或在 启动类 （main方法类）加入
 */
@Component
public class AsyncClass {
    @Async
    public void test1() {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("test1结束时间是" + sdf.format(new Date())+Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Async
    public void test2() {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("test2结束时间是" + sdf.format(new Date())+Thread.currentThread().getName());
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Async
    public void test3() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("test3结束时间是" + sdf.format(new Date())+Thread.currentThread().getName());
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
