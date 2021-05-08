package org.jarvis.designpattern.singleton.example;

/**
 * author:Lovel  date:2020/5/17
 */
public class SingletonLazy {
    public static void main(String[] args) {
        President zt1 = President.getInstance();
        zt1.getName();    //输出总统的名字
        President zt2 = President.getInstance();
        zt2.getName();    //输出总统的名字
        if (zt1 == zt2) {
            System.out.println("他们是同一人！");
        } else {
            System.out.println("他们不是同一人！");
        }
    }
}


class President {
    private static volatile President instance = null;
    private String name;

    private President() {
        System.out.println("create a president that id named stark");
        this.name = "Stark";
    }

    public static synchronized President getInstance() {
        if (instance == null) {
            instance = new President();
        } else {
            System.out.println("exist one president");
        }
        return instance;
    }

    public String getName() {
        System.out.println(String.format("my name is %s", name));
        return name;
    }
}
