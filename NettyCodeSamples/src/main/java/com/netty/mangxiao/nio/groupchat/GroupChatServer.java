package com.netty.mangxiao.nio.groupchat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @description:基于JDK原生NIO的群聊系统-服务端
 * @author:mangxiao2018@126.com
 * @date:2021-02-08
 */
public class GroupChatServer {
    //属性定义
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;
    //初始化
    public GroupChatServer(){
        try {
            //得到选择器
            selector = Selector.open();
            //ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞模式
            listenChannel.configureBlocking(false);
            //将listenChannel注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //监听
    public void listen(){
        System.out.println("监听线程:" + Thread.currentThread().getName());
        try {
            while (true){
                int count = selector.select();
                if (count > 0){ //有事件处理
                    //遍历得到selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        //取出selectkey
                        SelectionKey key = iterator.next();
                        //监听到accept
                        if (key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            //将sc注册到selector
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + "上线");
                        }
                        if (key.isReadable()){ //通道发送read事件，即通道是可读的状态
                            //处理读
                            readData(key);
                        }
                        //当前的key删除，防止重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待...");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            // todo
        }
    }
    //读取客户端消息
    private void readData(SelectionKey key){
        //取到关联的channel
        SocketChannel channel = null;
        try {
            //得到 channel
            channel = (SocketChannel) key.channel();
            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count的值做处理
            if (count > 0){
                //把缓存区的数据转成字符串
                String msg = new String(buffer.array());
                //输出该消息
                System.out.println("from 客户端:" + msg);
                //向其它的客户端转发消息(去掉自己)
                sendInfoToOtherClients(msg, channel);
            }
        } catch (Exception e){
            try {
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.cancel();
                //关闭通道
                channel.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
    }
    //转发消息给其它客户端(通道)
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws Exception {
        System.out.println("服务器转发消息中....");
        System.out.println("服务器转发数据给客户端线程：" + Thread.currentThread().getName());
        //遍历 所有注册到selector上的SocketChannel，并排除self
        for (SelectionKey key: selector.keys()){
            //通过key，取出对应的SocketChannel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != self){
                //转型
                SocketChannel dest = (SocketChannel) targetChannel;
                //将msg存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                //将buffer的数据写入通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args){
        //创建服务器对象
        GroupChatServer groupChatServer =  new GroupChatServer();
        groupChatServer.listen();
    }
}
