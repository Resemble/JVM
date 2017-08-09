package Java;

import java.io.UnsupportedEncodingException;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/8/9 20:23
 */
public class GetBytesTest {

    public static void main(String[] args) {
        String string = "123中文";
        try {
            System.out.println(string);
            System.out.println(new String(string.getBytes("UTF-8"), "GBK"));
            System.out.println(new String(string.getBytes("UTF-8")));
            System.out.println(new String(string.getBytes("GBK"), "UTF-8"));
            System.out.println(new String(string.getBytes("GBK")));

            System.out.println("*********************************");
            String chinese = "中文";//java内部编码
            String gbkChinese = new String(chinese.getBytes("GBK"),"ISO-8859-1");//转换成gbk编码
            String unicodeChinese = new String(gbkChinese.getBytes("ISO-8859-1"),"GBK");//java内部编码
            System.out.println(unicodeChinese);//中文
            String utf8Chinese = new String(unicodeChinese.getBytes("UTF-8"),"ISO-8859-1");//utf--8编码
            System.out.println(utf8Chinese);//乱码
            unicodeChinese = new String(utf8Chinese.getBytes("ISO-8859-1"),"UTF-8");//java内部编码
            System.out.println(unicodeChinese);//中文

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
