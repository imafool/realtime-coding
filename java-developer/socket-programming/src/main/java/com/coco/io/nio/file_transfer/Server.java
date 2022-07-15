package com.coco.io.nio.file_transfer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Server {
    private final ByteBuffer buffer = ByteBuffer.allocate(1024*1024);

    protected Map<SelectionKey, FileChannel> fileMap = new HashMap<>(); //保存每个连接

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.startServer();
    }
    public void startServer() throws IOException{
        //选择器
        Selector selector = Selector.open();
        //通道
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        //绑定
        serverChannel.bind(new InetSocketAddress(8888));
        //注册
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已开启...");
        while (true) {
            //选择频道
            int num = selector.select();
            if (num == 0)
                continue;
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            //遍历频道，根据不同状态做不同处理
            while (it.hasNext()) {
                SelectionKey key = it.next();
                //接收就绪
                if (key.isAcceptable()) {
                    ServerSocketChannel serverChannel1 = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverChannel1.accept();
                    if (socketChannel == null)
                        continue;
                    socketChannel.configureBlocking(false);
                    SelectionKey key1 = socketChannel.register(selector, SelectionKey.OP_READ);
                    InetSocketAddress remoteAddress = (InetSocketAddress)socketChannel.getRemoteAddress();
                    File file = new File(remoteAddress.getHostName() + "_" + remoteAddress.getPort() + ".txt");
                    FileChannel fileChannel = new FileOutputStream(file).getChannel();
                    fileMap.put(key1, fileChannel);
                    System.out.println(socketChannel.getRemoteAddress() + "连接成功...");
                    buffer.clear();
                    buffer.put((socketChannel.getRemoteAddress() + "连接成功").getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                    buffer.clear();
                } else if (key.isReadable()){
                    //读就绪
                    readData(key);
                }
                it.remove();
            }
        }
    }

    protected void readData(SelectionKey key) throws IOException  {
        FileChannel fileChannel = fileMap.get(key);
        buffer.clear();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        int num;
        try {
            while ((num = socketChannel.read(buffer)) > 0) {
                buffer.flip();
                // 写入文件
                fileChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            key.cancel();
            e.printStackTrace();
            return;
        }
        if (num == -1) {
            fileChannel.close();
            System.out.println("上传完毕");
            buffer.put((socketChannel.getRemoteAddress() + "上传成功").getBytes());
            buffer.clear();
            socketChannel.write(buffer);
            key.cancel();
        }
    }
}