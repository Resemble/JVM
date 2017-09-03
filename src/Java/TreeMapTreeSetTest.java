package Java;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/9/3 16:28
 */
public class TreeMapTreeSetTest {


    public static void main(String[] args) {
        testPriorityQueue();
        System.out.println("-----------------------------");
        testTreeSet();
        System.out.println("-----------------------------");
        testTreeMap();
    }

    public static void testTreeMap() {
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(2, 3);
        treeMap.put(12, 33);
        treeMap.put(3, 4);
        treeMap.put(1, 5);
        treeMap.put(5, 3);
        treeMap.put(7, 32);
        treeMap.put(6, 21);
        for (Integer key : treeMap.keySet()) {
            System.out.println(key);
        }
        for (Map.Entry entry : treeMap.entrySet()) {
            System.out.println("key:" + entry.getKey() + " value:" + entry.getValue());
        }
    }

    public static void testPriorityQueue() {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(3);
        priorityQueue.add(5);
        priorityQueue.add(2);
        priorityQueue.add(333);
        priorityQueue.add(32);
        priorityQueue.add(12);
        priorityQueue.add(1222);
        while (!priorityQueue.isEmpty()) {
            System.out.println(priorityQueue.poll());
        }
    }


    public static void testTreeSet() {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(3);
        treeSet.add(5);
        treeSet.add(2);
        treeSet.add(333);
        treeSet.add(32);
        treeSet.add(12);
        treeSet.add(1222);
        for (int num : treeSet) {
            System.out.println(num);
        }
    }

}
