package gc;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package gc
 * @Description:
 * @date 2018/11/25 11:12 AM
 */
public class SystemInTest {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = System.in;
        int i = inputStream.read();
        System.out.println("exit");
    }

}
