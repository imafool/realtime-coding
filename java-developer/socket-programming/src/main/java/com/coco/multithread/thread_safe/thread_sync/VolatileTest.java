package com.coco.multithread.thread_safe.thread_sync;

public class VolatileTest {
    public static void main(String[] args) {
        PrintString  printString = new PrintString();
        new Thread(printString::printStringMethod).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printString.setContinuePrint(false);//在main线程中修改打印标志

    }
    static class PrintString{
        private volatile boolean continuePrint = true;

        public void printStringMethod(){
            while (continuePrint){
                System.out.println(Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setContinuePrint(boolean continuePrint) {
            this.continuePrint = continuePrint;
        }
    }
}