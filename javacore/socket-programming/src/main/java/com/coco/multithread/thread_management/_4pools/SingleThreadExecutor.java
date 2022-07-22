package com.coco.multithread.thread_management._4pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutor {
    public static void main(String[] args) {
        //创建线程池
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

        //向线程池中提交18个任务
        for (int i = 0; i < 18; i++) {
            singleThreadExecutor.execute(() -> {
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