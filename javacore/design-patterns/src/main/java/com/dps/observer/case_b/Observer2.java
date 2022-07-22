package com.dps.observer.case_b;

/**
 * 具体观察者2
 */
public class Observer2 implements Observer{
    @Override
    public void response() {
        System.out.println("观察到目标对象变化，" + this.getClass().getSimpleName() + "做出反应！");
    }
}
