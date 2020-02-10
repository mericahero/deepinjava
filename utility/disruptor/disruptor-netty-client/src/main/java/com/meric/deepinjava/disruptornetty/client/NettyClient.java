package com.meric.deepinjava.disruptornetty.client;

import com.meric.deepinjava.disruptornetty.common.util.MarshallingCoder;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateData;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyClient {

    public static final String HOST = "127.0.0.1";
    public static final int PORT = 8765;
    private Channel channel;
    private ChannelFuture cf;
    // 1、创建两个工作线程组，一个用于接收网络请求，一个用于处理业务逻辑
    EventLoopGroup workGroup = new NioEventLoopGroup();
    public NettyClient() {
        connect(HOST, PORT);
    }

    private void connect(String host, int port) {


        Bootstrap bootstrap = new Bootstrap();

        try {

            bootstrap.group(workGroup)
                    .channel(NioSocketChannel.class)
                    //表示缓存区动态调配（自适应）
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    //缓存区 池化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 接收数据进行回调处理，尽量不要在这个handler里处理逻辑代码
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast(
                                    MarshallingCoder.getEncoder(),
                                    MarshallingCoder.getDecoder(),
                                    new ClientHandler()
                            );

//                            ch.pipeline().addLast(
//                                    new ObjectEncoder(),
//                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
//                                    new ClientHandler()
//                            );
                        }
                    });
            cf = bootstrap.connect(host, port).sync();
            System.out.println("client connected ...");

            channel = cf.channel();

            // 发送数据


            //sendData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendData() {
        for (int i = 0; i < 10; i++) {
            TranslateData data = new TranslateData();
            data.setId(i);
            data.setName("请求名称 " + i);
            data.setMessage("请求内容 " + i);

            this.channel.writeAndFlush(data);
        }
    }

    public void close() throws InterruptedException {

        cf.channel().closeFuture().sync();

        workGroup.shutdownGracefully();
        System.out.println("server shutdown.");
    }

}
