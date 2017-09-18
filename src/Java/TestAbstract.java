package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package First
 * @Description:
 * @date 2017/8/30 19:24
 */
public abstract class TestAbstract {
    int i = 0;
    public void TestAbstract() {
        System.out.println("fdsf");
    }
    public abstract void testAbs();

    public static void main(String[] args) {
        TestAbstract child = new TestAbstract() {
            @Override
            public void testAbs() {
                System.out.println("2343");
            }
        };
    }
}
