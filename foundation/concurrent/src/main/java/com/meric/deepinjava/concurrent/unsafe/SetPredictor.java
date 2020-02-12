package com.meric.deepinjava.concurrent.unsafe;

import com.google.common.collect.Sets;
import com.meric.deepinjava.concurrent.ConcurrentPredictor;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

public class SetPredictor implements ConcurrentPredictor {

    private AtomicInteger acc = new AtomicInteger(0);
    private Set<Integer> set ;

    public SetPredictor(){
        set = new HashSet<>();
        set = Collections.synchronizedSet(Sets.newHashSet());
        set = new CopyOnWriteArraySet<>();
        set = new ConcurrentSkipListSet<>();
    }

    @Override
    public void action() throws ParseException {
        set.add(acc.incrementAndGet());
    }

    @Override
    public int predict() {
        return set.size();
    }
}
