package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * 当线程执行wait()时，会把当前的锁释放，然后让出CPU，进入等待状态。
 * 当执行notify/notifyAll方法时，会唤醒一个处于等待该 对象锁 的线程，然后继续往下执行，
 * 直到执行完退出对象锁锁住的区域（synchronized修饰的代码块）后再释放锁。
 * @date 2017/7/26 23:00
 */
public class NotifyTest {

    public static void main(String[] args) {

        final Object obj = new Object();
        final Object obj1 = new Object();
        Thread t1 = new Thread() {
            public void run() {
                synchronized (obj) {
                    try {
                        obj.wait();
                        System.out.println("Thread 1 wake up.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t1.start();
        try {
            Thread.sleep(1000); // assume thread 1 must start up before than thread 2
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2 = new Thread() {
            public void run () {
                synchronized (obj) {
//                    obj.notifyAll();
                    obj.notify();
                    System.out.println("Thread 2 send notifyAll!");
                }
            }
        };
        t2.start();

    }

}
