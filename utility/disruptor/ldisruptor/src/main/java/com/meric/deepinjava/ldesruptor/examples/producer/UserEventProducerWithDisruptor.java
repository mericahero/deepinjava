package com.meric.deepinjava.ldesruptor.examples.producer;

import com.lmax.disruptor.dsl.Disruptor;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;

import java.util.concurrent.CountDownLatch;

public class UserEventProducerWithDisruptor implements Runnable {
    private Disruptor<UserEvent> disruptor;
    private CountDownLatch downLatch;
    private final int ACC_COUNT = 2;

    public UserEventProducerWithDisruptor(Disruptor<UserEvent> ruptor, CountDownLatch c) {
        disruptor = ruptor;
        downLatch = c;
    }

    @Override
    public void run() {
        for (int i = 0; i < ACC_COUNT; i++) {
            disruptor.publishEvent(new UserEventTranslator());
        }
        downLatch.countDown();
    }
}
