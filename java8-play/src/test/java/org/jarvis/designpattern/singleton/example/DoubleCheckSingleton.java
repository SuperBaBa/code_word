package org.jarvis.designpattern.singleton.example;

/**
 * author:Lovel  date:2020/5/19
 * 双重检查应用实例方式：线程安全、延迟加载、效率较高
 */
public class DoubleCheckSingleton {
    private static volatile DoubleCheckSingleton instance;

    public static DoubleCheckSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
