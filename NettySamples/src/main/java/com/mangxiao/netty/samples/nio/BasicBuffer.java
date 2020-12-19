package com.mangxiao.netty.samples.nio;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args){
        // 创建一个buffer,大小5,可以分配5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        // 放:向buffer中存放数据
        for (int i = 0;i < intBuffer.capacity(); i++){
            intBuffer.put(i * 2);
        }
        // 存取转置：将buffer转换，读写切换(非常重要!!!)
        intBuffer.flip();
        // 取
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
