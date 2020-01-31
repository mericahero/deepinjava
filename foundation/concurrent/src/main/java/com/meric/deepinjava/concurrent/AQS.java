package com.meric.deepinjava.concurrent;

import lombok.SneakyThrows;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class AQS {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("AQS");

        /**
         * ReentrantLock
         *
         * ReentrantReadWriteLock
         *
         * Condition
         *
         * LockSupport 基于线程的锁 pack 和 unpack 的顺序无所谓
         */

        /**
         * AQS架构核心
         *
         * 资源 + 队列
         *
         * 独占 共享 方式
         *  isHeldExcluively
         *  tryAcquire tryRelease
         *  tryAcquireShared tryReleaseShared
         *
         * ReentrantLock state 0 tryAcquire 1 其它线程再调用则失败 直到 unlock
         */
        //testSynchronized();
        testLockSupport();


    }

    private static void testSynchronized() throws InterruptedException {
        System.out.println("Synchronized");
        Object lock = new Object();

        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i < 10; i++) {
                    sum+=i;
                }
                synchronized (lock){
                    try{
                        lock.wait();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                System.out.println("sum:" + sum);
            }
        });
        a.start();
        Thread.sleep(200);
        /**
         * wait 方法释放锁 notify 不释放锁
         */
        synchronized (lock){
            lock.notify();
        }
    }

    public static void testLockSupport() throws InterruptedException {
        System.out.println("LockSupport");
        Thread a = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                int sum = 0;
                for (int i = 0; i <10; i++) {
                    sum+=i;
                }
                Thread.sleep(2000);
                LockSupport.park();
                System.out.println("sum: "+ sum);
            }
        });
        a.start();
        Thread.sleep(200);
        LockSupport.unpark(a);
    }

    private static void testReentrantLock(){
        ReentrantLock lock = new ReentrantLock();

    }
}
