# 10027-SelectionKey

**1、SelectionKey说明**

**表示** **Selector** **和网络通道的注册关系****,** **共四种****:**

```java
int OP_ACCEPT：有新的网络连接可以 accept，值为 16

int OP_CONNECT：代表连接已经建立，值为 8

int OP_READ：代表读操作，值为 1

int OP_WRITE：代表写操作，值为 4
```



源码中：

```java
public static final int OP_READ = 1 << 0; //1

 //1向左移动2位，每移动一位乘以2

public static final int OP_WRITE = 1 << 2; //4

public static final int OP_CONNECT = 1 << 3; //8

public static final int OP_ACCEPT = 1 << 4;  //16
```



**2、SelectionKey相关方法**

```java
public abstract class SelectionKey {

    public abstract Selector selector();//得到与之关联的 Selector 对象

    public abstract SelectableChannel channel();//得到与之关联的通道

    public final Object attachment();//得到与之关联的共享数据

    public abstract SelectionKey interestOps(int ops);//设置或改变监听事件

    public final boolean isAcceptable();//是否可以 accept

    public final boolean isReadable();//是否可以读

    public final boolean isWritable();//是否可以写

}
```

