package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class FiuDataCarrier {
    String protocolData;
    ChannelHandlerContext ctx;

    public void reset(){
        protocolData=null;
        ctx=null;
    }
}
