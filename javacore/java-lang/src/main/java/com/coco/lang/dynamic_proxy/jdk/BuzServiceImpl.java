package com.coco.lang.dynamic_proxy.jdk;

public class BuzServiceImpl implements IBuzService {
    @Override
    public void getInfo() throws InterruptedException {
        System.out.println("开始执行业务逻辑..." + this.getClass().getTypeName());
        Thread.sleep(3000);
    }
}
