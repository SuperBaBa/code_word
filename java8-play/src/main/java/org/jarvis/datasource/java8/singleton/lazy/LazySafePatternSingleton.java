package org.jarvis.consumer.java8.singleton.lazy;

/**
 * author:Lovel  date:2020/5/19
 */
public class LazySafePatternSingleton {
    private LazySafePatternSingleton() {
    }

    private static LazySafePatternSingleton instance = null;

    public static synchronized LazySafePatternSingleton getInstance() {
        if (instance == null) {
            instance = new LazySafePatternSingleton();
        }
        return instance;
    }
}
