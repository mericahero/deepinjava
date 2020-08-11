package com.meric.deepinjava.resume.proxy;

import lombok.val;

public class ProxyExample {
    public static void main(String[] args) {

        val handler = new JDKInvocationHandler(new MyOperate(),IOperate.class);
        val proxy = (IOperate)handler.getProxyObject();
        proxy.act("meric");
    }
}
