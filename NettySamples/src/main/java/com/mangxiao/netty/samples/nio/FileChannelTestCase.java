package com.mangxiao.netty.samples.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description:sample of FileChannel
 * @author:mangxiao2018@126.com
 * @date:2020-12-27
 */
public class FileChannelTestCase {

    public static void main(String[] args) throws Exception {
        // 对FileChannel进行放入和写取操作
        putAndGet();
        // 通过FileInputStream,FileChannel进行数据读取
        read();
        // 通过FileInputStream,FileOutStream,FileChannel进行数据的读取写入
        inAndOutStream();
    }
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

    /**
     * 通过FileInputStream,FileChannel进行数据读取
     * @throws Exception
     */
    public static void read() throws Exception {
        // 创建文件输入流
        File file = new File("d:\\03.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        // 通过fileInputStream 获取对应的FileChannel -> 实际类型  FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();
        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        // 将fileChannel通道的数据读入到Buffer
        fileChannel.read(byteBuffer);
        // 将byteBuffer字节的数据转成String
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();
    }

    /**
     * 通过FileInputStream,FileOutStream,FileChannel进行数据的读取写入
     * @throws Exception
     */
    public static void inAndOutStream() throws Exception {
        FileInputStream fileInputStream = new FileInputStream("d:\\04.txt");
        FileChannel fileChannel_a = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("d:\\05.txt");
        FileChannel fileChannel_b = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);

        while(true){
            //这里有一个重要的操作，一定不要忘了
            /*
             public final Buffer clear() {
                position = 0;
                limit = capacity;
                mark = -1;
                return this;
            }
             */
            byteBuffer.clear();//清空buffer
            int read = fileChannel_a.read(byteBuffer);
            System.out.println("read=" + read);
            if (read == -1){
                break;
            }
            //将buffer 中的数据写入到 fileChannel_b -- 05.txt
            byteBuffer.flip();
            fileChannel_b.write(byteBuffer);
        }
        //关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }

    /**
     * 通过FileInputStream,FileOutputStream,channel.transferFrom进行输入输出流转换
     * @throws Exception
     */
    public static void transfer() throws Exception {
        // 创建相关流
        FileInputStream fileInputStream = new FileInputStream("d:\\06.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\07.txt");
        // 获取各个流对应的filechannel
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel targetChannel = fileOutputStream.getChannel();

        targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        // 关闭channel
        sourceChannel.close();
        targetChannel.close();
        // 关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
