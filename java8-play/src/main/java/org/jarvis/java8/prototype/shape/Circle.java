package org.jarvis.java8.prototype.shape;

import java.util.Scanner;

/**
 * author:Lovel  date:2020/5/18
 */
public class Circle implements Shape {
    private double radius;

    @Override
    public double calculateArea() {
        System.out.print("这是一个圆，请输入圆的半径：");
        Scanner input = new Scanner(System.in);
        radius = Double.parseDouble(input.next());
        System.out.println("该圆的面积=" + Math.pow(radius,2) * Math.PI + "\n");
        return Math.pow(radius,2) * Math.PI;
    }

    @Override
    public Shape clone() {
        try {
            return (Circle) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
