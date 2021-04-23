package com.netty.mangxiao.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * @description: 粘包拆包-处理器
 * @author:mangxiao2018@126.com
 * @date:2021-4-23
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        int len = msg.getLen();
        byte[] content = msg.getContent();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("服务器接收到如下信息:");
        System.out.println("长度=" + len);
        System.out.println("内容=" + new String(content, Charset.forName("utf-8")));
        System.out.println("服务器接收到消息包数量=" + (++this.count));


        //回复消息
        String responseContent = UUID.randomUUID().toString();
        int responseLen = responseContent.getBytes("utf-8").length;
        byte[] responseContent2 = responseContent.getBytes("utf-8");
        //构建一个协议包
        MessageProtocol p = new MessageProtocol();
        p.setLen(responseLen);
        p.setContent(responseContent2);

        ctx.writeAndFlush(p);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
