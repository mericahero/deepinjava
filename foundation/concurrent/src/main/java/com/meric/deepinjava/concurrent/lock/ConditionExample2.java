package com.meric.deepinjava.concurrent.lock;

import javax.sound.midi.Soundbank;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample2 {
    public static void main(String[] args) throws InterruptedException {

        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        CountDownLatch latch = new CountDownLatch(1);
        Thread t = new Thread(()->{
            System.out.println("thread start ...");
            lock.lock();
            try{
                System.out.println("locked..");
                latch.await();
                System.out.println("latch continue");
                condition.await();
                System.out.println("condition continue");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("unlock");
            }
        });

        t.start();

        Thread t1 = new Thread(()->{
            latch.countDown();

            try {
                Thread.sleep(10);
                lock.lock();
                condition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }


        });

        t1.start();


    }
}
