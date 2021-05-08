package org.jarvis.java8.thread;

/**
 * author:Lovel  date:2020/5/31
 */
public class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }
}
