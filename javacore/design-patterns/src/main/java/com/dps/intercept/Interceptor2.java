package com.dps.intercept;

import java.lang.reflect.Method;

public class Interceptor2 implements Interceptor {
    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("interceptor..2..pre");
        return true;
    }
    
    public void around(Object proxy, Object target, Method method, Object[] args) {}

    public void after(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("interceptor..2..after");
    }
}
