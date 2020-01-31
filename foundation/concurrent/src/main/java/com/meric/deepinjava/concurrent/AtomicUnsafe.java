package com.meric.deepinjava.concurrent;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicUnsafe {
    public static void main(String[] args) {

        /*
        * Atomic
        *  原子性操作保障多线程下的安全运行
        *
        * Unsafe
        *  + 内存操作
        *  + 字段的定位与修改
        *  + 挂起与恢复
        *  + CAS
        */

        AtomicLong al = new AtomicLong(1L);
        boolean flag = al.compareAndSet(1, 2);
        System.out.println(flag);


    }
}
