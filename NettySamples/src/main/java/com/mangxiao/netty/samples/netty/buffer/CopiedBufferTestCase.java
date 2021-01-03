package com.mangxiao.netty.samples.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;

/**
 * @description:Unpooled.copiedBuffer
 * @author:mangxiao2018@126.com
 * @date:2021-1-3
 */
public class CopiedBufferTestCase {

    public static void main(String[] args){
        ByteBuf byteBuf = Unpooled.copiedBuffer("Hello world", Charset.forName("utf-8"));

        if (byteBuf.hasArray()){
            byte[] content = byteBuf.array();

            //将 content 转成字符串
            System.out.println(new String(content, Charset.forName("utf-8")));

            System.out.println("byteBuf=" + byteBuf);

            System.out.println(byteBuf.arrayOffset()); // 0
            System.out.println(byteBuf.readerIndex()); // 0
            System.out.println(byteBuf.writerIndex()); // 12
            System.out.println(byteBuf.capacity()); // 36

            System.out.println(byteBuf.getByte(0));
            // 可读的字节数
            int len = byteBuf.readableBytes();
            System.out.println("len=" + len);
            for (int i=0; i< byteBuf.capacity(); i++){
                System.out.println((char) byteBuf.getByte(i));
            }
            // 按某个范围读取
            System.out.println(byteBuf.getCharSequence(0, 4, Charset.forName("utf-8"));
            System.out.println(byteBuf.getCharSequence(4, 6, Charset.forName("utf-8"));

        }
    }
}
