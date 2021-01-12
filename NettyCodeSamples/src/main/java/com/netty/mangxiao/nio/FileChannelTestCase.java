package com.netty.mangxiao.nio;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:String->ByteBuffer->FileChannel->FileOutputStream->txt
 * @author:mangxiao2018@126.com
 * @date:2021-1-12
 */
public class FileChannelTestCase {
    public static void main(String[] args) throws Exception {
        String content = "你好，helloworld!";
        File file = new File("d:\\1.txt");
        FileOutputStream out = new FileOutputStream(file);
        FileChannel channel = out.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(content.getBytes());

        // 反转
        buffer.flip();

        // 写入
        channel.write(buffer);
        out.close();


    }
}
