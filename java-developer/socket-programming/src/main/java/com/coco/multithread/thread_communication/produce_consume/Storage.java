package com.coco.multithread.thread_communication.produce_consume;

import java.util.LinkedList;

public class Storage {
    // 仓库存储
    private final LinkedList<Object> list = new LinkedList<>();

    //生产者 生产Object 放到仓库
    public void produce() {
        synchronized (list) {
            int maxSize = 10;
            //仓库已满
            while (list.size() + 1 > maxSize) {
                System.out.println("【生产者" + Thread.currentThread().getName() + "】仓库已满");
                try {
                    //等待
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //仓库未满，添加
            list.add(new Object());
            System.out.println("【生产者" + Thread.currentThread().getName() + "】生产一个产品，现库存" + list.size());
            //唤醒消费者
            list.notifyAll();
        }
    }

    //消费者 从仓库 消费库存
    public void consume() {
        synchronized (list) {
            //仓库已空
            while (list.size() == 0) {
                System.out.println("【消费者" + Thread.currentThread().getName() + "】仓库为空");
                try {
                    //等待
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //仓库盈余，消费
            list.remove();
            System.out.println("【消费者" + Thread.currentThread().getName() + "】消费一个产品，现库存" + list.size());
            //唤醒生产者
            list.notifyAll();
        }
    }
}

//生产者线程体
class Producer implements Runnable{
    private Storage storage;

    public Producer(Storage storage){
        this.storage = storage;
    }

    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(1000);
                storage.produce();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

//消费者线程体
class Consumer implements Runnable{
    private Storage storage;

    public Consumer(Storage storage){
        this.storage = storage;
    }

    @Override
    public void run(){
        while(true){
            try{
                Thread.sleep(3000);
                storage.consume();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        Storage storage = new Storage();
        //3个生产者
        Thread p1 = new Thread(new Producer(storage));
        Thread p2 = new Thread(new Producer(storage));
        Thread p3 = new Thread(new Producer(storage));
        //3个消费者
        Thread c1 = new Thread(new Consumer(storage));
        Thread c2 = new Thread(new Consumer(storage));
        Thread c3 = new Thread(new Consumer(storage));

        p1.start();
        p2.start();
        p3.start();
        c1.start();
        c2.start();
        c3.start();
    }}


