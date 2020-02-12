package com.meric.deepinjava.concurrent.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class XSemaphore {
    public static void main(String[] args) {
        int count = 10;
        Semaphore semaphore = new Semaphore(3);

        ExecutorService ser = Executors.newCachedThreadPool();

        for (int i = 0; i <count; i++) {
            int finalI = i;
            ser.execute(()->{
                try {
                    semaphore.acquire();
                    System.out.println(finalI);
                    TimeUnit.SECONDS.sleep(1);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        ser.shutdown();
    }
}
