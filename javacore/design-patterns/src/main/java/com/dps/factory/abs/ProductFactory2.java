package com.dps.factory.abs;

import com.lean.ssm.chapter2.factory.products.Product3;

/**
 * ���幤��2������Ʒ3
 */
@SuppressWarnings("all")
public class ProductFactory2 implements IProductFactory{
    @Override
    public void createProduct(int code) throws Exception {
        String s = String.valueOf(code);
        if (s.length() != 2){
            System.out.println("��Ʒ��Ŵ���: "+s);
        }
        char index0 = s.charAt(0);
        if (index0 == '2'){
            new Product3().toString();
        }
    }
}
