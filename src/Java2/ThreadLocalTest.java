package Java2;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Java2
 * @Description:
 * @date 2018/11/5 7:58 PM
 */
public class ThreadLocalTest {

    private static People people = new People();

    private static ThreadLocal<People> threadLocal = new ThreadLocal<People>() {
        @Override public People initialValue() {
            return people;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        threadLocal.get().age = 5;
        Thread thread1 = new Thread(new Runnable() {
            @Override public void run() {
                //                threadLocal.get().age = 5;
                System.out.println(
                    Thread.currentThread().getName() + "thread1 people = " + threadLocal.get()
                        + " age = " + threadLocal.get().age);
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //                threadLocal.set(new People());
                System.out.println(
                    Thread.currentThread().getName() + "thread1 people = " + threadLocal.get() + " age = " + threadLocal.get().age);
            }

        });
        thread1.start();

        Thread.sleep(5);
        System.out.println(
            Thread.currentThread().getName() + "Main people = " + threadLocal.get() + " age = "
                + threadLocal.get().age);
        threadLocal.set(new People());

        Thread thread2 = new Thread(new Runnable() {
            @Override public void run() {
                System.out.println(
                    Thread.currentThread().getName() + "thread2 people = " + threadLocal.get()
                        + " age = " + threadLocal.get().age);
            }
        });
        thread2.start();

    }

}


class People {
    public int age;
}
