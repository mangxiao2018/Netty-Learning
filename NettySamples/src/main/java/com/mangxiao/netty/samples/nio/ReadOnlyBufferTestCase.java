package com.mangxiao.netty.samples.nio;

import java.nio.ByteBuffer;

/**
 * @description: sample of ReadOnlyBuffer
 * @author:mangxiao2018@126.com
 * @date:2020-12-28
 */
public class ReadOnlyBufferTestCase {

    public static void main(String[] args){
        ByteBuffer byteBuffer = ByteBuffer.allocate(64);
        // 放入数据
        for (int i=1; i<byteBuffer.capacity(); i++){
            byteBuffer.put((byte) i);
        }
        // 读写转置
        byteBuffer.flip();
        // 转成只读buffer
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());
        // 读取
        while (byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.get());
        }
        // 制造异常
        readOnlyBuffer.put((byte) 100);
    }
}
