package org.jarvis.leetcode;

/**
 * @author marcus
 * @date 2020/10/28-9:24
 */
public class Counter {
    private int x = 0;
    ThreadLocal<Integer> th = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 0;
        }
    };

    // 计数方法
    public void count() {
        for (int i = 0; i <= 10; i++) {
            th.set(th.get() + i);
        }
        System.out.println(Thread.currentThread().getName() + "--" + th.get());
    }
    // 计数方法SUM(1:10)=55
   /* public  void count() {
        for(int i=0;i<=10;i++) {
            x = x+i;
        }
        System.out.println(Thread.currentThread().getName()+"--"+x);
    }*/

    public static void main(String[] args) {
        // 定义线程实现接口
        Runnable runnable = new Runnable() {
            Counter counter = new Counter();

            @Override
            public void run() {
                counter.count();
            }
        };
        // 启动10个线程
        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }
}
