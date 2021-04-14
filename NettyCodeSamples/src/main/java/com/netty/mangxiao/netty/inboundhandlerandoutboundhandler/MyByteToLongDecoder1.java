package com.netty.mangxiao.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
/**
 * @description: decoder1
 * @author:mangxiao2018@126.com
 * @date:2021-4-14
 */
public class MyByteToLongDecoder1 extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder1 被调用了");
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
