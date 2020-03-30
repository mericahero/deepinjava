package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.val;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;


public class FiuDataRingBufferWorker {

    private static class FiuDataRingBufferWorkerHolder {
        static final FiuDataRingBufferWorker Instance = new FiuDataRingBufferWorker();
    }
    public static FiuDataRingBufferWorker getInstance(){
        return FiuDataRingBufferWorkerHolder.Instance;
    }


    private static final AtomicInteger producerCounter = new AtomicInteger(0);
    private RingBuffer<FiuDataCarrier> ringBuffer;

    private GenericObjectPool<FiuDataRingBufferProducer> producerPool;


    public void start(int consumerCount){

        val consumers = new FiuDataConsumer[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            consumers[i] = new FiuDataConsumer();
        }

        ringBuffer = RingBuffer.create(
                ProducerType.MULTI,
                new EventFactory<FiuDataCarrier>() {
                    @Override
                    public FiuDataCarrier newInstance() {
                        return new FiuDataCarrier();
                    }
                },
                1024*1024,
                new YieldingWaitStrategy()
        );
        val sequenceBarrier = ringBuffer.newBarrier();

        val workerPool = new WorkerPool<>(
                ringBuffer,
                sequenceBarrier,
                new ExceptionHandler<FiuDataCarrier>() {
                    @Override
                    public void handleEventException(Throwable throwable, long l, FiuDataCarrier fiuDataCarrier) {

                    }

                    @Override
                    public void handleOnStartException(Throwable throwable) {

                    }

                    @Override
                    public void handleOnShutdownException(Throwable throwable) {

                    }
                },
                consumers
        );
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());


        workerPool.start(new ThreadPoolExecutor(
                10,
                10,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1024)
        ));

        initialProducerPool();

    }

    public void invokeProduceData(Consumer<FiuDataRingBufferProducer> action) throws Exception {
        val producer = producerPool.borrowObject();
        action.accept(producer);
        producerPool.returnObject(producer);
    }


    private void initialProducerPool(){
        val factory = new BasePooledObjectFactory<FiuDataRingBufferProducer>(){

            @Override
            public FiuDataRingBufferProducer create() throws Exception {
                return new FiuDataRingBufferProducer(producerCounter.incrementAndGet(), ringBuffer);
            }

            @Override
            public PooledObject<FiuDataRingBufferProducer> wrap(FiuDataRingBufferProducer fiuDataRingBufferProducer) {
                return new DefaultPooledObject<>(fiuDataRingBufferProducer);
            }
        };

        val config = new GenericObjectPoolConfig<FiuDataRingBufferProducer>();
        config.setMinIdle(0);
        config.setMaxTotal(Runtime.getRuntime().availableProcessors()*2);
        producerPool = new GenericObjectPool<>(factory,config);


    }

}
