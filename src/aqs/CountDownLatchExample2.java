package aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package aqs
 * @Description:
 * @date 2018/9/16 下午3:34
 */
public class CountDownLatchExample1 {

    private static int threadCount = 200;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            executorService.execute(() -> {
                try {
                    test(threadNum);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("finnish");
        executorService.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        Thread.sleep(100);
        System.out.println(threadNum);
        Thread.sleep(100);
    }

}
