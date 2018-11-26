package OOM;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package OOM
 * @Description:
 * @date 2018/11/20 11:26 PM
 */



public class DemoApplication {

    public static void main(String[] args) {
        Thread thread = new Thread(new NewTask());
        thread.run();
    }
}



class NewTask implements Runnable {

    @Override public void run() {
        while (true) {
            System.out.println("The thread name is: " + Thread.currentThread().getName());
        }
    }
}
