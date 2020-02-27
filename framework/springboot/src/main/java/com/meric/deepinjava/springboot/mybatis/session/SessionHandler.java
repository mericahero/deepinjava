package com.meric.deepinjava.springboot.mybatis.session;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SessionHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Select annotation = method.getAnnotation(Select.class);
        if(annotation!=null){
            System.out.println(Arrays.toString(annotation.value()));
        }

        return proxy.getClass().getName();
    }
}
