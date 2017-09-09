package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/9/9 8:58
 */
public class TestNull2 {

    static class TestNullClass {
        int i;
    }

    public static void main(String[] args) {
        TestNullClass testNullClass = new TestNullClass();
        testNullClass.i = 2;
        test(testNullClass);
        test(testNullClass);
        System.out.println(testNullClass.i);

    }

    public static void test(final TestNullClass testNullClass) {
        testNullClass.i++;
    }

}
