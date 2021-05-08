package org.jarvis.consumer.concurrent.spring.thread;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;

/**
 * @author zoulog
 * @deprecated 展示异步注解的功能用法
 * 如下方式会使@Async失效
 * 异步方法使用static修饰
 * 异步类没有使用@Component注解（或其他注解）导致spring无法扫描到异步类
 * 异步方法不能与被调用的异步方法在同一个类中
 * 类中需要使用@Autowired或@Resource等注解自动注入，不能自己手动new对象
 * 如果使用SpringBoot框架必须在启动类中增加@EnableAsync注解
 */
@Component
public class AsyncTest  {

    // @PostConstruct // 加上该注解项目启动时就执行一次该方法
    @Async // 可以使用自定义的线程池
    public void asyncMethodWithVoidReturnType() { //    不可接收参数
        System.out.println("Execute method asynchronously. " + Thread.currentThread().getName()+"succeed ZOULONG");
    }

    @Async("taskExecutor1")
    public Future<String> asyncMethodWithParam(String name) {
        System.out.println("Execute method asynchronously. " + Thread.currentThread().getName()+name);
        return  new AsyncResult<String>(name+"--NX+zLong");
    }

    // 让当前异部的线程交给线程池处理，可接收参数
    @Async("taskExecutor2")
//    public Future<String> asyncMethodWithReturnType(String name) {
    public Future<List<String>> asyncMethodWithReturnType(String name, List<String> rustList) {
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName()+name);

        try{
            Thread.sleep(200);
            rustList.add( Thread.currentThread().getName()+name);
           return new AsyncResult<List<String>> (rustList);
//     return new AsyncResult<String>("hello world !!!!:  params:"+name);
        }catch(InterruptedException e) {
            //
        }
        return null;
    }
}
