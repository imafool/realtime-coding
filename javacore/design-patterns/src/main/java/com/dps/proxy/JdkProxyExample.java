package com.dps.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyExample implements InvocationHandler {

	private Object target = null;

	/**
	 * ��������������ʵ����Ĵ����ϵ�������ش������
	 * 
	 * @param target ��ʵ����
	 * @return �������
	 */
	public Object bind(Object target) {
		this.target = target;
		return Proxy.newProxyInstance(
				target.getClass().getClassLoader(), //��ʵ�������ClassLoader
				target.getClass().getInterfaces(), //�����ɵĴ�������¹ҵ��Ľӿ�
				this); //�����߼�������ʵ��InvocationHandler�ӿڵ�invoke����
	}

	/**
	 * �������߼�
	 * 
	 * @param proxy
	 *            --������� �� bind���ɵĶ���
	 * @param method
	 *            --��ǰ���ȷ��� �� sayHello()
	 * @param args
	 *            --��ǰ�������� : sayHello()�����Ĳ���
	 * @return ����������
	 * @throws Throwable
	 *             �쳣
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("step into...");
		System.out.println("pre");
		Object obj = method.invoke(target, args);// �൱�ڵ���sayHelloWorld������ͨ������ʵ�ֵ��ã�
		System.out.println("after");
		return obj;
	}
}