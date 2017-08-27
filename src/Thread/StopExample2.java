package Thread;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2017/8/26 10:33
 */
public class StopExample1 extends Thread{


    public static void main(String[] args) {
        StopExample1 thread = new StopExample1();
        System.out.println("Start thread ...");
        thread.start();
//        thread.run();
        thread.stop();
        System.out.println("Stop application");
    }

    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("this thread is running...");
    }

}
