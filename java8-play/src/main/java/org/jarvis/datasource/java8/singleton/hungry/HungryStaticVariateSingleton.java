package org.jarvis.consumer.java8.singleton.hungry;

/**
 * author:Lovel  date:2020/5/19
 * 静态变量式避免了线程同步，线程安全，但在类加载时完成实例化，占用了内存资源，未达到懒加载效果
 */
public class HungryStaticVariateSingleton {
    /*饿汉式，静态变量式*/
    private static final HungryStaticVariateSingleton instance = new HungryStaticVariateSingleton();

    private HungryStaticVariateSingleton() {
    }

    /**
     * 总结：这种方式基于ClassLoader类加载机制避免了多线程的同步问题，只不过instance属性在类加载就实例化，
     * 在单例模式中大多数都是调用getInstance方法，由于getInstance方法是static静态的，调用它肯定会触发类加载！
     * 但是触发类加载的原因有很多，我们不能保证这个类会通过其他的方式触发类加载（比如调用了其他的static方法）
     * 这个时候初始化instance就没有达到lazy loading 懒加载的效果，可能造成内存的浪费！
     * @return
     */
    private HungryStaticVariateSingleton getInstance() {
        return instance;
    }
}
