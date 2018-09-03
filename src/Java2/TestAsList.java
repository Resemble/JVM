package Java2;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java2
 * @Description:
 * @date 2017/9/11 11:10
 */
public class TestAsList {


    public void arrayListTest() {
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(0, 1, 5, 5, 5, 6));
        arrayList.get(3);
        arrayList.add(22);
        arrayList.size();
//        System.out.println(arrayList.indexOf(5));
//        System.out.println(arrayList.lastIndexOf(5));
//        System.out.println(arrayList);
//        System.out.println(arrayList.toArray()[2]);

        int[] array = {0, 1, 2, 3, 4, 5, 6};
        int[] array1 = {10, 11, 12, 13, 14, 15, 16};
        System.arraycopy(array, 3, array1, 5, 2);
        for (int i = 0; i < array1.length; i++) {
            System.out.println(array1[i]);
        }
        arrayList.remove(0);
        Integer integer = 5;
        arrayList.remove(integer);
        System.out.println(arrayList);
    }

    public void testIntegerEquals() {
        Integer integer1 = 5;
        Integer integer2 = 5;
        if(integer1.equals(integer2)) {
            System.out.println("integer1.equals(integer2)");
        }
        if(integer1.equals(5)) {
            System.out.println("integer1.equals(5)");
        }
    }

    public static void main(String[] args) {
//        int[] array = {1, 2, 2, 5, 3};
//        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
//        ArrayList<Integer> arrayList = new ArrayList<>(list);
//        List<Integer> tempList = arrayList.subList(0, 3);
//
//        Stream stream = tempList.stream();
//        stream.forEach(p -> System.out.println(p));
        TestAsList testAsList = new TestAsList();
        testAsList.arrayListTest();
        testAsList.testIntegerEquals();

    }


}
