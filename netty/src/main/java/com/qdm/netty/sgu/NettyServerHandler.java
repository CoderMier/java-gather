package com.qdm.netty.sgu;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 1、自定义一个handler需要继承netty规定好的handlerAdapter
 * 2、这时自定义的handler才能称为一个handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {



    /**
     * 读取数据的事件，读取客户端发送的消息
     * @param ctx 上下文对象，含有管道pipeline(中包含很多的handler，业务逻辑处理), 通道channel（数据的读写）, 地址
     * @param msg 客户端发送的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx = " + ctx);
        //将msg转成一个ByteBuf
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("client send msg:" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("client addr: " + ctx.channel().remoteAddress());
    }


    /**
     * 数据读取完成
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //是write + flush 将数据写入到缓冲并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client~", CharsetUtil.UTF_8));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
