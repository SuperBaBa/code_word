package org.jarvis.consumer.java8.singleton.lazy;

/**
 * author:Lovel  date:2020/5/19
 */
public class LazyNormalPatternSingleton {
    private LazyNormalPatternSingleton() {
    }

    private static LazyNormalPatternSingleton instance = null;

    public static LazyNormalPatternSingleton getInstance() {
        /*存在判断所以线程不一定安全*/
        if (instance == null) {
            instance = new LazyNormalPatternSingleton();
        }
        return instance;
    }
}
