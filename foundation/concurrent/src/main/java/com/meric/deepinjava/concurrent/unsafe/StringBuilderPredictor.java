package com.meric.deepinjava.concurrent.unsafe;

import com.meric.deepinjava.concurrent.ConcurrentPredictor;

public class StringBuilderPredictor implements ConcurrentPredictor {
    private static StringBuilder sb = new StringBuilder();
    @Override
    public void action() {
        sb.append(1);
    }

    @Override
    public int predict() {
        System.out.println(sb.length());
        return sb.length();
    }
}
