# 10024-Selector选择器注意事项

**注意事项**

1、NIO中的 ServerSocketChannel功能类似ServerSocket，SocketChannel功能类似Socket

2、selector 相关方法说明

```java
selector.select()//阻塞
selector.select(1000);//阻塞1000毫秒，在1000毫秒后返回
selector.wakeup();//唤醒selector
selector.selectNow();//不阻塞，立马返还
```



*坦白的讲selector的select()相关方法是阻塞的。但这个阻塞已比BIO的阻塞强了很多。*

