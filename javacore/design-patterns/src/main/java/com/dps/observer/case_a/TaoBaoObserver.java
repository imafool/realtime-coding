package com.dps.observer.case_a;

import java.util.Observable;
import java.util.Observer;

/**
 * A class can implement the Observer interface
 * when it wants to be informed of changes in observable objects.
 * 想被告知可观察对象们的变化
 */
public class TaoBaoObserver implements Observer {

    @Override
    public void update(Observable o, Object product) {
        String newProduct = (String) product;
        System.err.println("发送新产品【"+newProduct+"】同步到淘宝商城");
    }    
}
