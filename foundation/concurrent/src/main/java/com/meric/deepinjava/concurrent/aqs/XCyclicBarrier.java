package com.meric.deepinjava.concurrent.aqs;

import java.util.concurrent.*;

public class XCyclicBarrier {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        CyclicBarrier barrier = new CyclicBarrier(5);
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            TimeUnit.SECONDS.sleep(1);
            service.execute(()->{
                System.out.println(finalI);
                try {

                    barrier.await();
                    System.out.println(finalI+" done");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

            });
        }
        service.shutdown();
    }
}
