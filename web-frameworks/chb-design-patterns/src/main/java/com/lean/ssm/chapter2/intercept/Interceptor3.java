package com.lean.ssm.chapter2.intercept;

import java.lang.reflect.Method;

public class Interceptor3 implements Interceptor {
    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("interceptor..3..pre");
        return true;
    }
    
    public void around(Object proxy, Object target, Method method, Object[] args) {}

    public void after(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("interceptor..3..after");
    }
}
