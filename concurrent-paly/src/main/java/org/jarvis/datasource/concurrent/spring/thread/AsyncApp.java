//package org.jarvis.concurrent.spring.thread;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.EnableAsync;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//
///**
// * 异步方法不能与被调用的异步方法在同一个类中，否则无效
// */
//@Component
//// 开启异步调用
//@EnableAsync
//public class AsyncApp {
//    @Autowired
//    private AsyncTest asyncTest;
//
//    @Autowired
//    private ThreadOther threadOther;
//
//    public void playRun() throws ExecutionException, InterruptedException {
//
//        for (int i = 0; i < 20; i++) {
//            threadOther.poolTest("JOE加油"+i);
//        }
//        // 未使用线程池，相当于当前New了一个Thread对象
//        asyncTest.asyncMethodWithVoidReturnType();
//        //
//        Future<String> result = asyncTest.asyncMethodWithParam("zLong");
//        System.out.println("Test --Result from asynchronous process - " + result);
//
//       //  异步使用线程池的调用方法, 使用对象List做为参数，用来封装返回结果List，便于批量结果处理
//        List<String> result_list = new ArrayList<String>();
//        Future<List<String>> future = null;
//
//        for (int i = 0; i < 20; i++) {
//            future = asyncTest.asyncMethodWithReturnType("zJoe" + i, result_list);
//        }
//        while (true) {  // 这里使用了循环判断，等待获取结果信息
//            if (future.isDone()) { // 判断是否执行完毕
//                System.out.println("Result from asynchronous process - " + future.get());
//                break;
//            }
//            System.out.println("Continue doing something else. ");
//            Thread.sleep(1000);
//        }
//
////        for (String v: result_list) {
////            System.out.println(v);
////        }
////
////        for (int i = 0; i < 10; i++) {
////            try {
////                System.out.println("====AsyncApp===:" + i);
////                TimeUnit.SECONDS.sleep(1);
////            } catch (InterruptedException e) {
////                // TODO Auto-generated catch block
////                e.printStackTrace();
////            }
////        }
//    }
//}
