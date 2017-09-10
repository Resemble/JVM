package Thread;

import java.util.concurrent.*;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2017/9/10 9:56
 */
public class Consumer implements Callable{

    private static String ENDING_SYMBOL = "EOF";
    private ArrayBlockingQueue<String> arrayBlockingQueue;

    public Consumer(ArrayBlockingQueue<String> arrayBlockingQueue) {
        this.arrayBlockingQueue = arrayBlockingQueue;
    }

    @Override
    public Object call() throws Exception {
        while(true) {
            String data = arrayBlockingQueue.take();
            System.out.println("Consumer " + Thread.currentThread().getName() + "consume:" + data);
            if (ENDING_SYMBOL.equals(data)) break;
            TimeUnit.MILLISECONDS.sleep(100);
        }
        System.out.println("Consumer Over!--------");
        return "Consumer Over!";
    }
}

class RunProducerAndConsumer {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(200);
        ExecutorService es = Executors.newCachedThreadPool();
        Producer producer = new Producer(arrayBlockingQueue, 2);
        Consumer consumer1 = new Consumer(arrayBlockingQueue);
        Consumer consumer2 = new Consumer(arrayBlockingQueue);

        FutureTask futureTask1 = new FutureTask(producer);
        FutureTask futureTask2 = new FutureTask(consumer1);
        FutureTask futureTask3 = new FutureTask(consumer2);

        Future future1 = es.submit(futureTask1);
        Future future2 = es.submit(futureTask2);
        Future future3 = es.submit(futureTask3);

        try {
            System.out.println(future1.get() + "********1111");
            System.out.println(futureTask1.get() + "********");
            System.out.println(future2.get() + "***********222222");
            System.out.println(futureTask2.get() + "***********");
            System.out.println(futureTask3.get() + "**********");
            es.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}