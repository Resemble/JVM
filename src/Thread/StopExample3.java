package Thread;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2017/8/27 16:25
 */
public class StopExample3 extends Thread{

    public static void main(String[] args) throws Exception {
        StopExample3 stopExample3 = new StopExample3();
        stopExample3.start();
        stopExample3.interrupt();
    }

    @Override
    public void run() {
        System.out.println("start run ------------");
        int i = 0;
        while (true) {

            if (Thread.interrupted()) {
                System.out.println("true ******** ");
            } else {
//                System.out.println("false ****** ");
            }
//            try {
//                Thread.sleep(2000);
//                System.out.println("wake up");
//            } catch (InterruptedException e) {
//                System.out.println("catch interrupted exception");
//                return;
//            }
        }
    }


}
