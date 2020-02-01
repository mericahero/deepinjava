package com.meric.deepinjava.ldesruptor.examples.handler;

import com.lmax.disruptor.WorkHandler;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;

public class Handler3 extends AbstractUserEventHandler{
    @Override
    public String getHandlerName() {
        return "Handler3";
    }
}
