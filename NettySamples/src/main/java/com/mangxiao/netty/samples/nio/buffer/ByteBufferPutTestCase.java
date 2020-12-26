package com.mangxiao.netty.samples.nio.buffer;

import java.nio.ByteBuffer;

/**
 * @description: sample of ByteBuffer`s put and get.
 * @author:mangxiao2018@126.com
 * @date:2020-12-26
 */
public class ByteBufferPutTestCase {

    public static void main(String[] args){
        // 创建一个buffer 并分配64容量大小
        ByteBuffer  buffer = ByteBuffer.allocate(64);
        // 放入 - 类型化方式放入数据
        buffer.putInt(100);
        buffer.putLong(4);
        buffer.putChar('你');
        buffer.putShort((short)9);
        // 存取翻转
        buffer.flip();

        System.out.println();
        // 取出
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());

    }
}
