# 10050-任务队列taskqueue中的Task的3种典型使用场景

1、用户程序自定义的普通任务:**ctx.channel().eventLoop().execute()**

2、用户自定义定时任务:**ctx.channel().eventLoop().schedule()**

3、非当前 Reactor 线程调用 Channel 的各种方法

*在***推送系统***的业务线程里面，根据***用户的标识***，找到对应的* **Channel** **引用***，然后调用* *Write* *类方法向该用户推送消息，就会进入到这种场景。最终的* *Write* *会提交到任务队列中后被***异步消费**