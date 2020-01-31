package com.meric.deepinjava.ldesruptor.sourcecode;

public class SourceCodeBootstrap {
    public static void main(String[] args) {
        /**
         * disruptor 性能高的原因
         * 1. 数据结构方面，使用了环形结构、数组及内存预加载；
         * 2. 使用单线程写的方式，并且使用了内存屏障；
         * 3. 消除伪共享 （填充缓存行）；
         * 4. 序号栅栏和序号配合使用来消除锁以及CAS
         *
         * 单线程 无锁 前提的前提 Redis Netty 也都是单线程的
         * 内存屏障 valotile happen before
         * 消除伪共享 缓存行（Cache line）一般 32-356个字节，常用64字节 相互独立的变量，多线程操作如果命中同一个缓存行，就会有性能影响
         *
         * 序号栅栏机制 SequenceBarrier和 Sequence配合 消除锁和CAS
         */

        /**
         * RingBuffer Object[] entries
         */
    }
}
