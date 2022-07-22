package com.coco.lang.dynamic_proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BuzServiceImpl buzService = new BuzServiceImpl();
        LogAdvice logAdvice = new LogAdvice();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(buzService.getClass());
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            logAdvice.before();
            Object invoke = method.invoke(buzService, objects);
            logAdvice.after();
            return invoke;
        });
        BuzServiceImpl buzServiceEnhancer = (BuzServiceImpl) enhancer.create();
        buzServiceEnhancer.getInfo();
    }
}
