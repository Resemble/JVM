package Thread;

import java.util.Random;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2018/9/7 下午8:34
 */
public class Test {
    public static void main(String[] args) {
        Random random = new Random(100);
        for (int i = 0; i < 10000; i++) {
            System.out.println(random.nextInt());

        }
    }
}
