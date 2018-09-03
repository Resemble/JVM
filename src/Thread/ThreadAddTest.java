package Thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2018/7/30 下午10:30
 */
public class ThreadAddTest {

    public static int getTotal(final List<Integer> list1, final List<Integer> list2)
        throws ExecutionException, InterruptedException {
        Future<Integer> future = Executors.newCachedThreadPool().submit(new Callable<Integer>() {
            @Override public Integer call() throws Exception {
                int sum = 0;
                for (int num : list1) {
                    sum += num;
                }
                System.out.println("sum1");
                return sum;
            }
        });
        int sum = 0;
        for (int num : list2) {
            sum += num;
        }
        Thread.sleep(1000);  // sum1 和 sum2 并行的
        System.out.println("sum2");
        return sum + future.get();
    }

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        List<Integer> arrayList2 = new ArrayList();
        arrayList2.add(1);
        arrayList2.add(2);
        arrayList2.add(3);
        // new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        ExecutorService executorService = Executors.newCachedThreadPool();

        // new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();

        // new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        ExecutorService executorService2 = Executors.newFixedThreadPool(3);

        //  super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS, new DelayedWorkQueue());
        ExecutorService executorService3 = Executors.newScheduledThreadPool(2);
        try {
            System.out.println(getTotal(arrayList, arrayList2));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
