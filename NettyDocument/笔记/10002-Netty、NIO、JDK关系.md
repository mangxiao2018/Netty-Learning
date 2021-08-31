# 10002-Netty、NIO、JDK关系

Netty底层使用的是TCP/IP通信，JDK原生建立在TCP/IP上，再往上是NIO，Netty建立在NIO的上面。这张图清楚的表明了Netty、NIO、JDK三者之间的关系。

![10002](images/10002.jpg)

==所以要想学好Netty，需要把JDK的IO、NIO、网络编程学好==