package com.meric.deepinjava.concurrent.aqs;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class XForkJoin extends RecursiveTask<Integer> {

    private final int THRESHOLD = 2;
    private int start;
    private int end;

    public XForkJoin(int s,int e){
        start = s;
        end = e;
    }


    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end-start)<=THRESHOLD;
        if(canCompute){
            for (int i = start; i <= end; i++) {
                sum+=i;
            }
        }else{
            int middle = (start+end)/2;
            XForkJoin left = new XForkJoin(start, middle);
            XForkJoin right = new XForkJoin(middle+1,end);

            left.fork();
            right.fork();

            int leftResult = left.join();
            int rightResult = right.join();

//            right.fork();
//            int leftResult = left.compute();
//            int rightResult = right.join();

            sum = leftResult+rightResult;


        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();

        XForkJoin xForkJoin = new XForkJoin(1, 100);

        ForkJoinTask<Integer> resultTask = pool.submit(xForkJoin);


        Integer result = resultTask.get();

        System.out.println(result);
        System.out.println(System.currentTimeMillis()-start);
    }


}
