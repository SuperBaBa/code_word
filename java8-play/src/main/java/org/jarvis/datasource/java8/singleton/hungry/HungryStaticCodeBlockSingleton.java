package org.jarvis.consumer.java8.singleton.hungry;

/**
 * author:Lovel  date:2020/5/19
 * 静态块（静态变量）——>成员变量——>构造方法——>静态方法
 */
public class HungryStaticCodeBlockSingleton {
    /**
     * 准备加载类
     * 静态变量和静态代码块的加载顺序由编写先后决定
     * 静态块，然后执行静态代码块，因为有输出，故打印a
     */
    private HungryStaticCodeBlockSingleton() {
    }

    private static HungryStaticCodeBlockSingleton instance = null;

    static {
        instance = new HungryStaticCodeBlockSingleton();
    }

    public HungryStaticCodeBlockSingleton getInstance() {
        return instance;
    }
}
