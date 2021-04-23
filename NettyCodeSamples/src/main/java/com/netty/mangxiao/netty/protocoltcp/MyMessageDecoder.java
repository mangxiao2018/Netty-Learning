package com.netty.mangxiao.netty.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;
/**
 * @description: 粘包拆包-解码器
 * @author:mangxiao2018@126.com
 * @date:2021-4-23
 */
public class MyMessageDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyMessageDecoder decode 被调用");
        //需要将得到二进制字节码->MessageProtocol数据包(对象)
        int length = in.readInt();

        byte[] content = new byte[length];
        in.readBytes(content);
        //封装成MessageProtocol对象，放入out,传递下一个handler业务处理
        MessageProtocol message = new MessageProtocol();
        message.setLen(length);
        message.setContent(content);
        out.add(message);
    }
}
