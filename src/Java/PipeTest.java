package Java;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description: 管道异常测试  在利用管道读写数据时，必须保证利用管道读写数据的线程都不能退出
 * 2个 while 不能删除 否则有 “java - IOException: Read or Write end dead”
 * @date 2017/7/25 10:23
 */
public class PipeTest {

    public static void main(String[] args) {

        PipedOutputStream pos = new PipedOutputStream();
        PipedInputStream pis = new PipedInputStream();
        try {
            // 将管道输入流和输出流连接 此过程也可以通过重载的构造函数来实现
            pos.connect(pis);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Producer producer = new PipeTest().new Producer(pos);
        Consumer consumer = new Consumer(pis);  // 使用了 static
        producer.start();
        consumer.start();

    }

    private class Producer extends Thread {

        private PipedOutputStream pos;

        public Producer(PipedOutputStream pos) {
            this.pos = pos;
        }

        public void run() {
            int i = 0;
            while (true) {
                try {
                    pos.write(i);
                    i++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class Consumer extends Thread {

        private PipedInputStream pis;

        public Consumer(PipedInputStream pis) {
            this.pis = pis;
        }

        public void run() {
            while(true) {
                try {
                    System.out.println(pis.read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
