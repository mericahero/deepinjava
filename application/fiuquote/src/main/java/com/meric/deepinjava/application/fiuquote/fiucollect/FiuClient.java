package com.meric.deepinjava.application.fiuquote.fiucollect;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.concurrent.TimeUnit;


public class FiuClient {
    private FiuConfigration configration;

    private FiuClient self;
    public FiuClient(FiuConfigration conf){
        configration = conf;
        self = this;
    }


    private int retryTimes = 0;

    private final int THRESHOLD = 5;
    private final int RETRYONESLOT = 5;

    ChannelFuture cf = null;
    NioEventLoopGroup workGroup = null;

    public void start(boolean isReconnect) throws InterruptedException {
        if(isReconnect && retryTimes>=THRESHOLD){
            System.err.println("连接失败，超过最大重试次数");
            TimeUnit.SECONDS.sleep(retryTimes * RETRYONESLOT);
            retryTimes++;
            start(true);
        }

        Bootstrap bootstrap = new Bootstrap();
        workGroup = new NioEventLoopGroup(1);
        try{
            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                    new DelimiterBasedFrameDecoder(1024*1024, Unpooled.copiedBuffer(new byte[]{0})),
                                    new StringDecoder(),
                                    new FiuDataHandler(self),
                                    new FiuDataExceptionHandler(self)
                                    );
                        }
                    });
            cf = bootstrap.connect(configration.getHost(),configration.getPort()).sync();
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        retryTimes = 0;
                        System.out.println("connect to fiu server success!");
                    }else{
                        System.out.println("connect to fiu server failed, try to reconnect");

                        cf.channel().eventLoop().schedule(()->{
                            try {
                                start(true);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        },10,TimeUnit.SECONDS);
                    }
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() throws InterruptedException {
        cf.channel().closeFuture().sync();
        workGroup.shutdownGracefully();
    }

    public void sayHello(){

    }

}
