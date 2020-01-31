package com.meric.deepinjava.disruptornetty.common.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateData;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateDataEvent;
import io.netty.channel.ChannelHandlerContext;
import lombok.Data;
import lombok.val;

@Data
public class MessageProducer {
    private String producerId;
    private RingBuffer<TranslateDataEvent> ringBuffer;
    public MessageProducer(String pid,RingBuffer<TranslateDataEvent> buffer){
        this.producerId = pid;
        this.ringBuffer = buffer;
    }

    public void sendData(TranslateData translateData, ChannelHandlerContext ctx){
        long sequence = ringBuffer.next();
        try{
            val data = ringBuffer.get(sequence);
            data.setContext(ctx);
            data.setData(translateData);

        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
