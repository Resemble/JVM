package Java;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;


/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/9/4 11:36
 */
public class TestBase64 {

    public static void main(String[] args) {
        String string =  "dsf";
        String res = new BASE64Encoder().encode(string.getBytes());
        System.out.println(res);
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] bytes = base64Decoder.decodeBuffer(res);
            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
