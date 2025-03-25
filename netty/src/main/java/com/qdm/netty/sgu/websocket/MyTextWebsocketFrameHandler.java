package com.qdm.netty.sgu.websocket;

import com.sun.xml.internal.ws.util.StringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.internal.StringUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MyTextWebsocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static Map<String, Channel> sessionMap = new HashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx,
                                TextWebSocketFrame msg) throws Exception {

        System.out.println("server receive msg:" + msg.text());
        sendMsg2AllServer(ctx.channel().id().asLongText(), msg.text());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String sessionId = ctx.channel().id().asLongText();
        System.out.println("handlerAdded被调用： " + sessionId );
        sessionMap.put(sessionId, ctx.channel());
        System.out.println("当前客户端数量： " + sessionMap.size() );
        ctx.channel().writeAndFlush( new TextWebSocketFrame("hello client"));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved被调用： " + ctx.channel().id().asLongText() );
        sessionMap.remove(ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println(" exceptionCaught:" + ctx.channel().id().asLongText());
        sessionMap.remove(ctx.channel().id().asLongText());
        ctx.close();
    }

    private void sendMsg2AllServer(String senderSessionId, String msg) {

        sessionMap.entrySet().stream().forEach(e->{
            Channel channel = e.getValue();

            if (channel == null) {
                sessionMap.remove(e.getKey());
            } else {
                String longId = channel.id().asLongText();
                if (!Objects.equals(longId, senderSessionId)) {
                    TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(msg);
                    e.getValue().writeAndFlush(textWebSocketFrame);
                }
            }
        });
    }

}
