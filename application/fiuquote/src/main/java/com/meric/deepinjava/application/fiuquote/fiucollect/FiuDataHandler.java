package com.meric.deepinjava.application.fiuquote.fiucollect;

import com.meric.deepinjava.application.fiuquote.fiucollect.disruptor.FiuDataRingBufferWorker;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.atomic.AtomicLong;

public class FiuDataHandler extends ChannelInboundHandlerAdapter {


    private static AtomicLong counter = new AtomicLong(0);
    private static Long lastCounter = 0L;

    private static final long w = 2000;
    static {
        new Thread(()->{
            while(true){
                try {
                    Thread.sleep(w);
                    System.out.println(w+" ms receive "+ (counter.get()-lastCounter));
                    lastCounter = counter.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private FiuClient fiuClient;

    public FiuDataHandler(FiuClient client) {
        fiuClient = client;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try{
            FiuDataRingBufferWorker.getInstance().invokeProduceData(p->{
                p.sendData((String)msg,ctx);
                counter.incrementAndGet();
                //System.out.println((String)msg);
            });
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }finally {
            ReferenceCountUtil.release(msg);
        }

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        fiuClient.start(true);
    }
}
