package com.meric.deepinjava.lnetty;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyConfig {
    public static ChannelGroup CG = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
}
