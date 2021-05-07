package org.jarvis.consumer.concurrent.spring.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * author:marcus date:2020/7/28
 **/
@Service
public class TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    @Qualifier("springExecutor")
    private Executor executor;

    @Async("springExecutor")
    public void executeAsync(String input) {
        logger.info("开始异步线程要做的事情");
        logger.info("使用线程=>{}正在插入耗时数据[{}]", Thread.currentThread().getName(), input);
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (Exception e) {
        }
        logger.info("完成异步线程要做的事情");
    }

    public void asyncPrintString() {

        executor.execute(() -> {
            logger.info("当前是异步运行，线程是{}", Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(5L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//                System.out.println("start exception");
            if (Integer.parseInt(Thread.currentThread().getName().split("-")[1]) % 2 == 0) {
                logger.info("will throw exception {}", Thread.currentThread().getName());
                int arithmetic = 3 / 0;
            }
        });

//                System.out.println("end exception");
//
    }

    @Async
    public Future<String> doAsync(int i) {
        logger.info(">>>>>>>>>>>>>>线程名>>>>>>>>>>>>>>" + Thread.currentThread().getName());
        try {
            // 这个方法需要调用500毫秒
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 消息汇总
        return new AsyncResult<>(String.format("这个是第{%s}个异步调用的证书", i));
    }

    @Async
//    public Future<String> asyncMethodWithReturnType(String name) {
    public Future<List<String>> asyncMethodWithReturnType(String name, List<String> rustList) {
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName() + name);
        try {
            Thread.sleep((long) (Math.random() * 10000));
            rustList.add(Thread.currentThread().getName() + name);
            return new AsyncResult<List<String>>(rustList);
//     return new AsyncResult<String>("hello world !!!!:  params:"+name);
        } catch (InterruptedException e) {
            //
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
