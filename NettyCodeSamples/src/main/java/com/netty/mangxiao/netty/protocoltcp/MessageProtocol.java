package com.netty.mangxiao.netty.protocoltcp;
/**
 * @description: 协议包
 * @author:mangxiao2018@126.com
 * @date:2021-4-23
 */
public class MessageProtocol {
    private int len;
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
