package gc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package gc
 * @Description:
 * @date 2018/11/9 10:29 PM
 */
public class TestGC {

    private static class BigObject {
        byte[] bigBytes = new byte[1024 * 1024];
    }

    public static void main(String[] args) {
        keepInMemory();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
    }

    private static void keepInMemory () {
        List<BigObject> memList = new ArrayList<BigObject>();
        for (int i = 0; i < 55; i++) {
            memList.add(new BigObject());
        }
    }

}
