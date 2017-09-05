package Thread;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description: 死锁测试
 * jstack -l pid 显示
 * Found one Java-level deadlock:
    =============================
    "t3":
    waiting to lock monitor 0x000000001a18e608 (object 0x00000000d5f8c208, a java.lang.Object),
    which is held by "t1"
    "t1":
    waiting to lock monitor 0x00000000173fdb98 (object 0x00000000d5f8c218, a java.lang.Object),
    which is held by "t2"
    "t2":
    waiting to lock monitor 0x000000001a18b958 (object 0x00000000d5f8c228, a java.lang.Object),
    which is held by "t3"

    Java stack information for the threads listed above:
    ===================================================
    Found 1 deadlock.
 * @date 2017/9/5 10:08
 */
public class ThreadDeadlock {

    public static void main(String[] args) throws InterruptedException {
        Object obj1 = new Object();
        Object obj2 = new Object();
        Object obj3 = new Object();
        Thread thread1 = new Thread(new SyncThread(obj1, obj2), "t1");
        Thread thread2 = new Thread(new SyncThread(obj2, obj3), "t2");
        Thread thread3 = new Thread(new SyncThread(obj3, obj1), "t3");
        thread1.start();
        Thread.sleep(5000);
        thread2.start();
        Thread.sleep(5000);
        thread3.start();
    }

    static class SyncThread implements Runnable {
        private Object obj1;
        private Object obj2;

        public SyncThread(Object obj1, Object obj2) {
            this.obj1 = obj1;
            this.obj2 = obj2;
        }


        @Override
        public void run() {
            String name = Thread.currentThread().getName();
            System.out.println(name + " acquiring lock on " + obj1);
            synchronized (obj1) {
                System.out.println(name + " acquired lock on " + obj1);
                work();
                System.out.println(name + " acquiring lock on " + obj2);
                synchronized (obj2) {
                    System.out.println(name + " acquired lock on " + obj2);
                    work();
                }
                System.out.println(name + " released lock on " + obj2);
            }
            System.out.println(name + " released lock on " + obj1);
            System.out.println(name + " finished execution.");
        }
        private void work() {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
