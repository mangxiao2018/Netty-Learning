package com.netty.mangxiao.nio;

import java.nio.ByteBuffer;

/**
 * @description:ReadOnlyBuffer 实例
 * @author:mangxiao2018@126.com
 * @date:2021-1-14
 */
public class ReadOnlyBufferTestCase {

    public static void main(String[] args){
        // 创建一个大小为64的buffer并写入数据
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i< 64; i++){
            buffer.put((byte) i);
        }
        // 反转
        buffer.flip();
        // 得到一个只读 buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        // throws ReadOnlyBufferException
        readOnlyBuffer.put((byte) 100);
    }
}
