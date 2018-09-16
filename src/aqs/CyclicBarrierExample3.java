package aqs;

import java.util.concurrent.*;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package aqs
 * @Description:
 * @date 2018/9/16 下午3:54
 */
public class CyclicBarrierExample2 {

    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int threadNum = i;
            Thread.sleep(1000);
            executorService.execute(() -> {
                try {
                    race(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    private static void race(int threadNum)
        throws InterruptedException, BrokenBarrierException, TimeoutException {
        Thread.sleep(1000);
        System.out.println("" + threadNum + " is ready");
        try {
            barrier.await(2000, TimeUnit.MILLISECONDS);  // 只等2s
        } catch (BrokenBarrierException | TimeoutException e) {
            System.out.println("warn " + e);
        }
        System.out.println("continue");
    }

}
