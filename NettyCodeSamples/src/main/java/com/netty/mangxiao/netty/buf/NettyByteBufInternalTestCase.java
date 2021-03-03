package com.netty.mangxiao.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @description:ByteBuf内部原理练习
 * @author:mangxiao2018@126.com
 * @date:2021-03-03
 */
public class NettyByteBufInternalTestCase {

    public static void main(String[] args) throws Exception{
        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", Charset.forName("utf-8"));
        //使用相关方法
        if (byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            //将content转成字符串
            System.out.println(new String(content,Charset.forName("utf-8")));
            System.out.println("byteBuf=" + byteBuf);
            System.out.println(byteBuf.arrayOffset()); //0
            System.out.println(byteBuf.readerIndex()); //0
            System.out.println(byteBuf.writerIndex()); //12
            System.out.println(byteBuf.capacity()); //36

            System.out.println(byteBuf.getByte(0)); // 104

            int len = byteBuf.readableBytes(); //可读取的字节数 12
            System.out.println("len=" + len);
            //使用for取出各个字节
            for (int i = 0; i < len; i++){
                System.out.println((char) byteBuf.getByte(i));
            }
            //按照某个范围读取
            System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("utf-8")));

            System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("utf-8")));
        }
    }
}
