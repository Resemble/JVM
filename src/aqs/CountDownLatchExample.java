package aqs;

import java.util.concurrent.CountDownLatch;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package aqs
 * @Description:
 * @date 2018/10/21 9:32 PM
 */
public class CountDownLatchExample {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        CountDownLatchService countDownLatchService = new CountDownLatchService(countDownLatch);
        Runnable task = () -> countDownLatchService.exec();

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(task);
            thread.start();
        }

        System.out.println("main thread await.");
        countDownLatch.await();
        System.out.println("main thread finished await.");

    }

}
