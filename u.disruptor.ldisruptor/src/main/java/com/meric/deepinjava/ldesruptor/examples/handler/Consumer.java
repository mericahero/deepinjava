package com.meric.deepinjava.ldesruptor.examples.handler;

import com.lmax.disruptor.WorkHandler;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;

import java.util.concurrent.atomic.AtomicInteger;

public class Consumer implements WorkHandler<UserEvent> {

    private AtomicInteger count = new AtomicInteger(0);
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private int id;
    public Consumer(){
        id = idCounter.incrementAndGet();
    }


    @Override
    public void onEvent(UserEvent event) throws Exception {
        Thread.sleep(100);
        count.incrementAndGet();
        event.addChain("consumer");
        //System.out.println(event);
    }

    public int getCount(){
        return count.get();
    }
}
