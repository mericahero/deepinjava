package com.meric.deepinjava.concurrent;

import java.util.concurrent.*;

public class ExcutorsThreadPoolExcutor {
    public static void main(String[] args) {

        /**
         * 如何正确使用线程池
         * 1、线程池大小、数量， 心里有数，
         * 2、一定要使用有界队列；
         * 3、利用Hook嵌入行为；
         * 4、一定要优雅地关闭
         */
    }

    private static void testExcutors(){
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(10);

    }

    private static void testCutomExecutors(){
        new ThreadPoolExecutor(
                5,
                Runtime.getRuntime().availableProcessors() * 2,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(200),


                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r);

                        return t;
                    }
                },
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.err.println("reject; "+ r);
                    }
                }
        );
    }
}
