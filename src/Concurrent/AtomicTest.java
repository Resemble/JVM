package Concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Concurrent
 * @Description: Atomic 变量自增运算测试, int 不同步, AtomicInteger 同步
 * @date 2017/7/24 10:32
 */
public class AtomicTest {

    public static AtomicInteger race = new AtomicInteger(0);

    public static int anInt = 0;

    public static void increase () {
        System.out.println("increase once, race:" + race);
//        System.out.println("increase once, anInt:" + anInt);
        race.incrementAndGet();
//        anInt ++;

    }

    private static final int THREADS_COUNT = 20;

    public static void main (String[] args) throws Exception {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < THREADS_COUNT; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        increase();
                    }
                }
            });
            threads[i].start();
        }

        System.out.println(race);
//        System.out.println(anInt);
    }

}
