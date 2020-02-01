package com.meric.deepinjava.disruptornetty.common.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateDataEvent;
import lombok.val;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RingBufferWorkPoolFactory {
    private static class RingBufferWorkPoolFactoryHolder {
        static final RingBufferWorkPoolFactory Instance = new RingBufferWorkPoolFactory();
    }
    public static RingBufferWorkPoolFactory getInstance(){
        return RingBufferWorkPoolFactoryHolder.Instance;
    }


    GenericObjectPool<MessageProducer> producerPool;
    RingBuffer<TranslateDataEvent> ringBuffer;
    GenericObjectPool<? extends MessageConsumer> consumerPool;
    private static AtomicInteger producerIncr = new AtomicInteger(0);


    public void start(MessageConsumer[] consumers){

        ringBuffer = RingBuffer.create(
                ProducerType.MULTI,
                new EventFactory<TranslateDataEvent>() {
                    @Override
                    public TranslateDataEvent newInstance() {
                        return new TranslateDataEvent();
                    }
                },
                1024*1024,
                new BlockingWaitStrategy()
        );

        val sequenceBarrier = ringBuffer.newBarrier();

        val workerPool = new WorkerPool<TranslateDataEvent>(
                ringBuffer,
                sequenceBarrier,
                new ExceptionHandler<TranslateDataEvent>() {
                    @Override
                    public void handleEventException(Throwable ex, long sequence, TranslateDataEvent event) {

                    }

                    @Override
                    public void handleOnStartException(Throwable ex) {

                    }

                    @Override
                    public void handleOnShutdownException(Throwable ex) {

                    }
                },
                consumers);

        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()/2));

        initProducerPool();
    }


    public void producerInvoke(Consumer<MessageProducer> action) throws Exception {
        val producer =  producerPool.borrowObject();
        action.accept(producer);
        System.out.println("[Producer "+producer.getProducerId()+"] send data");
        producerPool.returnObject(producer);

    }


    private void initProducerPool(){
        val producerFactory = new BasePooledObjectFactory<MessageProducer>() {
            @Override
            public MessageProducer create() throws Exception {
                return new MessageProducer("producer"+producerIncr.incrementAndGet(),ringBuffer);
            }

            @Override
            public PooledObject<MessageProducer> wrap(MessageProducer messageProducer) {
                return new DefaultPooledObject<>(messageProducer);
            }
        };

        val config = new GenericObjectPoolConfig<MessageProducer>();
        config.setMaxIdle(2);
        config.setMaxTotal(10);
        config.setMinIdle(0);
        producerPool = new GenericObjectPool<>(producerFactory,config);

    }


}
