package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package nio
 * @Description:
 * @date 2018/11/25 9:23 PM
 */
public class MyServer {

    private static ExecutorService executorService = Executors.newCachedThreadPool();


    private static class HandleMsg implements Runnable {

        /**
         * 创建一个客户端
         */
        Socket client;

        public HandleMsg(Socket client) {
            this.client = client;
        }

        @Override public void run() {
            /** 创建字符缓存输入流 */
            BufferedReader bufferedReader = null;

            /** 创建字符写入流 */
            PrintWriter printWriter = null;

            try {
                bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                printWriter = new PrintWriter(client.getOutputStream(), true);
                String inputLine = null;
                long timeStart = System.currentTimeMillis();
                while (((inputLine = bufferedReader.readLine()) != null)) {
                    printWriter.println(inputLine);
                }
                long timeEnd = System.currentTimeMillis();
                System.out.println("此线程花费了: " + (timeEnd - timeStart) + "秒! ");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                    printWriter.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        Socket client = null;
        while (true) {
            client = serverSocket.accept();
            System.out.println(client.getRemoteSocketAddress() + "地址的客服端连接成功! ");
            executorService.submit(new HandleMsg(client));
        }
    }

}
