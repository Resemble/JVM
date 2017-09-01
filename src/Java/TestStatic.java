package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/9/1 22:31
 */
public class TestStatic {

    static int result = 3;
    static int getResult = 2;

    public static int getInt() {
        int i = getResult;
        return result;
    }

}
