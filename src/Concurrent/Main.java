package Concurrent;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package First
 * @Description:
 * @date 2017/8/30 19:21
 */
public class Main {
    public static synchronized void main(String[] args)throws InterruptedException {
        Thread t = new Thread(){
            public void run(){
                Right();
            }
        };
        t.start();
        System.out.print("Left");
    }
    static synchronized void Right(){
        System.out.print("Right");
    }
}
