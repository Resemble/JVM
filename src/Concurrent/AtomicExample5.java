package Concurrent;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Concurrent
 * @Description:
 * @date 2018/9/3 下午10:42
 */
public class AtomicExample5 {

    private static AtomicIntegerFieldUpdater<AtomicExample5> updater = AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class, "count");

    public volatile int count = 100;

    private static AtomicExample5 example5 = new AtomicExample5();

    public int getCount() {
        return count;
    }

    public static void main(String[] args) {
        if (updater.compareAndSet(example5, 100, 120)) {
            System.out.println("update success 1: " + example5.getCount());
        }

        if(updater.compareAndSet(example5, 100, 120)) {
            System.out.println("update success 2: " + example5.getCount());
        } else {
            System.out.println("update failed: " + example5.getCount());
        }
    }

}
