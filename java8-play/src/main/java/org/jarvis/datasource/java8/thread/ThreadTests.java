package org.jarvis.consumer.java8.thread;

/**
 * author:Lovel  date:2020/5/31
 */
public class ThreadTests {
    public static void main(String[] args) {
        org.jarvis.consumer.java8.thread.MyThread myThread = new org.jarvis.consumer.java8.thread.MyThread("这是我的线程");
        myThread.run();
        myThread.run();
        myThread.run();
        myThread.start();
    }
}
