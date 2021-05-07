package org.jarvis.consumer.java8.factory;

/**
 * author:Lovel  date:2020/6/7
 */
public class ProductFirstFactory implements org.jarvis.consumer.java8.factory.AbstractProductFactory {
    @Override
    public org.jarvis.consumer.java8.factory.Product newProduct() {
        return new org.jarvis.consumer.java8.factory.ProductFirst();
    }
}
