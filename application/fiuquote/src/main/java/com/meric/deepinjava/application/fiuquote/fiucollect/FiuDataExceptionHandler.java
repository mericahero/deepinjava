package com.meric.deepinjava.application.fiuquote.fiucollect;

import com.meric.deepinjava.application.fiuquote.fiucollect.disruptor.FiuDataRingBufferWorker;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;



public class FiuDataExceptionHandler extends ChannelInboundHandlerAdapter {

    private FiuClient fiuClient;

    public FiuDataExceptionHandler(FiuClient client) {
        fiuClient = client;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

        Channel channel = ctx.channel();
        if(channel.isActive()){
            ctx.close();
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        fiuClient.start(true);
    }
}
