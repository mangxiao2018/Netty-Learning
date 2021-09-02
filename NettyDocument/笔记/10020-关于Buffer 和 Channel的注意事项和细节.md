# 10020-关于Buffer 和 Channel的注意事项和细节

1、ByteBuffer 支持类型化的put 和 get, put 放入的是什么数据类型，get就应该使用相应的数据类型来取出，否则可能有 BufferUnderflowException 异常。[代码示例]

```java
mport java.nio.ByteBuffer;

/**
 * @description:ByteBuffer各类型数据的写入与读取
 * @author:mangxiao2018@126.com
 * @date:2021-1-14
 */
public class ByteBufferTestCase {

    public static void main(String[] args){
        ByteBuffer buffer = ByteBuffer.allocate(64);

        buffer.putInt(9);
        buffer.putLong(100);
        buffer.putChar('青');
        buffer.putShort((short) 3);

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
//        System.out.println(buffer.getLong()); //全报错 BufferUnderflowException
    }
}
```



2、可以将一个普通Buffer 转成只读Buffer [代码示例]

```java
import java.nio.ByteBuffer;

/**
 * @description:ReadOnlyBuffer 实例
 * @author:mangxiao2018@126.com
 * @date:2021-1-14
 */
public class ReadOnlyBufferTestCase {

    public static void main(String[] args){
        // 创建一个大小为64的buffer并写入数据
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i< 64; i++){
            buffer.put((byte) i);
        }
        // 反转
        buffer.flip();
        // 得到一个只读 buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass());

        while (readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
        // throws ReadOnlyBufferException
        readOnlyBuffer.put((byte) 100);
    }
}
```



3、NIO 还提供了 MappedByteBuffer， 可以让文件直接在内存（堆外的内存）中进行修改， 而如何同步到文件由NIO 来完成. [代码示例]

```java

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: MappedByteBuffer可以实现内存修改，代码上无需直接操作文件
 * @author:mangxiao2018@126.com
 * @date:2021-1-15
 */
public class MappedByteBufferTestCase {
    public static void main(String[] args) throws Exception {
        RandomAccessFile file = new RandomAccessFile("d:\\1.txt","rw");
        FileChannel channel = file.getChannel();
        /**
         * 参数1: FileChannel.MapMode.READ_WRITE 使用的读写模式
         * 参数2： 0 ： 可以直接修改的起始位置
         * 参数3:  5: 是映射到内存的大小(不是索引位置) ,即将 1.txt 的多少个字节映射到内存
         * 可以直接修改的范围就是 0-5
         * 实际类型 DirectByteBuffer
         */
        MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
        buffer.put(0,(byte) 'H');
        buffer.put(3,(byte) '9');
        // throws IndexOutOfBoundsException
//        buffer.put(5,(byte) 'W');

        file.close();
        System.out.println("修改完成...");
    }
}
```



4、前面我们讲的读写操作，都是通过一个Buffer 完成的，NIO 还支持 通过多个Buffer (即 Buffer 数组) 完成读写操作，即 Scattering 和 Gathering 【代码示例】

```java
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @description:
 * 1、Scattering：将数据写入到buffer时，可以采用buffer数组，依次写入  [分散]
 * 2、Gathering: 从buffer读取数据时，可以采用buffer数组，依次读
 * @author:mangxiao2018@126.com
 * @date:2021-1-15
 */
public class ScatteringAndGatheringTestCase {

    public static void main(String[] args) throws Exception {
        // 使用 ServerSocketChannel 和 SocketChannel 网络
        ServerSocketChannel channel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(7000);

        channel.socket().bind(address);
        // 创建buffer数组
        ByteBuffer[] buffers = new ByteBuffer[2];
        buffers[0] = ByteBuffer.allocate(5);
        buffers[1] = ByteBuffer.allocate(3);

        SocketChannel socketChannel = SocketChannel.open();
        // 假定从客户端接收8个字节
        int messageLength = 8;
        while (true){
            int byteRead = 0;
            if (byteRead < messageLength){
                long l = socketChannel.read(buffers);
                // 累计读取的字节数
                byteRead += 1;
                System.out.println("byteRead=" + byteRead);
                // 使用流打印, 看看当前的这个buffer的position 和 limit
                Arrays.asList(buffers).stream().map(buffer -> "position="
                        + buffer.position()
                        + ",limit=" +buffer.limit()).forEach(System.out::println);
            }
            // 将所有的buffer进行flip
            Arrays.asList(buffers).forEach(buffer -> buffer.flip());
            // 将数据读出显示到客户端
            long byteWrite = 0;
            while (byteWrite < messageLength){
                long l = socketChannel.write(buffers);
                byteWrite += 1;
            }
            // 将所有的buffer 进行clear
            Arrays.asList(buffers).forEach(buffer -> {
                buffer.clear();
            });
            System.out.println("byteRead=" + byteRead + ", byteWrite=" + byteWrite
                    + ", messageLength=" + messageLength);
        }
    }
}
```

