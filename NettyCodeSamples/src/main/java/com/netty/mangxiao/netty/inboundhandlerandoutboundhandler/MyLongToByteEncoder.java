package com.netty.mangxiao.netty.inboundhandlerandoutboundhandler;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
/**
 * @description: encoder
 * @author:mangxiao2018@126.com
 * @date:2021-4-14
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyLongToByteEncoder 被调用");
        System.out.println("msg=" + msg);
        out.writeLong(msg);
    }
}
