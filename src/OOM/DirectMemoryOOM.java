package OOM;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package OOM
 * @Description:
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 * @date 2017/7/31 10:54
 */
public class DirectMemoryOOM {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws  Exception{
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
//        Unsafe.class.getFields();
        unsafeField.setAccessible(true);
        Unsafe unsafe = null;
        try {
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.println("----");
            unsafe.allocateMemory(_1MB);
        }
    }


}
