package com.meric.deepinjava.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock(false);
        Condition condition = lock.newCondition();

        new Thread(()->{
            lock.lock();
            try{
                System.out.println("thread 1 first");
                condition.await();
                System.out.println("thread 1 second");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(()->{
            lock.lock();
            try{
                System.out.println("thread 2 first");
                condition.await();
                System.out.println("thread 2 second");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(()->{
            lock.lock();
            try{
                System.out.println("thread 3 first");
                condition.await();
                System.out.println("thread 3 second");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        Thread t3 = new Thread(() -> {
            lock.lock();
            try {
                System.out.println("thread signal first");
                condition.signalAll();
                System.out.println("thread signal second");
            } finally {
                lock.unlock();
            }
        });

        TimeUnit.MICROSECONDS.sleep(100);
        t3.start();
    }
}
