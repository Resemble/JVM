package nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package nio
 * @Description:
 * @date 2018/11/25 9:53 PM
 */
public class MyClient {

    public static void main(String[] args) throws IOException {
        Socket client = null;
        PrintWriter printWriter = null;
        BufferedReader bufferedReader = null;
        client = new Socket();
        client.connect(new InetSocketAddress("localhost", 8888));
        printWriter = new PrintWriter(client.getOutputStream(), true);
        printWriter.println("hello");
        printWriter.flush();

        bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println("来自服务器的信息是：" + bufferedReader.readLine());
        printWriter.close();
        bufferedReader.close();
        client.close();
    }

}
