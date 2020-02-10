package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import com.lmax.disruptor.RingBuffer;
import io.netty.channel.ChannelHandlerContext;
import lombok.val;


public class FiuDataRingBufferProducer {
    private RingBuffer<FiuDataCarrier> ringBuffer;

    private int producerid;

    public FiuDataRingBufferProducer(int id, RingBuffer<FiuDataCarrier> buffer){
        producerid = id;
        ringBuffer = buffer;
    }

    public void sendData(String data, ChannelHandlerContext ctx){
        val sequence = ringBuffer.next();
        try{
            val origin = ringBuffer.get(sequence);
            origin.setCtx(ctx);
            origin.setProtocolData(data);
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
