# 10019-使用transferFrom进行文件拷贝

**通过transferFrom完成文件的拷贝**

**类FileChannelTestCase的代码片段**

```java
	/**
     * 通过FileInputStream,FileOutputStream,channel.transferFrom进行输入输出流转换
     * @throws Exception
     */
    public static void transfer() throws Exception {
        // 创建相关流
        FileInputStream fileInputStream = new FileInputStream("d:\\06.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("d:\\07.txt");
        // 获取各个流对应的filechannel
        FileChannel sourceChannel = fileInputStream.getChannel();
        FileChannel targetChannel = fileOutputStream.getChannel();

        targetChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
        // 关闭channel
        sourceChannel.close();
        targetChannel.close();
        // 关闭流
        fileInputStream.close();
        fileOutputStream.close();
    }
```

