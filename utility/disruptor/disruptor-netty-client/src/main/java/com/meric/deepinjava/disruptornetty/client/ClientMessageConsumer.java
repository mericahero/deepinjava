package com.meric.deepinjava.disruptornetty.client;

import com.lmax.disruptor.EventTranslator;
import com.meric.deepinjava.disruptornetty.common.disruptor.MessageConsumer;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateData;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateDataEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.val;

public class ClientMessageConsumer extends MessageConsumer {
    public ClientMessageConsumer(String cid){
        super(cid);
    }
    @Override
    public void onEvent(TranslateDataEvent event) throws Exception {
        val data = event.getData();
        val ctx = event.getContext();

        System.out.printf("[Consumer %s] receive data: %s\n",getConsumerId(),data);

        //Thread.yield();
        TranslateData res = new TranslateData();
        res.setId(data.getId());
        res.setName(data.getName());
        res.setMessage(data.getMessage());

        ctx.writeAndFlush(res);
        //        try{
//            System.out.printf("[Consumer %s] receive data: %s\n",getConsumerId(),data);
//        }finally {
//            ReferenceCountUtil.release(data);
//        }
    }
}
