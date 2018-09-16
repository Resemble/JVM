package immutable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package immutable
 * @Description:
 * @date 2018/9/16 上午10:47
 */
public class ImmutableExample1 {


    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map = new HashMap<>();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
    }

    public static void main(String[] args) {
        map.put(1, 3);
        System.out.println(map.get(1));
    }

    private void test(final int a) {
      //  a = 1;
    }

}
