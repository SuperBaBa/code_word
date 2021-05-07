package org.jarvis.consumer.java8.prototype.shape;

public interface Shape extends Cloneable {
    double calculateArea();

    Shape clone();
}
