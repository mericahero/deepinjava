package com.meric.deepinjava.concurrent;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class UCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        testCountDownLatch();

    }
    private static void testCountDownLatch() throws InterruptedException {
        CountDownLatch c = new CountDownLatch(3);
        Thread t1=new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(3000);
                System.out.println("t1 sleep 3000, wake up.");
                c.countDown();
            }
        });

        Thread t2=new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(2000);
                System.out.println("t2 sleep 2000, wake up.");
                c.countDown();
            }
        });

        Thread t3=new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(1000);
                System.out.println("t3 sleep 1000, wake up.");
                c.countDown();
            }
        });
        t1.start();
        t2.start();
        t3.start();

        c.await();

        System.out.println("main wake up.");

    }
}
