package Java;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/8/14 15:15
 */
public class Test {

    public static void main(String[] args) {
        String string = "0123456789";
        System.out.println(string.substring(5));
        System.out.println(string.substring(5, 7));

//        System.out.println("\\\"");
//        System.out.println("\\'");
//        System.out.println("\"");
//        System.out.println("'");
//        System.out.println("/'");
//
//        failFastTest();
//        stringTest();
        String[] strings = {"fd", "fd", "fds"};
        String strings1[] = {"fd", "fd", "fds"};

        System.out.println(Math.round(4.4));
        System.out.println(Math.round(4.5));
        System.out.println(Math.round(-4.4));
        System.out.println(Math.round(-4.5));
        System.out.println(Math.round(-4.6));

        Long l1 = 1L;
        Long l2 = new Long(1L);
        System.out.println(l1 == l2);
        System.out.println(l1.equals(l2));
    }


    public static void failFastTest() {
        Vector<Integer> vector = new Vector<>();
        vector.add(0);
        vector.add(1);
        vector.add(2);
        vector.add(3);
        vector.add(4);
        Iterator<Integer> integerIterator = vector.iterator();
        int j = 0;
        while (integerIterator.hasNext()) {
            System.out.println(integerIterator.next());
            if (j == 3) {
                vector.remove(0);
                integerIterator.remove();
            }
            j++;
        }
//        Iterator<Integer> integerIterator2 = vector.iterator();
//        while (integerIterator2.hasNext()) {
//            System.out.println(integerIterator2.next());
//        }
    }

    public static void stringTest() {
        String s = "Hello";
        s = "Java";
        String s1 = "Hello";
        String s2 = new String("Hello");
        String s3 = new String("Hello");
        if (s == s1) {
            System.out.println("s == s1");
        } else {
            System.out.println("s != s1");
        }
        if (s.equals(s1)) {
            System.out.println("s.equals(s1)");
        } else {
            System.out.println("!s.equals(s1)");
        }
        if (s2 == s1) {
            System.out.println("s2 == s1");
        } else {
            System.out.println("s2 != s1");
        }
        if (s2.equals(s1)) {
            System.out.println("s2.equals(s1)");
        } else {
            System.out.println("!s2.equals(s1)");
        }
        if (s2 == s3) {
            System.out.println("s2 == s3");
        } else {
            System.out.println("s2 != s3");
        }
        if (s2.equals(s3)) {
            System.out.println("s2.equals(s3)");
        } else {
            System.out.println("!s2.equals(s3)");
        }
    }

}
