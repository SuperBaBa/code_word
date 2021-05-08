package org.jarvis.designpattern.factory;

/**
 * author:Lovel  date:2020/6/7
 */
public class ProductFirstFactory implements AbstractProductFactory {
    @Override
    public Product newProduct() {
        return new ProductFirst();
    }
}
