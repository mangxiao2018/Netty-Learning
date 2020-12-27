package com.mangxiao.netty.samples.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:sample of FileChannel
 * @author:mangxiao2018@126.com
 * @date:2020-12-27
 */
public class FileChannelTestCase {

    /**
     * 对FileChannel进行放入和写取操作
     * @throws Exception
     */
    public static void putAndGet() throws Exception {
        String data = "Hello,World.";
        // 创建一个输出流->channel
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\2.txt");
        //通过 fileOutputStream 获取 对应的 FileChannel
        //这个 fileChannel 真实 类型是  FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将data放入byteBuffer中
        byteBuffer.put(data.getBytes());
        // 对byteBuffer 进行flip
        byteBuffer.flip();
        // 将byteBuffer 数据写入到 fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
