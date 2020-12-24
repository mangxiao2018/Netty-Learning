package com.mangxiao.netty.samples.nio.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @description:sample of Unpooled.copiedBuffer
 * @author:mangxiao2018@126.com
 * @date:2020-12-24
 */
public class UnpooledCopiedBufferTestCase {
    public static void main(String[] args){
        //创建ByteBuf
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world", Charset.forName("utf-8"));
        //使用相关的方法
        if (byteBuf.hasArray()){
            byte[] content = byteBuf.array();
            // 将byte[]转成字符串
            System.out.println(new String(content,Charset.forName("utf-8")));

            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());
            System.out.println(byteBuf.capacity());

            System.out.println(byteBuf.getByte(0));

            int len = byteBuf.readableBytes();
            System.out.println("len=" + len);
            // 取出各个字节
            for (int i=0; i < len; i++){
                System.out.println((char)byteBuf.getByte(i));
            }
            //按照某个范围读取
            System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("utf-8")));
            System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("utf-8")));
        }
    }
}
