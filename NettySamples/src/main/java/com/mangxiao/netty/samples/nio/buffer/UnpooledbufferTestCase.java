package com.mangxiao.netty.samples.nio.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @description:sample of Bytebuf of netty
 * @author:mangxiao2018@126.com
 * @date:2020-12-24
 */
public class UnpooledbufferTestCase {

    /**
     * 创建一个ByteBuf
     * 说明
     * 1.创建 对象，该对象包含一个数组arr , 是一个byte[10]
     * 2. 在netty 的buffer中，不需要使用flip 进行反转
     *    底层维护了 readerindex 和 writerIndex
     * 3. 通过 readerindex 和  writerIndex 和  capacity， 将buffer分成三个区域
     *     0---readerindex 已经读取的区域
     *     readerindex---writerIndex ， 可读的区域
     *     writerIndex -- capacity, 可写的区域
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{

        ByteBuf buffer = Unpooled.buffer(10);

        //写入
        for (int i = 0; i < 10; i++){
            buffer.writeByte(i);
        }

        System.out.println("buffer大小:" + buffer.capacity());

        //读取
        for (int i = 0; i < buffer.capacity(); i++){
            System.out.println(buffer.readByte());
        }

        System.out.println("ByteBuf数据读取完毕。");
    }
}
