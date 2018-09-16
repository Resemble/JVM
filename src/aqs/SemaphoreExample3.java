package aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package aqs
 * @Description:
 * @date 2018/9/16 下午3:34
 */
public class SemaphoreExample3 {

    private static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    if(semaphore.tryAcquire()){  //尝试获取一个许可
                        test(threadNum);
                        semaphore.release();  // 释放一个许可
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(100);
        System.out.println(threadNum);
    }

}
