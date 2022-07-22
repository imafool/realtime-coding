package com.coco.multithread.thread_communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 【交替打印】线程之间通信 - Condition
 */
public class ConditionTest {
    public static void main(String[] args) {
        MyService myService = new MyService();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                myService.printOne();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                myService.printTwo();
            }
        }).start();
    }
    static class MyService{
        private final Lock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();
        private boolean flag = true;
        public void printOne(){
            try {
                lock.lock();
                while(!flag){
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName()+"----------1----------");
                flag=false;
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void printTwo(){
            try {
                lock.lock();
                while(flag){
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName()+"----------2----------");
                flag=true;
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
