package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/9/9 17:25
 */
public class TestSuperThis {
    int x;

    public TestSuperThis(int x) {
        this.x = x;
        System.out.println("1111111111");
    }

    public TestSuperThis() {
        System.out.println("22222222222");
    }
}

class TestChild extends TestSuperThis {
    public TestChild() {
//        super();
        this(1);
//        this(2);
//        super();
    }
    public TestChild(int x) {
        System.out.println("3333333");
    }
}