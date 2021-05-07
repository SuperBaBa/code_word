package org.jarvis.consumer.java8.singleton.example;

/**
 * 1、这种方式采用了类加载机制来保证初始化实例时只有一个线程
 * 2、巧妙的将实例化Singleton操作放进getInstance方法中，getInstance方法返回静态内部类中实例化好的Singleton
 * 3、类的静态属性只会在第一次加载类的时候初始化，也就是只会初始化一次，在这里，JVM帮我们保证了线程的安全，类在初始化时，别的线程无法进入。
 * 原文链接：https://blog.csdn.net/qq_44543508/article/details/103249751
 * author:Lovel  date:2020/5/19
 */
public class StaticInernalClassSingleton {
    private StaticInernalClassSingleton() {
    }

    /*写一个static静态内部类，给该类添加一个static静态instance属性
    内部类不会在其外部类被加载的同时被加载的事实，我们可以引申出单例模式的一种实现方式： 静态内部类*/
    private static class SingletonInstance {
        private static final StaticInernalClassSingleton INTAL_CLASS_SINGLETON = new StaticInernalClassSingleton();
    }
    /**
     * 调用构造方法时，外部类Outer被加载，但这时其静态内部类StaticInner却未被加载。
     * 直到调用该内部类的静态方法才被加载。
     */
    public static synchronized StaticInernalClassSingleton getInstence() {
        return SingletonInstance.INTAL_CLASS_SINGLETON;
    }
}
