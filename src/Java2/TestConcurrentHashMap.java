package Java2;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Java2
 * @Description: 死循环bug
 * @date 2018/8/29 下午12:58
 */
public class TestConcurrentHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        System.out.println("start.");
        map.computeIfAbsent("t",
            (String t) -> {
                map.put("t", "t");
                return "t";
            });
        System.out.println("fin.");

    }
}
