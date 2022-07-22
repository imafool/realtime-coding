package com.dps.proxy;

import com.lean.ssm.chapter2.reflect.ReflectServiceImpl;

public class ProxyTest {

	public static void main(String[] args) {
		testJdkProxy();
		// tesCGLIBProxy();
	}

	public static void testJdkProxy() {
		JdkProxyExample jdk = new JdkProxyExample();
		HelloWorld proxy = (HelloWorld) jdk.bind(new HelloWorldImpl());
		proxy.sayHelloWorld();
	}
	
	public static void tesCGLIBProxy() {
	    CglibProxyExample cpe = new CglibProxyExample();
	    ReflectServiceImpl obj = (ReflectServiceImpl)cpe.getProxy(ReflectServiceImpl.class);
	    obj.sayHello("ssm");
	}

}
