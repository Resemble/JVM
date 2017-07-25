package Java;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description: 判断回文
 * @date 2017/7/25 19:52
 */
public class Main2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String source = sc.nextLine();
        int length = source.length();
        if (length == 1) {
            System.out.println(1);
        } else {
            char[] sourceChar = source.toCharArray();
            HashMap<Character, Integer> hashMap = new HashMap<>();
            for (int i = 0; i < source.length(); i++) {
                char key = sourceChar[i];

                if (hashMap.containsKey(key)) {
                    int value = hashMap.get(key);
                    if (value == 0) {
                        value++;
                        hashMap.put(key, value);
                    } else {
                        value--;
                        hashMap.put(key, value);
                    }

                } else {
                    hashMap.put(key, 1);
                }
            }
            int count = 0;
            for(Integer value : hashMap.values()) {
                if (value == 1) {
                    count++;
                }
            }
            System.out.println(count);
        }


    }


}
