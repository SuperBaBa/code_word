package org.jarvis.consumer.java8.prototype.shape;

/**
 * author:Lovel  date:2020/5/18
 */
public class ShapeTests {
    public static void main(String[] args) {
        org.jarvis.consumer.java8.prototype.shape.ProtoTypeManager protoTypeManager = new org.jarvis.consumer.java8.prototype.shape.ProtoTypeManager();
        Circle circle = (Circle) protoTypeManager.getShape("Circle");
        circle.calculateArea();
        org.jarvis.consumer.java8.prototype.shape.Square square = (org.jarvis.consumer.java8.prototype.shape.Square) protoTypeManager.getShape("Square");
        square.calculateArea();
    }
}
