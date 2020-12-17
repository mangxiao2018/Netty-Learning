package com.mangxiao.netty.samples.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description 编写一个Server与cmd的telnet进行通信
 * @author mangxiao2018@126.com
 * @date 2020-12-17
 */
public class BIOSamples {
    public static void main(String[] args) throws Exception{
        // 思路：
        // 1.创建一个线程池
        // 2.如果有客户端连接，创建一个线程与之通信
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建ServerSokect
        ServerSocket server = new ServerSocket(6666);
        System.out.println("服务器启动了");

        while (true){
            System.out.print("线程信息: id =" + Thread.currentThread().getId() + " 名字 = " + Thread.currentThread().getName());
            // 监听，等待客户端连接
            System.out.println("等待连接...");
            final Socket socket = server.accept();
            System.out.print("连接到一个客户端");
            // 新创建一个线程，与之通信
            newCachedThreadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }

    }

    /**
     * handler与客户端通信
     * @param socket
     */
    public static void handler(Socket socket){
        try{
            System.out.println("线程信息: id = " + Thread.currentThread().getId() + " name =" + Thread.currentThread().getName());
            byte[] bytes = new byte[2048];
            InputStream in = socket.getInputStream();
            // 循环读取客户端信息
            while (true){
                System.out.println("线程信息: id = " + Thread.currentThread().getId() + " name =" + Thread.currentThread().getName());

                System.out.print("read.....");
                int read = in.read(bytes);
                if (read != -1){
                    // 输出客户端发送的数据
                    System.out.println(new String(bytes, 0, read));
                }else{
                    break;
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally {
            System.out.println("关闭和client的连接");
            try{
                socket.close();
            } catch (Exception e){
                System.out.print(e.getMessage());
            }

        }
    }
}
