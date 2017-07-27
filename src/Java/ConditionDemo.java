package Java;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description: 条件锁测试
 * 虽然thread 2一开始就调用了signal()方法去唤醒thread 1，但是因为thread 2在4秒钟后才释放锁，也即thread 1在4秒后才获得锁，
 * 所以thread 1的await方法在4秒钟后才返回，并且返回负值。
 * @date 2017/7/27 10:41
 */
public class ConditionDemo {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(new Date() + "\tThread 1 is waiting");
                try {
                    long waitTime = condition.awaitNanos(TimeUnit.SECONDS.toNanos(2));
                    System.out.println(new Date() + "\tThread 1 remaining time " + waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(new Date() + "\tThread 1 is wake up");
            } finally {
                lock.unlock();
            }
        }).start();


        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(new Date() + "\tThread 2 is running");
                condition.signal();

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(new Date() + "\tThread 2 ended");
            } finally {
                lock.unlock();
            }
        }).start();

    }

}
