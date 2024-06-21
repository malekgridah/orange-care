package com.billcom.connectionpools.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;

    public static <T extends Object> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        setContext(context);
    }

    private static synchronized void setContext(ApplicationContext context) {
        SpringContext.context = context;
    }
}
