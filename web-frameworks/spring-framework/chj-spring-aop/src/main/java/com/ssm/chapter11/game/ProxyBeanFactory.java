package com.ssm.chapter11.game;

/**
 * AOP实现的逻辑，隐藏动态代理实现
 */
public class ProxyBeanFactory {
	public static <T> T getBean(T  obj, Interceptor interceptor) {
        return (T) ProxyBeanUtil.getBean(obj, interceptor);
    }
}
