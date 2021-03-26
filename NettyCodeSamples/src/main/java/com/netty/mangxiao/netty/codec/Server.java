package com.netty.mangxiao.netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

/**
 * @description:protobuf of google example
 * @author:mangxiao2018@126.com
 * @date:2021-3-26
 */
public class Server {
    public static void main(String[] args) throws Exception{
        //创建BossGroup 和 WorkerGroup
        //说明
        //1. 创建两个线程组 bossGroup 和 workerGroup
        //2. bossGroup 只是处理连接请求 , 真正的和客户端业务处理，会交给 workerGroup完成
        //3. 两个都是无限循环
        //4. bossGroup 和 workerGroup 含有的子线程(NioEventLoop)的个数
        //   默认实际 cpu核数 * 2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup);
            //使用NioSocketChannel 作为服务器的通道实现
            bootstrap.channel(NioServerSocketChannel.class);
            // 设置线程队列得到连接个数
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            //设置保持活动连接状态
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            //创建一个通道初始化对象(匿名对象)
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel channel) throws Exception {
                    ChannelPipeline pipeline = channel.pipeline();
                    //在pipeline加入ProtoBufDecoder
                    //指定对哪种对象进行解码
                    pipeline.addLast("decoder", new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                    pipeline.addLast(new ServerHandler());
                }
            });
            System.out.println("---==- 服务器 is ready...");

            ChannelFuture future = bootstrap.bind(6668).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (future.isSuccess()){
                        System.out.println("监听端口 6668 成功");
                    }else{
                        System.out.println("监听端口 6668 失败");
                    }
                }
            });
            future.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
