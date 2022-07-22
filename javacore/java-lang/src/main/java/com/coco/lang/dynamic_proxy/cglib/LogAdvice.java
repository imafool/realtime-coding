package com.coco.lang.dynamic_proxy.cglib;

public class LogAdvice {
    public void before(){
        System.out.println("~~公共日志逻辑~~before");
    }

    public void after(){
        System.out.println("~~公共日志逻辑~~after");
    }
}
