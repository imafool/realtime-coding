package com.coco.multithread.thread_management._4pools;

import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPool {

    public static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5); //初始化指定核心线程数 5

        System.out.println("即将开始第一次执行：" + date.format(System.currentTimeMillis()));

        scheduledThreadPool.scheduleAtFixedRate(() -> {
            String time = date.format(System.currentTimeMillis());
            System.out.println("延迟一秒，每3s运行一次"+time);
            }, 1, 3, TimeUnit.SECONDS); //参数：1 线程任务 2 第一次延迟执行时间 3 执行周期 4 时间单位
    }
}