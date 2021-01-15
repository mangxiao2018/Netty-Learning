package com.netty.mangxiao.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @description:
 * 1、Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入  [分散]
 * 2、Gathering: 从buffer读取数据时，可以采用buffer数组，依次读
 * @author:mangxiao2018@126.com
 * @date:2021-1-15
 */
public class ScatteringAndGatheringTestCase {

    public static void main(String[] args) throws Exception {
        // 使用 ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel channel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(7000);

        channel.socket().bind(address);
        // 创建buffer数组
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(5);
        buffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = SocketChannel.open();
        // 假定从客户端接收8个字节
        int messageLength = 8;
        while (true){
            int byteRead = 0;
            if (byteRead < messageLength){
                long l = socketChannel.read(buffers);
                // 累计读取的字节数
                byteRead += 1;
                System.out.println("byteRead=" + byteRead);
                // 使用流打印, 看看当前的这个buffer的position 和 limit
                Arrays.asList(buffers).stream().map(buffer -> "position="
                        + buffer.position()
                        + ",limit=" +buffer.limit()).forEach(System.out::println);
            }
            // 将所有的buffer进行flip
            Arrays.asList(buffers).forEach(buffer -> buffer.flip());
            // 将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength){
                long l = socketChannel.write(buffers);
                byteWrite += 1;
            }
            // 将所有的buffer 进行clear
            Arrays.asList(buffers).forEach(buffer -> {
                buffer.clear();
            });
            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite
                    + ", messageLength=" + messageLength);
        }
    }
}
