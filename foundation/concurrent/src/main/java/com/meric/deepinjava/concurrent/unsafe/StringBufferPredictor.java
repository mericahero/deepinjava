package com.meric.deepinjava.concurrent.unsafe;

import com.meric.deepinjava.concurrent.ConcurrentPredictor;

public class StringBufferPredictor implements ConcurrentPredictor {
    private static StringBuffer sb = new StringBuffer();
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
