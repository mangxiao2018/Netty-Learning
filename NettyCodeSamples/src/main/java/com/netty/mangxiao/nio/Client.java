package com.netty.mangxiao.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @description:client of NIO
 * @author:mangxiao2018@126.com
 * @date:2021-1-28
 */
public class Client {
    public static void main(String[] args) throws Exception{
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器端的ip 和 端口
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(address)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作..");
            }
        }
        //...如果连接成功，就发送数据
        String content = "Hi, world!";
        //Wraps a byte array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
        //发送数据，将 buffer 数据写入 channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
