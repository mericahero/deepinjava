package com.meric.deepinjava.application.fiuquote.fiucollect.disruptor;

import com.lmax.disruptor.WorkHandler;
import com.meric.deepinjava.application.fiuquote.fiucollect.protocol.OrderProtocol;
import com.meric.deepinjava.application.fiuquote.fiucollect.protocol.TradeProtocol;
import com.meric.deepinjava.application.fiuquote.quoteserver.QuoteClientSubscriber;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.val;
import lombok.var;

/**
 * 行情数据消费者，将数据发送到客户端
 */
public class QuoteDataConsumer implements WorkHandler<QuoteDataCarrier> {
    @Override
    public void onEvent(QuoteDataCarrier quoteDataCarrier) throws Exception {

        val quoteObject = quoteDataCarrier.getQuoteData();
        val command = quoteDataCarrier.getCommand();
        switch (command){
            case "trade":
                val tradeObject = (TradeProtocol) quoteObject;
                QuoteClientSubscriber.getInstance().fireStock(tradeObject.getCode(), g->{
                    var str =tradeObject.toString();
                    g.writeAndFlush(new TextWebSocketFrame(str));
                    str = null;
                });
                break;
            case "order":
                val orderObject = (OrderProtocol) quoteObject;
                QuoteClientSubscriber.getInstance().fireStock(orderObject.getCode(),g->{
                    var str =orderObject.toString();
                    g.writeAndFlush(new TextWebSocketFrame(str));
                    str = null;
                });
                break;
            default:
                break;
        }
        quoteDataCarrier.reset();

    }
}
