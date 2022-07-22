package com.dps.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyExample implements InvocationHandler {

	private Object target = null;

	/**
	 * 建立代理对象和真实对象的代理关系，并返回代理对象
	 * 
	 * @param target 真实对象
	 * @return 代理对象
	 */
	public Object bind(Object target) {
		this.target = target;
		return Proxy.newProxyInstance(
				target.getClass().getClassLoader(), //真实对象本身的ClassLoader
				target.getClass().getInterfaces(), //把生成的代理对象下挂到的接口
				this); //代理逻辑，必须实现InvocationHandler接口的invoke方法
	}

	/**
	 * 代理方法逻辑
	 * 
	 * @param proxy
	 *            --代理对象 ： bind生成的对象
	 * @param method
	 *            --当前调度方法 ： sayHello()
	 * @param args
	 *            --当前方法参数 : sayHello()方法的参数
	 * @return 代理结果返回
	 * @throws Throwable
	 *             异常
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("step into...");
		System.out.println("pre");
		Object obj = method.invoke(target, args);// 相当于调用sayHelloWorld方法（通过反射实现调用）
		System.out.println("after");
		return obj;
	}
}