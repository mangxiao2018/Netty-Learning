# 10026-NIOServer-Client通信实例

**NIOServer代码：**

```java
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
        //创建ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个Selecor对象
        Selector selector = Selector.open();
        //绑定一个端口6666, 在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //把serverSocketChannel 注册到  selector 关心 事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("注册后的selectKey 数量=" + selector.keys().size());
        //循环等待客户端连接
        while (true){
            //这里我们等待1秒，如果没有事件发生, 返回
            //没有事件发生
            if (selector.select(1000) == 0){
                System.out.println("服务器等待了1秒，无连接");
                continue;
            }
            //如果返回的>0, 就获取到相关的 selectionKey集合
            //1.如果返回的>0， 表示已经获取到关注的事件
            //2. selector.selectedKeys() 返回关注事件的集合
            //通过 selectionKeys 反向获取通道
            Set<SelectionKey> selectKeys = selector.selectedKeys();
            System.out.println("SelectKeys数量= " + selectKeys.size());
            //遍历 Set<SelectionKey>, 使用迭代器遍历
            Iterator<SelectionKey> keyInterator = selectKeys.iterator();
            while (keyInterator.hasNext()){
                //获取到SelectionKey
                SelectionKey key = keyInterator.next();
                //根据key 对应的通道发生的事件做相应处理
                //如果是 OP_ACCEPT, 有新的客户端连接
                if (key.isAcceptable()){
                    //该该客户端生成一个 SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功 生成了一个 socketChannel" + socketChannel.hashCode());
                    //将SocketChannel 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socketChannel 注册到selector, 关注事件为 OP_READ， 同时给socketChannel
                    //关联一个Buffer
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.print("客户端连接后，注册的selectionkey 数量="+selector.keys().size());
                }
                //发生 OP_READ
                if(key.isReadable()){
                    //通过key 反向获取到对应channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    //获取到该channel关联的buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("from 客户端 " + new String(buffer.array()));
                }
                //手动从集合中移动当前的selectionKey, 防止重复操作
                keyInterator.remove();
            }
        }
        }
}
```

**NIOClient代码：**

```java
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @description:client of NIO
 * @author:mangxiao2018@126.com
 * @date:2021-1-28
 */
public class ClientTestCase {
    public static void main(String[] args) throws Exception{
        //得到一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //提供服务器端的ip 和 端口
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(address)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其它工作..");
            }
        }
        //...如果连接成功，就发送数据
        String content = "Hi, world!";
        //Wraps a byte array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(content.getBytes());
        //发送数据，将 buffer 数据写入 channel
        socketChannel.write(buffer);
        System.in.read();
    }
}
```

