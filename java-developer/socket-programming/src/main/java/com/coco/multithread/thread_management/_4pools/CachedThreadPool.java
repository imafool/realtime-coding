package com.coco.multithread.thread_management._4pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPool {

    public static void main(String[] args) {

        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        //提交20个任务
        for (int i = 0; i < 20; i++) {
            cachedThreadPool.execute(() -> {
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