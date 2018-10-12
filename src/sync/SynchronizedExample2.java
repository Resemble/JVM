package sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package sync
 * @Description:
 * @date 2018/9/9 下午10:38
 */
public class SynchronizedExample2 {

    // 修饰一个类
    public static void test1() {
        synchronized (SynchronizedExample2.class) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
            }
        }
    }

    // 修饰一个静态方法
    public static synchronized void test2() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);

        }
    }

    public static void main(String[] args) {
        SynchronizedExample2 synchronizedExample1 = new SynchronizedExample2();
        SynchronizedExample2 synchronizedExample2 = new SynchronizedExample2();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(()-> {
            synchronizedExample1.test1();
        });
        executorService.execute(()-> {
            synchronizedExample2.test1();
        });
        executorService.shutdown();

    }
    
}
