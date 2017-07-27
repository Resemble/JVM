package Java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * 装饰器模式
 * 适配器模式
 *
 *
 *java I/O库具有两个对称性，它们分别是：
 *（1）输入-输出对称：比如InputStream 和OutputStream 各自占据Byte流的输入和输出的两个平行的等级结构的根部；
 * 而Reader和Writer各自占据Char流的输入和输出的两个平行的等级结构的根部。
 *（2）byte-char对称：InputStream和Reader的子类分别负责byte和Char流的输入；OutputStream和Writer的子类分别负责byte和Char流的输出
 * 可以看出，这个类接受一个类型为inputStream的System.in对象(字节流)，将之适配成Reader类型(字符流)，然后再使用
 * BufferedReader类“装饰”它，将缓冲功能加上去。这样一来，就可以使用BufferedReader对象的readerLine()
 * 方法读入整行的输入数据，数据类型是String。
 *
 *
 *
 * @date 2017/7/26 17:40
 */
public class IO {

    public static void main(String[] args) {
        String line;
        InputStreamReader input = new InputStreamReader(System.in);
        System.out.println("Enter data and push enter:");
        BufferedReader reader = new BufferedReader(input);
        try {
            line  = reader.readLine();
            System.out.println("Data entered: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

    }

}
