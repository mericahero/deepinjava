package com.meric.deepinjava.concurrent.lock;

public class ReenterLockExample {

    private static synchronized void A(){
        System.out.println("A");
    }

    private static synchronized void B(){
        System.out.println("B");
    }
}
