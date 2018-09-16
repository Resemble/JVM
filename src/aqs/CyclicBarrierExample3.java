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
public class CyclicBarrierExample3 {

    private static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
        System.out.println("callback is running");
    });

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
                }
            });
        }
        executorService.shutdown();
    }

    private static void race(int threadNum) throws InterruptedException, BrokenBarrierException {
        Thread.sleep(1000);
        System.out.println("" + threadNum + " is ready");
        barrier.await();
        System.out.println("continue");
    }

}
