package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.WorkHandler;
import com.meric.deepinjava.application.fiuquote.fiucollect.codec.DecodeClassFactory;
import com.meric.deepinjava.application.fiuquote.quoteserver.QuoteClientSubscriber;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.ArrayUtils;

import java.util.concurrent.atomic.AtomicLong;

public class FiuDataConsumer implements WorkHandler<FiuDataCarrier> {


    @Override
    public void onEvent(FiuDataCarrier fiuDataCarrier) throws Exception {
        val data = (String)fiuDataCarrier.getProtocolData();
        val protocolPrefix = "{\"protocol\":\"";
        val dataNext = data.substring(protocolPrefix.length());
        val protocol = dataNext.substring(0,dataNext.indexOf("\""));
        val clazz = DecodeClassFactory.DEFAULT.getClass(protocol);
        val protocolObject = JSON.parseObject(data,clazz);
        val protocolJsonObject = JSON.parseObject(data);
        if(true || ArrayUtils.contains(new String[] {"order","trade"},protocol)){
            QuoteDataRingBufferWorker.getInstance().invokeProduceData(p->{
                p.sendData(protocol,protocolObject);
            });
        }
        fiuDataCarrier.reset();




    }
}
