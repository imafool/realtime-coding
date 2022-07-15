package com.lean.ssm.chapter2.intercept;

import java.lang.reflect.Method;

public class Interceptor1 implements Interceptor {
	public boolean before(Object proxy, Object target, Method method, Object[] args) {
		System.out.println("interceptor..1..pre");
		return true;
	}

	public void around(Object proxy, Object target, Method method, Object[] args) {
	}

	public void after(Object proxy, Object target, Method method, Object[] args) {
		System.out.println("interceptor..1..after");
	}
}
