package com.dps.observer.case_b;

/**
 * ����Ŀ�꣨���۲����
 */
public class SubjectOnlyone extends Subject {

    //֪ͨÿ���۲���
    @Override
    public void notifyObservers() {
        System.out.println("Ŀ�귢���ı䣡");
        System.out.println("֪ͨ���й۲���");
        for (Observer observer : observers){
            observer.response();
        }
    }
}
