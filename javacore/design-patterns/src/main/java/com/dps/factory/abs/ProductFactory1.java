package com.dps.factory.abs;

import com.lean.ssm.chapter2.factory.products.Product1;
import com.lean.ssm.chapter2.factory.products.Product2;

/**
 * ���幤��1������Ʒ1����Ʒ2
 */
@SuppressWarnings("all")
public class ProductFactory1 implements IProductFactory{
    @Override
    public void createProduct(int code) throws Exception {
        String s = String.valueOf(code);
        if (s.length() != 2){
            System.out.println("��Ʒ��Ŵ���: "+s);
        }
        char index0 = s.charAt(0);
        char index1 = s.charAt(1);
        if (index0 == '1'){
            if (index1 == '1'){
                new Product1().toString();
            }else if(index1 == '2'){
                new Product2().toString();
            }else{
                System.out.println("�ù����޷������ò�Ʒ: "+index1);
            }
        }
    }
}
