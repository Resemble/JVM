package Thread;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2017/8/27 17:08
 */
public class FutureCallable {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        Task task = new Task();
        Task2 task2 = new Task2();
        Future<Integer> future = executorService.submit(task);
        Future<Integer> future2 = executorService.submit(task2);
        executorService.shutdown();

        ArrayList<Future<Integer>> futureArrayList = new ArrayList<>();
        futureArrayList.add(future);
        futureArrayList.add(future2);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程在执行任务");
        try {
            for (int i = 0; i < futureArrayList.size(); i++) {
                System.out.println(i);
                System.out.println("task 运行结果" + futureArrayList.get(i).get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务运行完毕");

    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("子1线程在进行计算");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            throw new InterruptedException();
            //return sum;
        }
    }



    static class Task2 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("子2线程在进行计算");
            Thread.sleep(5000);
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
