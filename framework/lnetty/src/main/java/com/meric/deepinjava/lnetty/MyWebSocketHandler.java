package com.meric.deepinjava.lnetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import javax.print.attribute.standard.RequestingUserName;
import javax.sound.midi.Soundbank;
import javax.xml.soap.Text;
import java.util.Date;

public class MyWebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker serverHandshaker;
    private static final String WEB_SOCKET_URL="ws://localhost:8888";
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        // 握手
        if(o instanceof FullHttpRequest){
            handleHttpRequest(channelHandlerContext,(FullHttpRequest)o);
        }else if(o instanceof WebSocketFrame){
            handleWebSocketFrame(channelHandlerContext,(WebSocketFrame)o);
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx,WebSocketFrame frame){
        if(frame instanceof CloseWebSocketFrame){
            serverHandshaker.close(ctx.channel(),(CloseWebSocketFrame)frame);
            return;
        }
        if(frame instanceof PingWebSocketFrame){
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if(!(frame instanceof TextWebSocketFrame)){
            throw new RuntimeException("不支持的消息");
        }

        String requestMsg = ((TextWebSocketFrame)frame).text();
        System.out.println("收到的消息 ==> "+ requestMsg);
        TextWebSocketFrame tsf = new TextWebSocketFrame(new Date().toString() + ": "+ctx.channel().id()+": "+requestMsg);
        // 群发
        NettyConfig.CG.writeAndFlush(tsf);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx,FullHttpRequest req){
        if(!req.decoderResult().isSuccess() || !"websocket".equals(req.headers().get("Upgrade"))){
            sendHttpResponse(ctx,req,new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory factory=new WebSocketServerHandshakerFactory(WEB_SOCKET_URL,null,false);
        serverHandshaker = factory.newHandshaker(req);
        if(serverHandshaker == null){
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        }else{
            serverHandshaker.handshake(ctx.channel(),req);
        }
    }

    private void sendHttpResponse(
            ChannelHandlerContext ctx,
            FullHttpRequest req,
            DefaultFullHttpResponse res
    ){
        if(res.status()!= HttpResponseStatus.OK){
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        ChannelFuture f=ctx.channel().writeAndFlush(res);
        if(res.status()!=HttpResponseStatus.OK){
//            ctx.channel().close();
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        NettyConfig.CG.add(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        NettyConfig.CG.remove(ctx.channel());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}


