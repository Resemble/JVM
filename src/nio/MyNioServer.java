package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package nio
 * @Description:
 * @date 2018/11/25 10:03 PM
 */
public class MyNioServer {

    private Selector selector;
    private final static int port = 8888;
    private final static int BUF_SIZE = 10240;

    private void initServer() throws IOException {
        /* 创建通道管理器对象 selector */
        this.selector = Selector.open();
        /* 创建一个通道对象 channel */
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        /* 将通道设置为非阻塞 */
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        /* 将上述的通道管理器和通道绑定，并为该通道注册 OP_ACCEPT 事件 */
        /* 注册事件后，当该时间到达时， selector.select()会返回（一个 key），如果该事件没到达 selector.select() 会一直阻塞*/
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            /* 这是一个阻塞方法，一直等待直到有数据可读，返回值是 key 的数量（可以有多个）*/
            selector.select();
            Set keysSet = selector.selectedKeys();
            Iterator iterator = keysSet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey1 = (SelectionKey) iterator.next();
                iterator.remove();
                if (selectionKey1.isAcceptable()) {
                    doAccept(selectionKey1);
                } else if (selectionKey1.isReadable()) {
                    doRead(selectionKey1);
                } else if (selectionKey1.isWritable() && selectionKey1.isValid()) {
                    doWrite(selectionKey1);
                } else if (selectionKey1.isConnectable()) {
                    System.out.println("连接成功！");
                }
            }

        }
    }

    public void doAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        System.out.println("ServerSocketChannel 正在循环监听");
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selectionKey.selector(), SelectionKey.OP_READ);
    }


    public void doRead(SelectionKey selectionKey) throws IOException {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
        long bytesRead = socketChannel.read(byteBuffer);
        while (bytesRead > 0) {
            byteBuffer.flip();
            byte[] data = byteBuffer.array();
            String info = new String(data).trim();
            System.out.println("从客户端发过来的消息是：" + info);
            byteBuffer.clear();
            bytesRead = socketChannel.read(byteBuffer);
        }
        if (bytesRead == -1) {
            socketChannel.close();
        }
    }

    public void doWrite(SelectionKey selectionKey) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(BUF_SIZE);
        byteBuffer.flip();
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);
        }
        byteBuffer.compact();
    }

    public static void main(String[] args) throws IOException {
        MyNioServer myNioServer = new MyNioServer();
        myNioServer.initServer();
    }
}
