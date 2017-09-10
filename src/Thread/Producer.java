package Thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2017/9/10 9:55
 */
public class Producer implements Callable{

    private static String ENDING_SYMBOL = "EOF";
    private ArrayBlockingQueue<String> arrayBlockingQueue;
    private int consumerNum;

    public Producer(ArrayBlockingQueue<String> queue, int consumerNum) {
        this.arrayBlockingQueue = queue;
        this.consumerNum = consumerNum;
    }


    @Override
    public Object call() throws Exception {
        for (int i = 0; i < 100; i++) {
            arrayBlockingQueue.put("data_" + i);
        }
        for (int i = 0; i < consumerNum; i++) {
            arrayBlockingQueue.put(ENDING_SYMBOL);
        }
        System.out.println("producer over!-------");
        return "producer over!";
    }
}
