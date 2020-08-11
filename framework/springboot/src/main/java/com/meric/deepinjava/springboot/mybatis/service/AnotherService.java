package com.meric.deepinjava.springboot.mybatis.service;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE,proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AnotherService {
    public long a = System.currentTimeMillis();
    @PostConstruct
    public void init(){
        a = System.currentTimeMillis();
    }
}
