package gc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package gc
 * @Description:
 * @date 2018/11/25 10:50 AM
 */


class TestTask implements Runnable {

    @Override public void run() {
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class WaitTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new TestTask());
    }
}
