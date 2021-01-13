package com.netty.mangxiao.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:FileChannel、FileOutputStream、FileInputStream、ByteBuffer
 * @author:mangxiao2018@126.com
 * @date:2021-1-12
 */
public class FileChannelTestCase {
    public static void main(String[] args) throws Exception {
        out();
        in();
        inAndOut();
    }

    /**
     * String->ByteBuffer->FileChannel->FileOutputStream->txt
     * @throws Exception
     */
    public static void out() throws Exception {
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

    /**
     * TXT->FileInputStream->FileChannel->ByteBuffer->System.out
     * @throws Exception
     */
    public static void in() throws Exception {
        File file = new File("d:\\1.txt");
        FileInputStream in = new FileInputStream(file);
        FileChannel channel = in.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate((int)file.length());
        channel.read(buffer);

        System.out.println(new String(buffer.array()));
        in.close();
    }

    /**
     * 1.TXT->FileInputStream->FileChannel(IN)->ByteBuffer->FileChannel(OUT)->FileOutputStream->2.TXT
     * @throws Exception
     */
    public static void inAndOut() throws Exception {
        FileInputStream in = new FileInputStream("d:\\1.txt");
        FileOutputStream out = new FileOutputStream("d:\\2.txt");

        FileChannel inChannel = in.getChannel();
        FileChannel outChannel = out.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(2048);

        while (true){
            buffer.clear();
            int read = inChannel.read(buffer);
            System.out.println("read=" + read);
            if (read == -1){
                break;
            }
            buffer.flip();
            outChannel.write(buffer);
        }

        out.close();
        in.close();
    }
}
