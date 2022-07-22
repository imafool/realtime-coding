package com.dps.observer.case_b;

/**
 * APP
 */
public class Main {
    public static void main(String[] args) {
        Subject subject = new SubjectOnlyone();
        Observer1 observer1 = new Observer1();
        Observer2 observer2 = new Observer2();
        subject.add(observer1);
        subject.add(observer2);
        subject.notifyObservers();
    }
}
