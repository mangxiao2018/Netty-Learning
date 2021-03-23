package com.netty.mangxiao.netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @description:群聊系统客户端
 * @author:mangxiao2018@126.com
 * @date:2021-3-23
 */
public class GroupChatClient {
    private final String host;
    private final int port;

    public GroupChatClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void run() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            pipeline.addLast(new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect(host,port).sync();
            Channel channel = channelFuture.channel();
            System.out.println("==" + channel.localAddress() + "===");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg + "\r\n");
            }
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        new GroupChatClient("127.0.0.1", 7000).run();
    }
}