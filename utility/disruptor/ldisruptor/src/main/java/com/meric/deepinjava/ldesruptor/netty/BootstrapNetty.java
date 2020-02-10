package com.meric.deepinjava.ldesruptor.netty;

public class BootstrapNetty {
    public static void main(String[] args) {
        /**
         * 在使用netty进行接收处理数据的时候，尽量不要在工作线程上编写自己的代码逻辑
         *
         * 所以要使用异步的机制，比如使用线程池进行异步处理，不过使用线程池就意味着使用阻塞队列，可以使用disruptor代替，以更高效地处理逻辑
         */
    }
}
