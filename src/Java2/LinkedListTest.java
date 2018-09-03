package Java2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Java2
 * @Description:
 * @date 2018/7/7 上午10:19
 */
public class LinkedListTest {


    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();

        linkedList.add(0);
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        linkedList.add(5);
        linkedList.poll();
        for (int i = 0; i < linkedList.size(); i++) {
            System.out.println(linkedList.get(i));
        }

        HashMap<Integer, Integer> hashMap = new HashMap<>();
        System.out.println("==");

        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(3);
    }

}
