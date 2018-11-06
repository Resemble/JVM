package Java2;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Java2
 * @Description:
 * @date 2018/11/5 7:58 PM
 */
public class ThreadLocalTest2 {

    private static Integer integer = new Integer(3);

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override public Integer initialValue(){
            return integer;
        }
    };

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1 integer = " + threadLocal.get());
                threadLocal.set(5);
                System.out.println("thread1 integer = " + threadLocal.get());
            }

        });
        thread1.start();

        Thread.sleep(15);
        System.out.println("Main integer = " + threadLocal.get());


        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2 integer = " + threadLocal.get());
            }
        });
       thread2.start();

       Thread.sleep(5);
       threadLocal.set(7);
       thread2.run();
    }

}



