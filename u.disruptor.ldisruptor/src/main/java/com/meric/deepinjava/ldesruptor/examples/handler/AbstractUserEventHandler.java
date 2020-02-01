package com.meric.deepinjava.ldesruptor.examples.handler;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;

public class AbstractUserEventHandler implements EventHandler<UserEvent> {
    protected String handlerName = null;

    public String getHandlerName() {
        return handlerName;
    }

    @Override
    public void onEvent(UserEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.addChain(getHandlerName());
        System.out.println(event);
    }
}
