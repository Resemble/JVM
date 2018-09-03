package Concurrent;

import java.util.HashMap;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Concurrent
 * @Description:
 * @date 2018/8/27 下午11:15
 */
public class HashMapTest{
    private HashMap<Integer,Integer> map = new HashMap<>();

    public HashMapTest(){
        int insertNum = 1000000;
        Thread t1 = new Thread() {
            public void run() {
                for (int i = 0; i < insertNum; i++) {
                    map.put(new Integer(i), Integer.valueOf(i));
                }
            }
        };
        Thread t2 = new Thread() {
            public void run() {
                for (int i = insertNum; i < insertNum * 2; i++) {
                    map.put(new Integer(i),Integer.valueOf(i));
                }
            }
        };
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
            new HashMapTest();
    }
}

