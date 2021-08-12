package org.jarvis.consumer.concurrent.spring.dto;

/**
 * author:marcus date:2020/7/28
 **/
public class Course {
    private String name;
    private String classHour;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassHour() {
        return classHour;
    }

    public void setClassHour(String classHour) {
        this.classHour = classHour;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static void main(String[] args) {
        int times = Integer.MAX_VALUE;
        /* 此处计算2^31-1次9999整数对1024的取模，使用的数学计算
         * 多次执行放大时间
         */
        long currentTimeMillis = System.currentTimeMillis();
        int a = 0;
        for (long i = 0; i < times; i++) {
            a = 9999 % 1024;
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        /* 此处计算2^31-1次9999整数对1024的取模，使用的位运算
         * 因为要求 hash&(n-1) 中n必须为2的n次方，这里取1024
         */
        int b = 0;
        for (long i = 0; i < times; i++) {
            b = 9999 & (1024 - 1);
        }

        long currentTimeMillis3 = System.currentTimeMillis();
        System.out.println(a + "," + b);//最后的结果应该是一样的
        System.out.println("数学计算耗时: " + (currentTimeMillis2 - currentTimeMillis));// 1839ms
        System.out.println("位运算耗时: " + (currentTimeMillis3 - currentTimeMillis2));// 852ms
    }
}
