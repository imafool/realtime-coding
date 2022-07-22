package com.dps.observer.case_b;

/**
 * 具体目标（被观察对象）
 */
public class SubjectOnlyone extends Subject {

    //通知每个观察者
    @Override
    public void notifyObservers() {
        System.out.println("目标发生改变！");
        System.out.println("通知所有观察者");
        for (Observer observer : observers){
            observer.response();
        }
    }
}
