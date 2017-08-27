package Thread;

import java.util.concurrent.*;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2017/8/27 17:17
 */
public class FutureTaskCallable {
    public static void main(String[] args) {
        // 第一种方式
        ExecutorService executorService = Executors.newCachedThreadPool();
        Task2 task = new Task2();
        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
        Future future = executorService.submit(futureTask);
        System.out.println(future);
        if ( future.isCancelled()) {
            System.out.println("Cancelled!");
        } else {
            System.out.println("Not Cancelled");
        }
//        executorService.execute(futureTask);
        executorService.shutdown();

//        // 第二种方式 注意这种方式和第一种方式效果是类似的，只不过一个使用的是ExecutorService，一个使用的是Thread
//        Task2 task2 = new Task2();
//        FutureTask<Integer> futureTask = new FutureTask<Integer>(task2);
//        Thread thread = new Thread(futureTask);
//        thread.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程在执行任务");
        try {
            System.out.println("task 运行结果" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("所有任务运行完毕");

    }

    static class Task2 implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        }
    }
}
