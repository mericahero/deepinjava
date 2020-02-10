package com.meric.deepinjava.disruptornetty.client;

import com.meric.deepinjava.disruptornetty.common.disruptor.RingBufferWorkPoolFactory;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.val;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client channel active");

        int i=0;
        TranslateData data = new TranslateData();
        data.setId(i);
        data.setName("请求名称 " + i);
        data.setMessage("请求内容 " + i);

        ctx.channel().writeAndFlush(data);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        try{
//            val data = (TranslateData)msg;
//            System.out.println("[Client] received "+ data);
//
//        }finally {
//            // 用完了缓存要进行释放
//            ReferenceCountUtil.release(msg);
//        }
        RingBufferWorkPoolFactory.getInstance().producerInvoke(p->p.sendData((TranslateData)msg,ctx));

    }
}
