package com.meric.deepinjava.ldesruptor.examples;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;
import com.meric.deepinjava.ldesruptor.examples.event.UserEventFactory;
import com.meric.deepinjava.ldesruptor.examples.handler.Consumer;
import com.meric.deepinjava.ldesruptor.examples.producer.UserEventProducerWithRingBuffer;
import lombok.val;

import java.nio.ByteBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class BootstrapMutiConsumer {
    public static void main(String[] args) throws InterruptedException {
        // 1、创建ringbuffer
        RingBuffer<UserEvent> ringBuffer = RingBuffer.create(
                ProducerType.SINGLE,
                new UserEventFactory(),
                2 << 20,
                new YieldingWaitStrategy()
        );
        // 2、序号屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 3、创建多个消费者
        val consumerCount = 10;
        val consumers = new Consumer[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            consumers[i] = new Consumer();
        }

        // 4、构建workpool
        WorkerPool<UserEvent> wpool = new WorkerPool<>(
                ringBuffer,
                sequenceBarrier,
                new ExceptionHandler<UserEvent>() {
                    @Override
                    public void handleEventException(Throwable ex, long sequence, UserEvent event) {

                    }

                    @Override
                    public void handleOnStartException(Throwable ex) {

                    }

                    @Override
                    public void handleOnShutdownException(Throwable ex) {

                    }
                },
                consumers);

        // 5、设置多个消费者的sequence用于单独的消费者进度，并且设置到ringBuffer中
        ringBuffer.addGatingSequences(wpool.getWorkerSequences());

        // 6、启用workderpool
        wpool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

        // 生产者向RingBuffer中投递数据
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i <100; i++) {
            UserEventProducerWithRingBuffer p = new UserEventProducerWithRingBuffer(ringBuffer);
            new Thread(()->{
                try{
                    latch.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
                for (int j=0;j<100;j++) {
                    ByteBuffer bb = ByteBuffer.allocate(4);
                    bb.putInt(0);
                    p.sendData(bb);
                }
            }).start();
        }

        Thread.sleep(1000);
        latch.countDown();
        new Thread(()->{
            for(;;) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < consumerCount; i++) {
                    sb.append("consumer [" + i + "] 消费到 " + consumers[i].getCount() + "\n");
                }

                sb.append("实际sequence\n");
                Sequence[] workerSequences = wpool.getWorkerSequences();
                for (int i = 0; i < workerSequences.length; i++) {
                    sb.append("consumer [" + i + "] 消费到 " + workerSequences[i].get() + "\n");
                }


                System.out.println(sb.toString());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
