package com.meric.deepinjava.concurrent.unsafe;

import com.meric.deepinjava.concurrent.ConcurrentLauncher;
import com.meric.deepinjava.concurrent.ConcurrentPredictor;

public class UnsafeExample {



    public static void main(String[] args) throws InterruptedException {
        ConcurrentPredictor predictor = new StringBuilderPredictor();
        predictor = new StringBufferPredictor();
        ConcurrentLauncher launcher = new ConcurrentLauncher(
                5000,
                200,
                predictor
        );
        launcher.start();
    }



}
