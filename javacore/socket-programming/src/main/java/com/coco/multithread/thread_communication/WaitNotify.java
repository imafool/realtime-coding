package com.coco.multithread.thread_communication;

public class WaitNotify {
    public static void main(String[] args) {
        String lock = "lockObj";
        //线程1，等待
        new Thread(() -> {
            synchronized (lock){
                System.out.println("线程1开始等待-->"+System.currentTimeMillis() / 1000);
                try {
                    lock.wait(); //线程等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程1结束等待-->"+System.currentTimeMillis() / 1000);
            }
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //线程2，用来唤醒线程1
        new Thread(() -> {
            synchronized (lock){
                System.out.println("线程2开始唤醒"+System.currentTimeMillis() / 1000);
                lock.notify(); //notify
                System.out.println("线程2结束唤醒"+System.currentTimeMillis() / 1000);
            }
        }).start();
    }
}