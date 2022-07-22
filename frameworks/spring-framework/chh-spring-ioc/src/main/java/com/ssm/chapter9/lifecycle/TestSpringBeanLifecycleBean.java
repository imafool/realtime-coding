package com.ssm.chapter9.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class TestSpringBeanLifecycleBean implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

    public void init() {
        System.out.println("6. Custom..init()....for bean!");
    }

    public void destroy() {
        System.out.println("10. Custom..destroy()....for bean!");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("2. BeanFactoryAware..aware....for BeanFactory!");
    }

    @Override
    public void setBeanName(String s) {
        System.out.println("1. BeanNameAware..aware....for bean!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("5. InitializingBean..initial....for bean!");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("3. ApplicationContextAware..aware....for ApplicationContext!");
    }

    public void doSomething(){
        System.out.println("8. do something!");
    }
}
