package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/7/26 22:58
 */
class Base {
    public Base(String s) {
        System.out.println("B");
    }

    public static void testStatic() {
        System.out.println("Base static!");
    }
}
public class Derived extends Base{
    public Derived(String s) {
        super(s);
        System.out.println("D");
    }
    public static void testStatic() {
        System.out.println("Derived static!");
    }

    public static void main(String[] args) {
        new Derived("C");
    }

}
