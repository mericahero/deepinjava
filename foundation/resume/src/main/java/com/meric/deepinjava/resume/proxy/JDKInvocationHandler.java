package com.meric.deepinjava.resume.proxy;

import jdk.nashorn.internal.ir.debug.PrintVisitor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKInvocationHandler implements InvocationHandler {


    private Object targetObject;
    private Class<?>[] targetInterfaces;

    public JDKInvocationHandler(Object target, Class<?>... interfaces){
        this.targetObject=target;
        this.targetInterfaces=interfaces;
    }

    public Object getProxyObject(){
        return Proxy.newProxyInstance(getClass().getClassLoader(),targetInterfaces,this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("[jdk proxy] method:" + method.getName() + " begin.");
        Object result = method.invoke(targetObject,args);
        System.out.println("[jdk proxy] method:" + method.getName() + " end.");
        return result;
    }
}
