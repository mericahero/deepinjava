package com.meric.deepinjava.concurrent;


import java.util.concurrent.*;

public class ConcurrentLauncher {

    private int totalCount;
    private int threadCount;
    private ExecutorService exeService;
    private ConcurrentPredictor predictor;
    private CompletableFuture<Boolean> future;


    public ConcurrentLauncher(final int totalCount,final int threadCount,ConcurrentPredictor predictor){
        this(totalCount,threadCount,predictor,Executors.newCachedThreadPool());
    }



    public ConcurrentLauncher(final int totalCount, final int threadCount,ConcurrentPredictor predictor,ExecutorService service){
        this.totalCount = totalCount;
        this.threadCount = threadCount;
        this.predictor = predictor;
        this.exeService = service;
        this.future = new CompletableFuture<>();
    }

    public void start() throws InterruptedException {
        Semaphore semaphore = new Semaphore(threadCount);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < totalCount; i++) {
            exeService.execute(()->{
                try {
                    semaphore.acquire();
                    try{
                        predictor.action();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });

        }
        countDownLatch.await();
        exeService.shutdown();
        System.out.println(predictor.predict());
        this.future.complete(predictor.predict() == totalCount);

    }

    public boolean getPredictResult() throws ExecutionException, InterruptedException {
        return future.get();
    }





}
