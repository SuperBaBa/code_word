package org.jarvis.java8.prototype.shape;

import java.util.Scanner;

/**
 * author:Lovel  date:2020/5/18
 */
public class Square implements Shape{
    private double width;
    @Override
    public double calculateArea() {
        System.out.print("这是一个正方形，请输入它的边长：");
        Scanner input=new Scanner(System.in);
        width=Double.parseDouble(input.next());
        System.out.println("该正方形的面积="+Math.pow(width,2)+"\n");
        return Math.pow(width,2);
    }

    @Override
    public Shape clone()  {
        try {
            return (Square) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
