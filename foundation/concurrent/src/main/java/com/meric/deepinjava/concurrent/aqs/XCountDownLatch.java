package com.meric.deepinjava.concurrent.aqs;



import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.zip.CheckedOutputStream;

public class XCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        int count = 100;
        CountDownLatch cdl = new CountDownLatch(count);

        ExecutorService ser = Executors.newCachedThreadPool();

        for (int i = 0; i <count; i++) {
            int finalI = i;
            ser.execute(()->{
                try {
                    TimeUnit.SECONDS.sleep(finalI%5);
                    cdl.countDown();
                    System.out.println(finalI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }
        cdl.await(3, TimeUnit.SECONDS);
        System.out.println("当前"+cdl.getCount());

    }
}
