package com.dps.factory.simple;

public class TestSimpleFactory {
    public static void main(String[] args) {
        ProductFactory factory = new ProductFactory();
        factory.createProduct(1);
        factory.createProduct(2);
        factory.createProduct(3);
    }
}
