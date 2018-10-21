package aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package aqs
 * @Description:
 * @date 2018/10/21 9:32 PM
 */
public class CountDownLatchService {

    private CountDownLatch countDownLatch;

    public CountDownLatchService(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void exec() {
        try {
            System.out.println(Thread.currentThread().getName() + " execute task.");
            TimeUnit.SECONDS.sleep(2);
            System.out.println(Thread.currentThread().getName() + " finished task.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countDownLatch.countDown();
        }
    }

}
