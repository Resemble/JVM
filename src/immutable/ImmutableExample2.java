package immutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package immutable
 * @Description:  线程安全，使用易抛出异常
 * @date 2018/9/16 上午10:47
 */
public class ImmutableExample2 {


    private static Map<Integer, Integer> map = new HashMap<>();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(1, 3);
        System.out.println(map.get(1));
    }



}
