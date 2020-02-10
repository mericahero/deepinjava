package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import com.lmax.disruptor.RingBuffer;
import io.netty.channel.group.ChannelGroup;
import lombok.val;

public class QuoteDataRingBufferProducer {
    private RingBuffer<QuoteDataCarrier> ringBuffer;

    private int producerid;

    public QuoteDataRingBufferProducer(int id, RingBuffer<QuoteDataCarrier> buffer){
        producerid = id;
        ringBuffer = buffer;
    }

    public void sendData(String command, Object data){
        val sequence = ringBuffer.next();
        try{
            val origin = ringBuffer.get(sequence);
            origin.setCommand(command);
//            origin.setChannelGroup(group);
            origin.setQuoteData(data);
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
