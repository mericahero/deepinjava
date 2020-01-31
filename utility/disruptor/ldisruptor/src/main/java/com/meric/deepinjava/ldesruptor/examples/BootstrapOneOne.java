package com.meric.deepinjava.ldesruptor.examples;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.meric.deepinjava.ldesruptor.examples.event.UserEvent;
import com.meric.deepinjava.ldesruptor.examples.event.UserEventFactory;
import com.meric.deepinjava.ldesruptor.examples.handler.UserEventHandler;
import com.meric.deepinjava.ldesruptor.examples.producer.UserEventProducerWithRingBuffer;
import lombok.val;

import java.nio.ByteBuffer;
import java.util.concurrent.*;

public class BootstrapOneOne {
    public static void main(String[] args) {

        /**
         * 1、创建Event事件类，创建Event工厂类；
         * 2、创建监听事件类，用于处理Event事件；
         * 3、实例化Disruptor，传递好配置参数，编写核心组件；
         * 4、创建生产者，向disruptor投递数据。
         */

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

        disruptor.handleEventsWith(new UserEventHandler());


        disruptor.start();
        val ringBuffer = disruptor.getRingBuffer();

        val producer = new UserEventProducerWithRingBuffer(ringBuffer);

        for (int i = 0; i <10; i++) {
            val buffer = ByteBuffer.allocate(4);
            buffer.putInt(i);
            producer.sendData(buffer);
        }

        disruptor.shutdown();
        executor.shutdown();

    }
}
