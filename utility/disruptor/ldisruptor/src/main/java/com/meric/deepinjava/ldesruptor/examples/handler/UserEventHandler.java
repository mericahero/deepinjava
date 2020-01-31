package com.meric.deepinjava.ldesruptor.examples.handler;

        import com.lmax.disruptor.EventHandler;
        import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;

public class UserEventHandler implements EventHandler<UserEvent> {
    @Override
    public void onEvent(UserEvent event, long sequence, boolean endOfBatch) throws Exception {
        event.addChain(this.getClass().getSimpleName());
        System.out.println("received: sequence is: "+sequence + ", data is: "+event);
    }
}
