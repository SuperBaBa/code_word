package org.jarvis.datasource.concurrent.spring.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Lovel
 * @date 2021/6/24-8:27
 */
public class FutureConcurrent {

    ExecutorService executorService = Executors.newFixedThreadPool(50);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long startTimeStamp = System.currentTimeMillis();
        FutureConcurrent futureConcurrent = new FutureConcurrent();
        ExecutorCompletionService<Integer> service = new ExecutorCompletionService<>(futureConcurrent.executorService);
        CountDownLatch count = new CountDownLatch(50);
        System.out.println(Thread.currentThread().getName() + "#开始启动");
        List<Future<Integer>> arrayFuture = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            Future<Integer> future = service.submit(new Task(Integer.toString(i), count));
            arrayFuture.add(future);
        }

        count.await();
        arrayFuture.forEach(integerFuture -> {
            if (integerFuture.isDone()) {
                try {
                    System.out.println("获得一个结果" + integerFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println(Thread.currentThread().getName() + "#运行结束------耗时" + (System.currentTimeMillis() - startTimeStamp) + "ms");
    }

    static class Task implements Callable<Integer> {
        private final String startInt;
        private CountDownLatch count;

        public Task(String startInt, CountDownLatch count) {
            this.startInt = startInt;
            this.count = count;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println(Thread.currentThread().getName() + "#开始启动#初始化值是:" + startInt);
            int i = Integer.parseInt(startInt);
            for (int j = 0; j < 10; j++) {
                Thread.sleep(1000L);
                i += 1;
            }
            count.countDown();
            System.out.println(Thread.currentThread().getName() + "#运行结束------");
            return i;
        }
    }
}
