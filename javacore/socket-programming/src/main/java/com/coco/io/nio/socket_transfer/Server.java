package com.coco.io.nio.socket_transfer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 使用 NIO 完成网络通信的三个核心：
 *
 * 1. 通道（Channel）：负责连接
 *        java.nio.channels.Channel 接口：
 *             |--SelectableChannel
 *                 |--SocketChannel
 *                 |--ServerSocketChannel
 *                 |--DatagramChannel
 *
 * 2. 缓冲区（Buffer）：负责数据的存取
 *
 * 3. 选择器（Selector）：是 SelectableChannel 的多路复用器。用于监控 SelectableChannel 的 IO 状况
 *
 */
public class Server {
    public static void main(String[] args) throws IOException {
        server();
    }

    public static void server() throws IOException {
        //1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        ssChannel.configureBlocking(false);
        //3. 绑定连接
        ssChannel.bind(new InetSocketAddress(9898));
        //4. 获取选择器
        Selector selector = Selector.open();
        //5. 将通道注册到选择器上, 并且指定“监听接收事件”
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        //6. 轮询式的获取选择器上已经“准备就绪”的事件。使用 select() 来监听到达的事件，它会一直阻塞直到有至少一个事件到达。
        while(selector.select() > 0){
            //7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while(it.hasNext()){
                //8. 获取准备“就绪”的是事件
                SelectionKey sk = it.next();
                //9. “接收就绪”
                if(sk.isAcceptable()){
                    SocketChannel sChannel = ssChannel.accept();
                    sChannel.configureBlocking(false);
                    sChannel.register(selector, SelectionKey.OP_READ);
                }else if(sk.isReadable()){
                    //“读就绪”
                    SocketChannel sChannel = (SocketChannel) sk.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len;
                    while((len = sChannel.read(buf)) > 0 ){
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                //15. 取消选择键 SelectionKey，每一个“事件关键字”被处理后都必须移除，否则下一次轮询时，这个事件会被重复处理
                it.remove();
            }
        }
    }
}
