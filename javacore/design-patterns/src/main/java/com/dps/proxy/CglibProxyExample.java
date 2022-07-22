package com.dps.proxy;


import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyExample implements MethodInterceptor {
	/**
	 * 生成CGLIB代理对象
	 * 
	 * @param cls
	 *            -- Class类
	 * @return Class类的CGLIB代理对象
	 */
	public Object getProxy(Class cls) {
		// CGLIB enhancer增强类对象
		Enhancer enhancer = new Enhancer();
		// 设置增强类型
		enhancer.setSuperclass(cls);
		// 设置代理类，要求当前对象实现MethodInterceptor方法
		enhancer.setCallback(this);
		// 生成并返回代理对象
		return enhancer.create();
	}

	/**
	 * 代理逻辑方法
	 * 
	 * @param proxy
	 *            代理对象
	 * @param method
	 *            方法
	 * @param args
	 *            方法参数
	 * @param methodProxy
	 *            方法代理
	 * @return 代理逻辑返回
	 * @throws Throwable 异常
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.err.println("pre");
		// CGLIB反射调用真实对象方法
		Object result = methodProxy.invokeSuper(proxy, args);
		System.err.println("after");
		return result;
	}
}
