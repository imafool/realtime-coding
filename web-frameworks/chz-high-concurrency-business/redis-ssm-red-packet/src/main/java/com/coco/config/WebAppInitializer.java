package com.coco.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	// SpringIoC配置
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { RootConfig.class };
	}

	// DispatcherServlet 配置
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	// DispatchServlet 映射配置
	@Override
	protected String[] getServletMappings() {
		return new String[] { "*.do" };
	}

	//配置文件上传解析器，限制请求
	@Override
	protected void customizeRegistration(Dynamic dynamic) {
		String filepath = "C:\\Users\\Administrator\\Desktop";
		// 5MB
		long singleMax = (long) (5 * Math.pow(2, 20));
		// 10MB
		long totalMax = (long) (10 * Math.pow(2, 20));
		dynamic.setMultipartConfig(new MultipartConfigElement(filepath, singleMax, totalMax, 0));
	}

}
