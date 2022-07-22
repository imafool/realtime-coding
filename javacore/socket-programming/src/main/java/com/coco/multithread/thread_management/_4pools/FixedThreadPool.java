package com.coco.multithread.thread_management._4pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPool {

    public static void main(String[] args) {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

        //向线程池提交30个任务
        for (int i = 0; i < 30; i++) {
            fixedThreadPool.execute(() -> {
                System.out.println(Thread.currentThread().getId()+"编号的任务正在执行"+System.currentTimeMillis());
                try {
                    Thread.sleep(3000); //模拟任务
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
