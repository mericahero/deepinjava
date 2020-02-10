package com.meric.deepinjava.disruptornetty.server;

import com.meric.deepinjava.disruptornetty.common.disruptor.RingBufferWorkPoolFactory;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        TranslateData data = (TranslateData)msg;
//        System.out.println("[Server] received: "+data);
//
//        TranslateData res = new TranslateData();
//        res.setId(data.getId());
//        res.setName(data.getName());
//        res.setMessage(data.getMessage());
//
//        ctx.writeAndFlush(res);

        RingBufferWorkPoolFactory.getInstance().producerInvoke(p->p.sendData((TranslateData)msg,ctx));

    }
}
