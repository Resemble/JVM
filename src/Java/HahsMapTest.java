package Java;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/7/25 17:20
 */
public class HahsMapTest {

    public static void main(String[] args) {

        HashMap<Integer, String> hashMap = new HashMap();
        ConcurrentHashMap<String, String> concurrentHashMap =  new ConcurrentHashMap<>();
        concurrentHashMap.put("fsd", "fsd");
        hashMap.put(2, "23");
        hashMap.put(22, "232");
        hashMap.put(21, "er3");

        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        for (Integer key : hashMap.keySet()) {
            System.out.println("Key = " + key);
        }
        for (String value : hashMap.values()) {
            System.out.println("Value = " + value);
        }
    }

}
