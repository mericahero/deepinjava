package com.meric.deepinjava.concurrent.unsafe;

import com.meric.deepinjava.concurrent.ConcurrentPredictor;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MapPredictor implements ConcurrentPredictor {

    private AtomicInteger acc = new AtomicInteger(0);
    private Map<Integer,Integer> map;

    public MapPredictor(){
        map = new HashMap<>();
        map = new ConcurrentHashMap<>();
        map = new ConcurrentSkipListMap<>();
    }

    @Override
    public void action() throws ParseException {
        map.put(acc.incrementAndGet(),acc.get());
    }

    @Override
    public int predict() {
        return map.size();
    }
}
