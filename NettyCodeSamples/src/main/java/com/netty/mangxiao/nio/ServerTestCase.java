package com.netty.mangxiao.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author:mangxiao2018@126.com
 * @date:2021-1-27
 */
public class ServerTestCase {

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后的selectKey 数量=" + selector.keys().size());

        while (true){
            if (selector.select(1000) == 0){
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            Set<SelectionKey> selectKeys = selector.selectedKeys();
            System.out.println("SelectKeys数量= " + selectKeys.size());

            Iterator<SelectionKey> keyInterator = selectKeys.iterator();
            while (keyInterator.hasNext()){
                SelectionKey key = keyInterator.next();
                if (key.isAcceptable()){
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成了一个 socketChannel" + socketChannel);
                }
                if(key.isReadable()){

                }
            }
        }
        }
    }
}
