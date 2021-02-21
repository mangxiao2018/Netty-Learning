package com.netty.mangxiao.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @description:client of netty
 * @author:mangxiao2018@126.com
 * @date:2021-02-21
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {
        //客户端需要一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建客户端启动对象
            //注意客户端使用的不是 ServerBootstrap 而是 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            //相关参数
            bootstrap.group(group) //设置线程组
                    .channel(NioSocketChannel.class) //设置客户端通道的实现类(反射)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new NettyClientHandler());//加入自己编写的处理类
                        }
                    });
            System.out.println("Client ok ...");
            //启动客户端去连接服务器端
            //关于 ChannelFuture 要分析，涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
            //关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }

    }
}
