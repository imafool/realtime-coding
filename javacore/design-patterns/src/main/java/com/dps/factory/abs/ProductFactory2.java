package com.dps.factory.abs;

import com.lean.ssm.chapter2.factory.products.Product3;

/**
 * 具体工厂2生产产品3
 */
@SuppressWarnings("all")
public class ProductFactory2 implements IProductFactory{
    @Override
    public void createProduct(int code) throws Exception {
        String s = String.valueOf(code);
        if (s.length() != 2){
            System.out.println("产品编号错误: "+s);
        }
        char index0 = s.charAt(0);
        if (index0 == '2'){
            new Product3().toString();
        }
    }
}
