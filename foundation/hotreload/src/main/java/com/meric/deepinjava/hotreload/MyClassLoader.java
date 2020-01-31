package com.meric.deepinjava.hotreload;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader {

    private String classpath;

    public MyClassLoader(String classpath) {
        super(ClassLoader.getSystemClassLoader());
        this.classpath = classpath;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] data = loadClassData(name);
        return this.defineClass(name,data,0,data.length);
    }


    private byte[] loadClassData(String name){
        try{
            name = name.replace(".","//");
            FileInputStream fis = new FileInputStream(classpath + name + ".class");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //IOUtils.copy(fis,baos);
            return IOUtils.toByteArray(fis);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
