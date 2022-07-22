package com.coco.multithread.thread_safe.thread_sync;

public class VolatileNonAtom {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Mythread().start();
        }

    }
    static class Mythread extends Thread{

        public volatile static int count;
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                //编译器提醒：非原子操作
                count ++;
            }
            System.out.println(Thread.currentThread().getName()+"count="+count);
        }
    }
}