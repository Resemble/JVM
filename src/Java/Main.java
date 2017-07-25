package Java;
import java.util.Scanner;
/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description: 对称字符串
 * @date 2017/7/25 19:52
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String source = sc.nextLine();
        int length = source.length();
        if (length / 2 == 1) {
            return;
        }
        length = length - 2;
        source = source.substring(0, length);
        while (!judgeString(source, length)) {

            length = length - 2;
            if (length == 0) {
                return;
            }
            source = source.substring(0, length);
        }
        System.out.println(length);

    }

    public static boolean judgeString(String source, int length) {
        int halfLength = length / 2;
        String begin = source.substring(0, halfLength);
        String end = source.substring(halfLength, length);

        if (begin.equals(end)) {
            return true;
        } else {
            return false;
        }
    }

}
