package com.meric.deepinjava.disruptornetty.common.entity;

import io.netty.channel.ChannelHandlerContext;
import lombok.Data;

@Data
public class TranslateDataEvent {
    private TranslateData data;
    private ChannelHandlerContext context;
}
