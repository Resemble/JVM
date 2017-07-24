package OOM;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package OOM
 * @Description: java 堆内存溢出异常测试
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * @date 2017/7/24 18:33
 */
public class HeapOOM {

    static class OOMObject {

    }

    public static void main (String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();
        while (true) {
            try {
                TimeUnit.NANOSECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new OOMObject());
        }
    }


}
