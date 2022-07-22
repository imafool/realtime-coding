package com.dps.intercept;

import com.dps.proxy.HelloWorld;
import com.dps.proxy.HelloWorldImpl;

public class TestInterceptor {
	public static void main(String[] args) {
		// testInterceptor();
		testChain();
	}
	
	
	public static void testInterceptor() {
		HelloWorld proxy = (HelloWorld) InterceptorJdkProxy.bind(
				new HelloWorldImpl(), "com.dps.intercept.MyInterceptor");
		proxy.sayHelloWorld();
	}

	//测试责任链模式
	public static void testChain() {
		HelloWorld proxy1 = (HelloWorld) InterceptorJdkProxy.bind(
                new HelloWorldImpl(), "com.dps.intercept.Interceptor1");

        HelloWorld proxy2 = (HelloWorld) InterceptorJdkProxy.bind(
                proxy1, "com.dps.intercept.Interceptor2");

        HelloWorld proxy3 = (HelloWorld) InterceptorJdkProxy.bind(
                proxy2, "com.dps.intercept.Interceptor3");
        proxy3.sayHelloWorld();
	}
}
