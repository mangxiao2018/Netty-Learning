# 10015-FileChannel类说明

**FileChannel** **类**

FileChannel主要用来对本地文件进行 IO 操作，常见的方法有：

\1) public int read(ByteBuffer dst) ，从通道读取数据并放到缓冲区中

\2) public int write(ByteBuffer src) ，把缓冲区的数据写到通道中

\3) public long transferFrom(ReadableByteChannel src, long position, long count)，从目标通道中复制数据到当前通道

\4) public long transferTo(long position, long count, WritableByteChannel target)，把数据从当前通道复制给目标通道