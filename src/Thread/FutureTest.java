package Thread;

import java.util.concurrent.ExecutionException;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2018/7/30 下午11:34
 */
public class FutureTest {
    public static void main(String[] args) {
        final ResponseFuture responseFuture = new ResponseFuture(new ResponseCallback());
        new Thread(new Runnable() { // 请求线程
            @Override public void run() {
                System.out.println("发送一个同步请求");
                try {
                    System.out.println(responseFuture.get());  // 放开这句，就是同步
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("接着处理其他事情，过一会 ResponseCallback 会打印结果");
            }
        }).start();
        new Thread(new Runnable() {   // 处理线程
            @Override public void run() {
                try {
                    Thread.sleep(10000);
                    responseFuture.done("ok"); // 处理完成
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
