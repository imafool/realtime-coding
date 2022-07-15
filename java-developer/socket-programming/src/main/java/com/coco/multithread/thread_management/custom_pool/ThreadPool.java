package com.coco.multithread.thread_management.custom_pool;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 创建线程池：
 * 1. 使用自定义线程工厂
 * 2. 使用默认拒绝策略（抛出异常）
 */
public class ThreadPool {
    public static void main(String[] args) throws InterruptedException {
        /*
         * 参数列表
         * corePoolSize 核心线程数
         * maximumPoolSize 最大线程数
         * keepAliveTime 非核心线程存活时间，置为0，说明只有核心线程
         * java.util.concurrent.BlockingQueue<Runnable> workQueue 阻塞队列（工作队列）
         * 自定义线程工厂
         */
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS, new SynchronousQueue<>(),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);  //设置为守护线程, 当主线程运行结束,线程池中的线程会自动退出
                    System.out.println("创建了线程: " + t);
                    return t ;
        });

        //提交5个任务到线程池
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                int num = new Random().nextInt(10);
                System.out.println(Thread.currentThread().getName() + "号线程-->" + System.currentTimeMillis() + "开始睡眠:" + num + "秒");
                try {
                    TimeUnit.SECONDS.sleep(num); //模拟线程任务
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(10000);
    }
}