package com.meric.deepinjava.disruptornetty.server;

import com.meric.deepinjava.disruptornetty.common.disruptor.MessageConsumer;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateData;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateDataEvent;
import lombok.val;

public class ServerMessageConsumer extends MessageConsumer {

    public ServerMessageConsumer(String cid){
        super(cid);
    }

    @Override
    public void onEvent(TranslateDataEvent event) throws Exception {
        val data = event.getData();
        val ctx = event.getContext();
        System.out.printf("[Server %s] receive data: %s\n",getConsumerId(),data);

        TranslateData res = new TranslateData();
        res.setId(data.getId());
        res.setName(data.getName());
        res.setMessage(data.getMessage());

        ctx.writeAndFlush(res);

    }
}
