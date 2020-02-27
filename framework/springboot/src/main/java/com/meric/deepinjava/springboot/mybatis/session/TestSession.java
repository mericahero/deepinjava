package com.meric.deepinjava.springboot.mybatis.session;

import java.lang.reflect.Proxy;

public class TestSession {

    public Object getMapper(Class clazz){
        return Proxy.newProxyInstance(
                TestSession.class.getClassLoader(),
                new Class[]{clazz},
                new SessionHandler());
    }
}
