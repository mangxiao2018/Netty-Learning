package com.netty.mangxiao.netty.heartbeat;

import com.netty.mangxiao.nio.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @description:心跳服务端handler
 * @author:mangxiao2018@126.com
 * @date:2021-3-24
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     *
     * @param ctx 上下文
     * @param evt 事件
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "==超时时间" + eventType);
            System.out.println("服务器做相应处理...");
        }

    }
}
