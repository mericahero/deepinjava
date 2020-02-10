package com.meric.deepinjava.disruptornetty.server;

import com.meric.deepinjava.disruptornetty.common.disruptor.MessageConsumer;
import com.meric.deepinjava.disruptornetty.common.disruptor.RingBufferWorkPoolFactory;
import lombok.val;

public class NettyServerApplication {
    public static void main(String[] args) {
        val consumers = new MessageConsumer[4];
        for (int i=0;i<consumers.length;++i){
            consumers[i]=new ServerMessageConsumer("c"+i);
        }
        RingBufferWorkPoolFactory.getInstance().start(consumers);
        new NettyServer();
    }
}
