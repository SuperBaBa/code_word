package org.jarvis.java8.prototype.shape;

public interface Shape extends Cloneable {
    double calculateArea();

    Shape clone();
}
