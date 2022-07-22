package com.dps.observer.case_a;

import java.util.Observable;
import java.util.Observer;

public class TaoBaoObserver implements Observer {

    @Override
    public void update(Observable o, Object product) {
        String newProduct = (String) product;
        System.err.println("发送新产品【"+newProduct+"】同步到淘宝商城");
    }    
}
