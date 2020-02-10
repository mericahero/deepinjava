package com.meric.deepinjava.ldesruptor.examples;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;
import com.meric.deepinjava.ldesruptor.examples.event.UserEventFactory;
import com.meric.deepinjava.ldesruptor.examples.handler.*;
import com.meric.deepinjava.ldesruptor.examples.producer.UserEventProducerWithDisruptor;
import lombok.val;

import java.util.concurrent.*;

public class BootstrapSerialParallel {
    public static void main(String[] args) throws InterruptedException {

        // 生产者线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        EventFactory factory = new UserEventFactory();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(2<<10),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r);
                    }
                }
        );
        Disruptor<UserEvent> disruptor = new Disruptor<UserEvent>(
                factory,
                2 << 20,
                executor,
                ProducerType.SINGLE,
                new BlockingWaitStrategy()
        );


        //serial(disruptor);
        //parallel(disruptor);
        //parallel2(disruptor);
        polygon(disruptor);

        disruptor.start();

        CountDownLatch downLatch = new CountDownLatch(1);
        executorService.submit(new UserEventProducerWithDisruptor(disruptor,downLatch));

        downLatch.await();

        disruptor.shutdown();
        executor.shutdown();
        executorService.shutdown();

    }

    private static void serial(Disruptor< UserEvent> disruptor){
        val handler1 = new Handler1();
        val handler2 = new Handler2();
        val handler3 = new Handler3();
        val handler4 = new Handler4();
        val handler5 = new Handler5();

        disruptor.handleEventsWith(handler1).handleEventsWith(handler2);
    }

    private static void parallel(Disruptor< UserEvent> disruptor){
        val handler1 = new Handler1();
        val handler2 = new Handler2();
        val handler3 = new Handler3();
        val handler4 = new Handler4();
        val handler5 = new Handler5();

        disruptor.handleEventsWith(handler1);
        disruptor.handleEventsWith(handler2);
        disruptor.handleEventsWith(handler3);

    }

    private static void parallel2(Disruptor< UserEvent> disruptor){
        val handler1 = new Handler1();
        val handler2 = new Handler2();
        val handler3 = new Handler3();
        val handler4 = new Handler4();
        val handler5 = new Handler5();

        disruptor.handleEventsWith(handler1,handler2,handler3);

    }

    private static void polygon(Disruptor<UserEvent> disruptor){
        val handler1 = new Handler1();
        val handler2 = new Handler2();
        val handler3 = new Handler3();
        val handler4 = new Handler4();
        val handler5 = new Handler5();


        disruptor.handleEventsWith(handler1);
        disruptor.after(handler1).then(handler2);
        disruptor.after(handler1).then(handler3,handler4);
        disruptor.after(handler2,handler4).handleEventsWith(handler5);

    }



}
