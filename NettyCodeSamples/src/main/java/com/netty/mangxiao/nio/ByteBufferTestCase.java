package com.netty.mangxiao.nio;

import java.nio.ByteBuffer;

/**
 * @description:ByteBuffer各类型数据的写入与读取
 * @author:mangxiao2018@126.com
 * @date:2021-1-14
 */
public class ByteBufferTestCase {

    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(9);
        buffer.putLong(100);
        buffer.putChar('青');
        buffer.putShort((short) 3);

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
//        System.out.println(buffer.getLong()); //全报错 BufferUnderflowException
    }
}
