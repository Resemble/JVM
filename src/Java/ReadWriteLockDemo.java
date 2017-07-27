package Java;

import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description: 读读不互斥
 * thread 1和thread 2都只需获得读锁，因此它们可以并行执行。而thread 3因为需要获取写锁，必须等到thread 1和thread 2释放锁后才能获得锁。
 * @date 2017/7/27 10:10
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        new Thread(() -> {
            try {
                readWriteLock.readLock().lock();
                System.out.println(new Date() + "\tThread 1 started with read lock");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(new Date() + "\tThread1 ended");

            } finally {
                readWriteLock.readLock().unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                readWriteLock.readLock().lock();
                System.out.println(new Date() + "\tThread 2 started with read lock");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(new Date() + "\tThread 2 ended");

            } finally {
                readWriteLock.readLock().unlock();
            }
        }).start();

        new Thread(() -> {
            readWriteLock.writeLock().lock();
//            Lock lock = readWriteLock.writeLock();
//            lock.lock();
            try{
                System.out.println(new Date() + "\tThread 3 start with write lock");
                try {
                    Thread.sleep(2000);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(new Date() + "\tThread 3 ended");
            } finally {
//                lock.unlock();
                readWriteLock.writeLock().unlock();
            }
        }).start();

       new Thread(() -> {
//            readWriteLock.writeLock().lock();
            Lock lock = readWriteLock.writeLock();
            lock.lock();
            try{
                System.out.println(new Date() + "\tThread 4 start with write lock");
                try {
                    Thread.sleep(2000);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
                System.out.println(new Date() + "\tThread 4 ended");
            } finally {
                lock.unlock();
//                readWriteLock.writeLock().unlock();
            }
        }).start();

    }

}
