package com.dps.observer.case_a;

import java.util.Observable;
import java.util.Observer;

/**
 * A class can implement the Observer interface
 * when it wants to be informed of changes in observable objects.
 * �뱻��֪�ɹ۲�����ǵı仯
 */
public class TaoBaoObserver implements Observer {

    @Override
    public void update(Observable o, Object product) {
        String newProduct = (String) product;
        System.err.println("�����²�Ʒ��"+newProduct+"��ͬ�����Ա��̳�");
    }    
}
