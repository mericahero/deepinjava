package com.meric.deepinjava.ldesruptor.examples.event;

import com.lmax.disruptor.EventFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class UserEventFactory implements EventFactory<UserEvent> {
    private AtomicInteger ai = new AtomicInteger(0);
    @Override
    public UserEvent newInstance() {
        return new UserEvent(ai.incrementAndGet());
    }
}
