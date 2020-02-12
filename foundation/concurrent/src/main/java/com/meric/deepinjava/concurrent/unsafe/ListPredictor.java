package com.meric.deepinjava.concurrent.unsafe;

import com.meric.deepinjava.concurrent.ConcurrentPredictor;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ListPredictor implements ConcurrentPredictor {

    private AtomicInteger acc = new AtomicInteger(0);
    private List<Integer> list = new ArrayList<>();



    public ListPredictor(){
        list = new Stack<>();
        list = new Vector<>();
        list = Collections.synchronizedList(list);
        list = new CopyOnWriteArrayList<>();
    }

    @Override
    public void action() throws ParseException {
        list.add(acc.incrementAndGet());
    }

    @Override
    public int predict() {
        return list.size();
    }
}
