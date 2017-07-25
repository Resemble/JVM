package Java;

import java.util.Vector;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package
 * @Description: 多态测试
 * @date 2017/7/24 21:58
 */
public class PolymorphismTest {

//    public static void main(String[] args) {
    public static void main(String args[]) {
        PolymorphismTest polymorphismTest = new PolymorphismTest();
        int testInt = polymorphismTest.testOverload(3, 2);
        System.out.println(testInt);

        float testFloat = polymorphismTest.testOverload(3.0f, 2);
        System.out.println(testFloat);

        polymorphismTest.vectorTest();
    }

    public int testOverload(int aint, int bint) {
        return aint - bint;
    }

    public float testOverload(float afloat, int bint) {
        return bint;
    }


    public void vectorTest() {
        Vector<String> vector = new Vector<>();
        vector.add("3");
        vector.add("4");
        vector.add("5");
        for (String string:vector) {
            System.out.println(string);
        }
        for (int i = 0; i < vector.size(); i++) {
            System.out.println(vector.get(i));
        }
    }

}
