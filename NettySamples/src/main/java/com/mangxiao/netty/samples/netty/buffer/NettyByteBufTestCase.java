package com.mangxiao.netty.samples.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @decription:ByteBuf读写
 * @author:mangxiao2018@126.com
 * @date:2021-1-3
 */
public class NettyByteBufTestCase {

    public static void main(String[] args){
        //创建一个ByteBuf
        //说明
        //1. 创建 对象，该对象包含一个数组arr , 是一个byte[10]
        //2. 在netty 的buffer中，不需要使用flip 进行反转
        //   底层维护了 readerindex 和 writerIndex
        //3. 通过 readerindex 和  writerIndex 和  capacity， 将buffer分成三个区域
        // 0---readerindex 已经读取的区域
        // readerindex---writerIndex ， 可读的区域
        // writerIndex -- capacity, 可写的区域
        ByteBuf byteBuf = Unpooled.buffer(10);
        for (int i=0; i<byteBuf.capacity(); i++){
            byteBuf.writeByte(i);
        }
        System.out.println("capacity=" + byteBuf.capacity());
        for (int i=0; i<byteBuf.capacity(); i++){
            System.out.println(byteBuf.readByte());
        }
        System.out.println("执行完毕");
    }
}
