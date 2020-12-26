package com.mangxiao.netty.samples.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @description:sample of SocketChannel
 * @author:mangxiao2018@126.com
 * @date:2020-12-26
 */
public class NIOClientTestCase {

    public static void main(String[] args) throws Exception {
        // 创建一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 设置IP和端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作..");
            }
        }

        // 如果连接成功就发送数据...
        String data = "hello, world";
        // 把一个数组包装成buffer
        ByteBuffer byteBuffer = ByteBuffer.wrap(data.getBytes());
        // 发送数据，将buffer数据写入buffer
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}
