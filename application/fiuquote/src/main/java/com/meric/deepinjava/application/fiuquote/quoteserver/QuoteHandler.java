package com.meric.deepinjava.application.fiuquote.quoteserver;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.val;

public class QuoteHandler extends SimpleChannelInboundHandler<Object> {

    private String websocket_url;

    public QuoteHandler(String wsurl){
        websocket_url=wsurl;
    }

    private WebSocketServerHandshaker webSocketServerHandshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            handleHttpRequest(ctx,(FullHttpRequest)msg);
        }else if(msg instanceof WebSocketFrame){
            handleWebSocketFrame(ctx,(WebSocketFrame)msg);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if(msg instanceof CloseWebSocketFrame){
            webSocketServerHandshaker.close(ctx.channel(),(CloseWebSocketFrame)msg.retain());
        }else if(msg instanceof PingWebSocketFrame){
            ctx.channel().writeAndFlush(new PongWebSocketFrame(msg.content().retain()));
        }else if(msg instanceof TextWebSocketFrame){
            val text = ((TextWebSocketFrame) msg).text();
            QuoteClientSubscriber.getInstance().execCommand(text,ctx);
        }else{
            throw new UnsupportedOperationException(String.format(
                    "%s frame types not supported", msg.getClass().getName()));
        }
    }

    /**
     * 握手请求
     * @param ctx
     * @param request
     */
    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest request){
        if(!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))){
            sendHttpResponse(ctx,request,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory factory = new WebSocketServerHandshakerFactory(websocket_url,null,false);
        webSocketServerHandshaker = factory.newHandshaker(request);
        if(webSocketServerHandshaker==null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else{
            webSocketServerHandshaker.handshake(ctx.channel(),request);
        }
    }

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, DefaultFullHttpResponse response) {
        if(response.status()!=HttpResponseStatus.OK){
            val buffer = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buffer);
            buffer.release();
        }
        val cf = ctx.channel().writeAndFlush(response);
        cf.addListener(ChannelFutureListener.CLOSE);
    }
}
