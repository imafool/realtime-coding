package com.coco.lang.dynamic_proxy.jdk;

import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BuzServiceImpl buzService = new BuzServiceImpl();
        LogAdvice logAdvice = new LogAdvice();
        //代理逻辑（对目标对象，织入横切逻辑），返回代理对象
        IBuzService proxyInstance = (IBuzService) Proxy.newProxyInstance(buzService.getClass().getClassLoader(),
                buzService.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                    logAdvice.before();
                    method.invoke(buzService, args1);
                    logAdvice.after();
                    return null;
                });
        //代理执行
        proxyInstance.getInfo();
    }
}
