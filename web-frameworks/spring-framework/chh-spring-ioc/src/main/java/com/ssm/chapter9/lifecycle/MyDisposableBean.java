package com.ssm.chapter9.lifecycle;

import org.springframework.beans.factory.DisposableBean;

public class MyDisposableBean implements DisposableBean {

	@Override
	public void destroy() throws Exception {
		System.out.println("9. disposable..bean....destroy..for IoC!");
	}

}
