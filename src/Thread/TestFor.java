package Thread;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2018/9/7 下午7:56
 */
public class TestFor {



     public static void main(String[] args) {
        ArrayList<Future<Integer>> arrayList = new ArrayList<>();
        ExecutorService executorService = Executors.newCachedThreadPool();


        for (int i = 0; i < 30; i++) {
            System.out.println(i);
            Task task = new Task(i);
            Future<Integer> future = executorService.submit(task);
            arrayList.add(future);
        }


        int i = -1;
        for (Future future : arrayList) {
            System.out.println(i++);
            try {
                System.out.println("线程：" + arrayList.get(i).get(7, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }

        }

    }

}


class Task implements Callable<Integer> {
    final static Random random = new Random(5);

    final int i;

    public Task(int i) {
        this.i = i;
    }

    @Override public Integer call() throws Exception {
        System.out.println("子线程:" + i + "在进行计算");
        Thread.sleep(5000);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return  this.i;
    }
}
