package Thread;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2017/8/26 10:33
 */
public class StopExample2 extends Thread {


    public static void main(String[] args) throws Exception {
        StopExample2 stopExample2 = new StopExample2();
        stopExample2.start();
        stopExample2.interrupt();
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
