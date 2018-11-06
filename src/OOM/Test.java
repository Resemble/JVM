package OOM;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package OOM
 * @Description:
 * @date 2018/10/30 10:31 PM
 */
public class Test {
    public static void main(String[] args) {
        StringBuffer a = new StringBuffer("A");
        StringBuffer b = new StringBuffer("B");
        System.out.println("a:" + a.hashCode());
        System.out.println("b:" + b.hashCode());
        operator(a, b);
        System.out.println(a + "," + b);
    }

    public static void operator(StringBuffer x, StringBuffer y) {
        x.append(y);
        // 这是一个对象
        y = x;
        System.out.println("x:" + x.hashCode());
        System.out.println("y:" + y.hashCode());
    }
}
