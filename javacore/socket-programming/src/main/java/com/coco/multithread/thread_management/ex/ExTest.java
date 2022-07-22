package com.coco.multithread.thread_management.ex;

public class ExTest {

    public static void main(String[] args) {

        //当前线程 UncaughtExceptionHandler 全局回调
//        Thread.setDefaultUncaughtExceptionHandler((t, e) -> { //设置线程全局的回调接口
//            System.out.println(t.getName() + "线程产生了异常: " + e.getMessage());
//        });

        System.out.println(Thread.currentThread().getThreadGroup());

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程开始运行");
            //TODO 没有设置回调接口，使用当前线程组的回调接口，当前线程组也没有定义回调，把线程栈打印出来
            System.out.println(Thread.currentThread().getThreadGroup());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();//捕获处理
            }
            System.out.println(12 / 0);
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getThreadGroup());
            String txt = null;
            System.out.println(txt.length());  //会产生空指针异常
        }).start();
    }
}