package com.mangxiao.netty.samples.nio;

import io.netty.util.NettyRuntime;

/**
 * 测试本机CPU核数
 */
public class Test {
    /**
     * 本机8核
     * @param args
     */
    public static void main(String[] args){
        System.out.println(NettyRuntime.availableProcessors());
    }
}
