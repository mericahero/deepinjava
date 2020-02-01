package com.meric.deepinjava.hotreload;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        //new Thread(new MsgHander()).start();
        try {
            ClassLoader cloader = new MyClassLoader("/Users/meric/Repositories/Study/spare/java/DeepInJava/hotreload/target/classes");
            Class<?> aClass = cloader.loadClass("com.meric.deepinjava.hotreload.MyManager");
            Method logic = aClass.getMethod("logic");
            Object o = aClass.newInstance();
            logic.invoke(o);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
