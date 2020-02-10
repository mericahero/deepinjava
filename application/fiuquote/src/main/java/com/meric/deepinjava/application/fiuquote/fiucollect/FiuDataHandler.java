package com.meric.deepinjava.application.fiuquote.fiucollect;

import com.meric.deepinjava.application.fiuquote.fiucollect.disruptor.FiuDataRingBufferWorker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class FiuDataHandler extends ChannelInboundHandlerAdapter {

    private FiuClient fiuClient;

    public FiuDataHandler(FiuClient client) {
        fiuClient = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        try{
            FiuDataRingBufferWorker.getInstance().invokeProduceData(p->{
                p.sendData((String)msg,ctx);
            });
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        fiuClient.start(true);
    }
}
