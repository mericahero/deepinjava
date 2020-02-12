package com.meric.deepinjava.concurrent.lock;

import com.meric.deepinjava.concurrent.ConcurrentPredictor;

import java.text.ParseException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPredictor implements ConcurrentPredictor {


    private int a = 0;
    private Lock lock = new ReentrantLock();
    @Override
    public void action() throws ParseException {
        lock.lock();
        a++;
        lock.unlock();
    }

    @Override
    public int predict() {
        return a;
    }
}
