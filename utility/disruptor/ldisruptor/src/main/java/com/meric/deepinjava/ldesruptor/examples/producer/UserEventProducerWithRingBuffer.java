package com.meric.deepinjava.ldesruptor.examples.producer;

import com.lmax.disruptor.RingBuffer;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;
import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.val;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class UserEventProducerWithRingBuffer {

    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private int id;

    public UserEventProducerWithRingBuffer(){
        id = idCounter.incrementAndGet();
    }
    @NotNull
    private RingBuffer<UserEvent> ringBuffer;


    public UserEventProducerWithRingBuffer(RingBuffer<UserEvent> rb){
        ringBuffer=rb;
    }

    public void sendData(ByteBuffer buffer){
        long sequence = ringBuffer.next();
        try {
            val event = ringBuffer.get(sequence);
            buffer.rewind();
            event.setProducer(id);
            event.setAge(buffer.getInt());

        }finally {
            ringBuffer.publish(sequence);
        }
    }

}
