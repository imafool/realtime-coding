package com.dps.observer.case_b;

/**
 * ����۲���2
 */
public class Observer2 implements Observer{
    @Override
    public void response() {
        System.out.println("�۲쵽Ŀ�����仯��" + this.getClass().getSimpleName() + "������Ӧ��");
    }
}
