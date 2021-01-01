package com.mangxiao.netty.samples.nio.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description:java IO 的服务器
 * @author:mangxiao2018@126.com
 * @date:2021-1-1
 */
public class OldIOServer {

    public static void main() throws Exception {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true){
            Socket socket = serverSocket.accept();
            DataInputStream dataInputSteam = new DataInputStream(socket.getInputStream());
            try {
                byte[] byteArray = new byte[4096];
                while (true){
                    int readCount = dataInputSteam.read(byteArray, 0, byteArray.length);

                    if (-1 == readCount){
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
