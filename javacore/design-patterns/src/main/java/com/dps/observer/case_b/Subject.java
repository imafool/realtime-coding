package com.dps.observer.case_b;

import java.util.ArrayList;
import java.util.List;

/**
 * ����Ŀ�����
 */
public abstract class Subject {
    //�洢ע��Ĺ۲���
    protected List<Observer> observers = new ArrayList<>();

    //�����۲��߷�����add/remove
    public void add(Observer observer) {
        observers.add(observer);
    }

    public void remove(Observer observer) {
        observers.remove(observer);
    }

    //֪ͨ�۲��߷���
    public abstract void notifyObservers();
}
