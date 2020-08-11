package com.meric.deepinjava.resume.proxy;

public class MyOperate implements IOperate {
    @Override
    public void act(String name) {
        System.out.println("[my operate] name:"+name);
    }
}
