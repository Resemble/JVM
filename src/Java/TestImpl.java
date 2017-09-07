package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package First
 * @Description:
 * @date 2017/9/7 20:44
 */
public class TestImpl implements TestInterface, TestInterface2{

    final int finInt;

    public TestImpl() {
        finInt = 4;
    }

    @Override
    public void testIn() {
        System.out.println(3333333);
    }

    @Override
    public void TestInterface() {

    }

    
    public static void main(String[] args) {
        TestImpl test = new TestImpl();
        test.testIn();
    }
}
