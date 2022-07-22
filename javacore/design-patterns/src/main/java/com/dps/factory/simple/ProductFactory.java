package com.dps.factory.simple;

import com.dps.factory.products.Product1;
import com.dps.factory.products.Product2;
import com.dps.factory.products.Product3;

@SuppressWarnings("all")
public class ProductFactory {
    public void createProduct(int code) {
        switch (code){
            case 1:
                new Product1().toString();
                break;
            case 2:
                new Product2().toString();
                break;
            case 3:
                new Product3().toString();
                break;
        }
    }
}
