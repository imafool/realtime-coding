package com.coco.multithread.thread_create;

import java.util.concurrent.*;

/**
 * 线程创建，线程方法，线程状态
 */
public class ThreadShow {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        new MyThread().start();

        new Thread(new RunnableImpl()).start();

        FutureTask<Integer> futureTask = new FutureTask<>(new CallableImpl());
        new Thread(futureTask).start();

        Integer result = futureTask.get();
        System.out.println("Callable线程执行结果：" + result);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> System.out.println("使用CachedThreadPool线程池"));

        MyThread myThread = new MyThread();
        testThreadApi(myThread);
    }

    //线程状态
    enum State {
        NEW, //未开始执行
        RUNNABLE, //正在运行的，也可能正在等待一些OS/进程资源
        BLOCKED, //等待 monitor lock，进入 synchronized block/method
        WAITING, //线程等待等待另一个线程执行特定操作
        TIMED_WAITING, //线程等待指定时间
        TERMINATED; //线程执行结束
    }

    //Thread方法
    private static void testThreadApi(MyThread myThread) {
        myThread.setName("name"); //设置线程名称
        myThread.getName(); //获取线程名称
        myThread.isAlive(); //是否存活
        myThread.getId(); //线程唯一编号
        Thread.yield(); //线程放弃当前CPU资源
        myThread.setPriority(1); //设置优先级，1-10,越大，获得CPU资源概率越大，并不能保证优先级高的先运行
        myThread.interrupt(); //中断线程，只能中断阻塞中的线程，不能中断正在运行中的线程（对运行中的线程，只是给线程打一个停止标记，并非真正停止线程，通过监听中断标志，处理结束）
        myThread.isInterrupted(); //判断上述设置的标志
        myThread.setDaemon(true); //线程启动前，设置守护线程
    }
}

//线程创建
class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println("继承Thread");
    }
}

//线程创建
class RunnableImpl implements Runnable{
    @Override
    public void run() {
        System.out.println("实现Runnable接口");
    }
}

//线程创建
class CallableImpl implements Callable<Integer>{
    @Override
    public Integer call() {
        System.out.println("实现Callable接口");
        return 1;
    }
}
