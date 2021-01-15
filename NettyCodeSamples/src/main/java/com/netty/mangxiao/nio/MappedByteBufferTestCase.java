package com.netty.mangxiao.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: MappedByteBuffer可以实现内存修改，代码上无需直接操作文件
 * @author:mangxiao2018@126.com
 * @date:2021-1-15
 */
public class MappedByteBufferTestCase {
    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("d:\\1.txt","rw");
        FileChannel channel = file.getChannel();
        /**
         * 参数1: FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2： 0 ： 可以直接修改的起始位置
         * 参数3:  5: 是映射到内存的大小(不是索引位置) ,即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        buffer.put(0,(byte) 'H');
        buffer.put(3,(byte) '9');
        // throws IndexOutOfBoundsException
//        buffer.put(5,(byte) 'W');

        file.close();
        System.out.println("修改完成...");
    }
}
