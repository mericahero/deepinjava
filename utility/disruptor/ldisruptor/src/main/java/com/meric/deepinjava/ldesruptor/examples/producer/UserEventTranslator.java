package com.meric.deepinjava.ldesruptor.examples.producer;

import com.lmax.disruptor.EventTranslator;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;

import java.util.Random;

public class UserEventTranslator implements EventTranslator<UserEvent> {
    private Random rnd = new Random();
    @Override
    public void translateTo(UserEvent event, long sequence) {
        generateUserEvent(event);
    }

    private void generateUserEvent(UserEvent event) {
        event.setAge(rnd.nextInt());
    }
}
