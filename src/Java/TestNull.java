package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/9/9 8:56
 */
public class TestNull {

    public static void main(String[] args) {
        Object object = null;
        print(object);
    }

    public static void print(Object object) {
//        object.notifyAll();
        System.out.println("Object");
    }

    public static void print(String string) {
        System.out.println("String");
    }

}
