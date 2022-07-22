package com.dps.observer.case_b;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象目标对象
 */
public abstract class Subject {
    //存储注册的观察者
    protected List<Observer> observers = new ArrayList<>();

    //操作观察者方法，add/remove
    public void add(Observer observer) {
        observers.add(observer);
    }

    public void remove(Observer observer) {
        observers.remove(observer);
    }

    //通知观察者方法
    public abstract void notifyObservers();
}
