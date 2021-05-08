package org.jarvis.designpattern.singleton.lazy;

/**
 * author:Lovel  date:2020/5/19
 * 懒汉式3 同步代码块（线程安全） 但是不满足单例，依旧会有多个实例
 */
public class LazySyncCodeBlockSigle {
    private static LazySyncCodeBlockSigle instance;

    private LazySyncCodeBlockSigle() {
    }

    public LazySyncCodeBlockSigle getInstance() {
        if (instance == null) {
            synchronized (LazySyncCodeBlockSigle.class) {
                instance = new LazySyncCodeBlockSigle();
            }
        }
        return instance;
    }
}
