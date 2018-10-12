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
public class SynchronizedExample1 {

    // 修饰一个代码块
    public void test1() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " - " + i);
            }
        }
    }

    // 修饰一个方法
    public synchronized void test2() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " - " + i);

        }
    }

    public static void main(String[] args) {
        SynchronizedExample1 synchronizedExample1 = new SynchronizedExample1();
        SynchronizedExample1 synchronizedExample2 = new SynchronizedExample1();
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
