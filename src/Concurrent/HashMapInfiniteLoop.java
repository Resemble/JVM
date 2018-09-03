package Concurrent;

import java.util.HashMap;
import java.util.Random;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Concurrent
 * @Description:
 * @date 2018/7/14 下午3:42
 */
public class HashMapInfiniteLoop {

    private static HashMap<Integer, String> map = new HashMap<Integer, String>(2, 0.75f);
    private static Random random = new Random(1000000);

    public static void thread1() {
        new Thread("Thread1") {
            public void run() {
                while(true) {
                    map.put(random.nextInt(), "B");
                    System.out.println(map.size());
                }
            }
        }.start();
    }


    public static void thread2() {
        new Thread("Thread2") {
            public void run() {
                while (true) {
                    map.put(random.nextInt(), "A");
                    System.out.println(map.size());
                }

            }
        }.start();
    }

    public static void main(String[] args) {
        thread1();
        thread2();

    }

}
