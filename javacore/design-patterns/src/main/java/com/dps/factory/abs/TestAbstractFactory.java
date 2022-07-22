package com.dps.factory.abs;

public class TestAbstractFactory {
    public static void main(String[] args) throws Exception {
        IProductFactory factory1 = new ProductFactory1();
        factory1.createProduct(11);
        factory1.createProduct(12);
        factory1.createProduct(13);
        IProductFactory factory2 = new ProductFactory2();
        factory2.createProduct(21);
        factory2.createProduct(22);
        factory2.createProduct(23);
    }
}
