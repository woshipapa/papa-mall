package com.papa.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.swing.*;
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(SpringUtil.applicationContext == null)
            SpringUtil.applicationContext=applicationContext;
    }

    public static Object getBean(String name){return applicationContext.getBean(name);}

    public static <T> T getBean(Class<T> clazz){return applicationContext.getBean(clazz);}


    public static <T> T getBean(String name,Class<T> clazz){return applicationContext.getBean(name,clazz);}
}
