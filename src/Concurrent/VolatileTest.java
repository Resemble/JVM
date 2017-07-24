package Concurrent;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Concurrent
 * @Description: Volatile 1.变量对所有线程的可见性，一条线程修改了变量的值，
 * 新值对其他线程来说总是可以立即得知的      2.禁止指令重排优化
 * volatile 变量自增运算测试，结果小于 200000, 原因在于 increase 方法被分解成了多条，然后就没有同步了
 * @date 2017/7/24 11:00
 */
public class VolatileTest {

    public static volatile int race = 0;

    public static int anInt = 0;

    public static void increase () {
        System.out.println("increase once, race:" + race);
//        System.out.println("increase once, anInt:" + anInt);
        race++;
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
