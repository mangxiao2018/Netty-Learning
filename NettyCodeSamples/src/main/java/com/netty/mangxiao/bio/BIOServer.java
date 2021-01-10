package com.netty.mangxiao.bio;

import javax.print.DocFlavor;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:使用普通ServerSocket创建一线程一连接的BIO通信实例
 * @author:mangxiao2018@126.com
 * @date:2021-1-10
 */
public class BIOServer {

    public static void main(String[] args) throws Exception {
        // 创建一个线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        // 创建一个Socket连接，指定端口
        ServerSocket server = new ServerSocket(6667);
        System.out.println("服务器启动了");
        while (true){
            System.out.println("当前线程id:" + Thread.currentThread().getId() + ",当前线程名称:" + Thread.currentThread().getName());
            System.out.println("等待连接...");
            final Socket socket = server.accept();
            System.out.println("已连接到一个客户端....");
            // 新创建一个线程，与之通信
            executor.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    public static void handler(Socket socket){
        try {
            System.out.println("线程信息: id = " + Thread.currentThread().getId() + " name =" + Thread.currentThread().getName());
            InputStream in = socket.getInputStream();
            byte[] bytes = new byte[2048];
            // 循环获取客户端信息
            while (true){
                System.out.println("线程信息: id = " + Thread.currentThread().getId() + " name =" + Thread.currentThread().getName());
                System.out.println("read...");
                int read = in.read(bytes);
                if (read != -1){
                    // 输出客户端发送过来的信息
                    String content = new String(bytes, 0, read);
                    System.out.println(content);
                }else{
                    break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭和client的连接");
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
