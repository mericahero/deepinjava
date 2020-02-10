package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;
import lombok.val;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class QuoteDataRingBufferWorker {
    private static class QuoteDataRingBufferWorkerHolder {
        static QuoteDataRingBufferWorker Instance = new QuoteDataRingBufferWorker();
    }

    public static QuoteDataRingBufferWorker getInstance(){
        return QuoteDataRingBufferWorkerHolder.Instance;
    }

    private static AtomicInteger producerIdGenerator = new AtomicInteger(0);

    ObjectPool<QuoteDataRingBufferProducer> producerPool;

    public void start(int consumerCount){
        val consumers = new QuoteDataConsumer[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            consumers[i]=new QuoteDataConsumer();
        }

        val ringBuffer = RingBuffer.create(
                ProducerType.MULTI,
                new EventFactory<QuoteDataCarrier>() {
                    @Override
                    public QuoteDataCarrier newInstance() {
                        return new QuoteDataCarrier();
                    }
                },
                1024*1024,
                new YieldingWaitStrategy()
        );

        val sequenceBarrier = ringBuffer.newBarrier();

        val workerPool = new WorkerPool<QuoteDataCarrier>(
                ringBuffer,
                sequenceBarrier,
                new ExceptionHandler<QuoteDataCarrier>() {
                    @Override
                    public void handleEventException(Throwable throwable, long l, QuoteDataCarrier quoteDataCarrier) {

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

        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2));

        initialProducerPool(ringBuffer);

    }

    private void initialProducerPool(RingBuffer<QuoteDataCarrier> ringBuffer) {
        val factory = new BasePooledObjectFactory<QuoteDataRingBufferProducer>() {
            @Override
            public QuoteDataRingBufferProducer create() throws Exception {
                return new QuoteDataRingBufferProducer(producerIdGenerator.incrementAndGet(), ringBuffer);
            }

            @Override
            public PooledObject<QuoteDataRingBufferProducer> wrap(QuoteDataRingBufferProducer quoteDataRingBufferProducer) {
                return new DefaultPooledObject<>(quoteDataRingBufferProducer);
            }
        };
        val config = new GenericObjectPoolConfig<QuoteDataRingBufferProducer>();
        config.setMinIdle(0);
        config.setMaxTotal(Runtime.getRuntime().availableProcessors()*2);
        producerPool = new GenericObjectPool<QuoteDataRingBufferProducer>(factory,config);
    }

    public void invokeProduceData(Consumer<QuoteDataRingBufferProducer> action) throws Exception {
        val producer = producerPool.borrowObject();
        action.accept(producer);
        producerPool.returnObject(producer);
    }
}
