package com.dps.factory.abs;

import com.lean.ssm.chapter2.factory.products.Product1;
import com.lean.ssm.chapter2.factory.products.Product2;

/**
 * 具体工厂1生产产品1，产品2
 */
@SuppressWarnings("all")
public class ProductFactory1 implements IProductFactory{
    @Override
    public void createProduct(int code) throws Exception {
        String s = String.valueOf(code);
        if (s.length() != 2){
            System.out.println("产品编号错误: "+s);
        }
        char index0 = s.charAt(0);
        char index1 = s.charAt(1);
        if (index0 == '1'){
            if (index1 == '1'){
                new Product1().toString();
            }else if(index1 == '2'){
                new Product2().toString();
            }else{
                System.out.println("该工厂无法生产该产品: "+index1);
            }
        }
    }
}
