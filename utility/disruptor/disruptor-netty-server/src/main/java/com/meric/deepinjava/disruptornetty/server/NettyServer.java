package com.meric.deepinjava.disruptornetty.server;

import com.meric.deepinjava.disruptornetty.common.util.MarshallingCoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.val;

public class NettyServer {
    public NettyServer() {
        // 1、创建两个工作线程组，一个用于接收网络请求，一个用于处理业务逻辑
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        try {

            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //表示缓存区动态调配（自适应）
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    //缓存区 池化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    // 接收数据进行回调处理，尽量不要在这个handler里处理逻辑代码
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast(
                                    MarshallingCoder.getEncoder(),
                                    MarshallingCoder.getDecoder(),
                                    new ServerHandler()
                            );

//                            ch.pipeline().addLast(
//                                    new ObjectEncoder(),
//                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
//                                    new ServerHandler()
//                            );
                        }
                    });
            val cf = bootstrap.bind("127.0.0.1",8765).sync();
            System.out.println("server starting ...");
            cf.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            System.out.println("server shutdown.");

        }

    }
}
