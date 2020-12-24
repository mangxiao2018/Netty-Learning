package com.mangxiao.netty.samples.nio.buffer;

import java.nio.IntBuffer;

/**
 * @description:sample of IntBuffer
 * @author:mangxiao2018@126.com
 * @date:2020-12-24
 */
public class JavaNIObufferTestCase {
    public static void main(String[] args){
        //创建一个buffer，分配大小为5，可以存储5个int数值
        IntBuffer intBuffer = IntBuffer.allocate(10);
        //写
        for (int i = 0; i < intBuffer.capacity(); i++){
            intBuffer.put(i);
        }
        //存取转置：将buffer转换,读写切换(非常重要!!!)
        intBuffer.flip();
        //取
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
