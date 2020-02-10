package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import lombok.Data;

@Data
public class QuoteDataCarrier {

    private String command;

    private Object quoteData;
//    private ChannelGroup channelGroup;
}
