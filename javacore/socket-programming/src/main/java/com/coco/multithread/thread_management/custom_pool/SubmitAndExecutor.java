package com.coco.multithread.thread_management.custom_pool;

import java.util.concurrent.*;

public class SubmitAndExecutor {
    public static void main(String[] args) {
        ThreadPoolExecutor customThreadPool = new CustomThreadPool(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, new SynchronousQueue<>());

        for (int i = 0; i < 5; i++) {
            //submit() 方式 仅返回封装结果
            //customThreadPool.submit(new CustomRunnable(10, i));

            //execute() 方式 会把异常详情返回
            customThreadPool.execute(new CustomRunnable(10, i));
        }

    }
}

//自定义线程池
class  CustomThreadPool extends ThreadPoolExecutor {

    //沿用父类
    public CustomThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    //包装任务，封装异常
    public Runnable wrap(Runnable task, Exception exception){
        return () -> {
            try {
                task.run();
            }catch (Exception e ){
                exception.printStackTrace();
                throw e;
            }
        };
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(wrap(task, new Exception("客户跟踪异常")));
    }

    @Override
    public void execute(Runnable command) {
        super.execute(wrap(command, new Exception("客户跟踪异常")));
    }
}

class CustomRunnable implements Runnable{
    private final int x;
    private final int y;

    public CustomRunnable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "计算:" + x + " / " + y + " = " + (x/y));
    }
}