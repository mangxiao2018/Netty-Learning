package com.mangxiao.netty.samples.nio.chat;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer(){
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen(){
        System.out.println("监听线程=" + Thread.currentThread().getName());

        try {
            while (true){
                int count = selector.select();
                if (count > 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + " 上线了。");
                        }
                        if (key.isReadable()){
                            readData(key);
                        }
                        iterator.remove();
                    }
                }else{
                    System.out.println("等待....");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readData(SelectionKey key){
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count > 0){
                String msg = new String(buffer.array());
                System.out.println("from 客户端" + msg);
                sendInfoToOtherClient(msg, channel);
            }
        }catch (Exception e){
            try{
                System.out.println(channel.getRemoteAddress() + " 离线了...");
                key.cancel();
                channel.close();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void sendInfoToOtherClient(String msg, SocketChannel self) throws Exception{
        System.out.println("服务器转发消息中...");
        System.out.println("服务器转发数据给客户端线程: " + Thread.currentThread().getName());
        for (SelectionKey key : selector.keys()){
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != self){
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }


    public static  void main(String[] args){
        //创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}

class MyHandler {
    public void readData(){

    }

    public void sendInfoToOtherClient(){

    }

}