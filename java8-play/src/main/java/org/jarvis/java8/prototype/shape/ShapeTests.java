package org.jarvis.java8.prototype.shape;

/**
 * author:Lovel  date:2020/5/18
 */
public class ShapeTests {
    public static void main(String[] args) {
        ProtoTypeManager protoTypeManager = new ProtoTypeManager();
        Circle circle = (Circle) protoTypeManager.getShape("Circle");
        circle.calculateArea();
        Square square = (Square) protoTypeManager.getShape("Square");
        square.calculateArea();
    }
}
