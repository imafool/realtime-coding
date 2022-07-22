package com.dps.proxy;


import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyExample implements MethodInterceptor {
	/**
	 * ����CGLIB�������
	 * 
	 * @param cls
	 *            -- Class��
	 * @return Class���CGLIB�������
	 */
	public Object getProxy(Class cls) {
		// CGLIB enhancer��ǿ�����
		Enhancer enhancer = new Enhancer();
		// ������ǿ����
		enhancer.setSuperclass(cls);
		// ���ô����࣬Ҫ��ǰ����ʵ��MethodInterceptor����
		enhancer.setCallback(this);
		// ���ɲ����ش������
		return enhancer.create();
	}

	/**
	 * �����߼�����
	 * 
	 * @param proxy
	 *            �������
	 * @param method
	 *            ����
	 * @param args
	 *            ��������
	 * @param methodProxy
	 *            ��������
	 * @return �����߼�����
	 * @throws Throwable �쳣
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
		System.err.println("pre");
		// CGLIB���������ʵ���󷽷�
		Object result = methodProxy.invokeSuper(proxy, args);
		System.err.println("after");
		return result;
	}
}
