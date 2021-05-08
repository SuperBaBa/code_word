package org.jarvis.consumer.java8.factory;

/**
 * author:Lovel  date:2020/6/7
 */
public class FactoryTests {

    public static void main(String[] args) {
        org.jarvis.consumer.java8.factory.AbstractProductFactory productFactory = new org.jarvis.consumer.java8.factory.ProductFirstFactory();
        org.jarvis.consumer.java8.factory.Product product1 = productFactory.newProduct();
        org.jarvis.consumer.java8.factory.Product product2 = productFactory.newProduct();
        product1.show();
    }
}
