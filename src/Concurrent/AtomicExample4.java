package Concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Concurrent
 * @Description:
 * @date 2018/9/3 下午10:38
 */
public class AtomicExample4 {

    private static AtomicReference<Integer> count = new AtomicReference<>(0);

    public static void main(String[] args) {
        count.compareAndSet(0, 2);
        count.compareAndSet(0, 1);
        count.compareAndSet(1, 3);
        count.compareAndSet(2, 4);
        count.compareAndSet(3, 5);
        System.out.println("count: " + count.get());

    }

}
