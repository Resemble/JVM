package Java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/7/29 10:28
 */
public class ExecutorServiceCallableDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> resultList = new ArrayList<Future<String>>();

        for (int i = 0; i < 10; i++) {
            // 使用 ExcutorService 执行 Callable 类型的任务，并将结果保存在 future 变量中
            Future<String> future = executorService.submit(new TaskWithResult(i));
            // 将任务执行结果存储在 List 中
            resultList.add(future);
        }

        executorService.shutdown();

        for(Future<String> fs : resultList) {
            try {
                // 打印各个线程（任务）执行的结果
                System.out.println("执行结果：" + fs.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                executorService.shutdown();
                e.printStackTrace();
                return;
            }
        }

    }

}

class TaskWithResult implements Callable<String> {

    private int id;

    public TaskWithResult(int id) {
        this.id = id;
    }

    public String call() throws TaskException {
        System.out.println("call() 方法被自动调用，当前线程为：Thread.currentThread().getName()" + Thread.currentThread().getName());
        // 不加下面这一句可以看到正常执行结果，加上验证可以抛出异常
        if (new Random().nextBoolean())
            throw new TaskException("Meet error in task." + Thread.currentThread().getName());
        // 一个模拟耗时的操作
        for (int i = 9999999; i >0; i--);
        return "call() 方法被自动调用，任务的结果是：" + id + " " + Thread.currentThread().getName();
    }

    private class TaskException extends Exception {
        public TaskException(String message) {
            super(message);
        }
    }
}
