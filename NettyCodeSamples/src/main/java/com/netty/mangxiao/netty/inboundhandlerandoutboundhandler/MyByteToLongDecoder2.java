package com.netty.mangxiao.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description: decoder2
 * @author:mangxiao2018@126.com
 * @date:2021-4-14
 */
public class MyByteToLongDecoder2 extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2 被调用了");
        out.add(in.readLong());
    }
}
