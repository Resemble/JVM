package Java;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description: 写时复制数组解决并发问题
 * @date 2017/7/29 9:52
 */
public class CopyOnWriteListDemo {

    private static final int THREAD_POOL_MAX_NUM = 10;
//    private List<String> mList = new ArrayList<String>();
    private List<String> mList = new CopyOnWriteArrayList<String>();

    public static void main(String[] args) {
        new CopyOnWriteListDemo().start();
    }

    void initData() {
        for (int i = 0; i < THREAD_POOL_MAX_NUM; i++) {
            this.mList.add(".... Line" + (i + 1) + "....");
        }
    }

    private void start() {
        initData();
//        Executors.newScheduledThreadPool()
        ExecutorService service = Executors.newFixedThreadPool(THREAD_POOL_MAX_NUM);
        for (int i = 0; i < THREAD_POOL_MAX_NUM; i++) {
            service.execute(new ListReader(this.mList));
//            service.submit(new ListReader(this.mList));
            service.execute(new ListWriter(this.mList, i));
//            service.submit(new ListWriter(this.mList, i));
        }
        service.shutdown();
    }


    private class ListReader implements Runnable {

        private List<String> mList;

        public ListReader(java.util.List<String> list) {
            this.mList = list;
        }

        @Override
        public void run() {
            if (this.mList != null) {
                for (String str : mList)
                    System.out.println(Thread.currentThread().getName() + ": " + str);
            }
        }

    }

    private class ListWriter implements Runnable {

        private List<String> mList;
        private int mIndex;

        public ListWriter(List<String> list, int index) {
            this.mList = list;
            this.mIndex = index;
        }


        @Override
        public void run() {
            if (this.mList != null) {
                this.mList.add("... add " + mIndex + "...");
            }
        }
    }

}
