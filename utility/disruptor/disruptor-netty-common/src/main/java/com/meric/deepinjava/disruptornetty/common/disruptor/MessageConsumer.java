package com.meric.deepinjava.disruptornetty.common.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.meric.deepinjava.disruptornetty.common.entity.TranslateDataEvent;
import lombok.Data;

@Data
public abstract class MessageConsumer implements WorkHandler<TranslateDataEvent> {

    protected String consumerId;

    public MessageConsumer(String cid){
        this.consumerId = cid;
    }

}
