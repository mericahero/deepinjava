package com.meric.deepinjava.lpool;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        testPool();
    }

    private static void testPool(){
        final PooledObjectFactory<Resource> factory = new ResourcePooledObjectFactory();
        final GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        final GenericObjectPool<Resource> pool = new GenericObjectPool<>(factory,poolConfig);
        final int count = 100;
        for (int i = 0; i < count; i++) {
            new Thread(()->{
                try{
                    Resource resource =  pool.borrowObject();
                    pool.returnObject(resource);

                }catch (Exception e){
                    e.printStackTrace();
                }finally {

                }
            }).start();
        }


    }
}
