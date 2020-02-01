package com.meric.deepinjava.ldesruptor.examples.handler;

import com.lmax.disruptor.WorkHandler;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;

public class Handler2 extends AbstractUserEventHandler{
    @Override
    public String getHandlerName() {
        return "Handler2";
    }
}
