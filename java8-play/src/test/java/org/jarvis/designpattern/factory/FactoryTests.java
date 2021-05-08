package org.jarvis.designpattern.factory;

/**
 * author:Lovel  date:2020/6/7
 */
public class FactoryTests {

    public static void main(String[] args) {
        AbstractProductFactory productFactory = new ProductFirstFactory();
        Product product1 = productFactory.newProduct();
        Product product2 = productFactory.newProduct();
        product1.show();
    }
}
