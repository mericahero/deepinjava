package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.meric.deepinjava.application.fiuquote.fiucollect.protocol.OrderProtocol;
import com.meric.deepinjava.application.fiuquote.fiucollect.protocol.TradeProtocol;
import com.meric.deepinjava.application.fiuquote.quoteserver.QuoteClientSubscriber;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.val;

public class QuoteDataConsumer implements WorkHandler<QuoteDataCarrier> {
    @Override
    public void onEvent(QuoteDataCarrier quoteDataCarrier) throws Exception {

        val quoteObject = quoteDataCarrier.getQuoteData();
        val command = quoteDataCarrier.getCommand();
        switch (command){
            case "trade":
                val tradeObject = (TradeProtocol) quoteObject;
                QuoteClientSubscriber.getInstance().fireStock(tradeObject.getCode(), g->{
                    g.writeAndFlush(new TextWebSocketFrame(tradeObject.toString()));
                });
                break;
            case "order":
                val orderObject = (OrderProtocol) quoteObject;
                QuoteClientSubscriber.getInstance().fireStock(orderObject.getCode(),g->{
                    g.writeAndFlush(new TextWebSocketFrame(orderObject.toString()));
                });
                break;
            default:
                break;
        }


    }
}
