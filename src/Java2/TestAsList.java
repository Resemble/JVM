package Java2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java2
 * @Description:
 * @date 2017/9/11 11:10
 */
public class TestAsList {

    public static void main(String[] args) {
        int[] array = {1, 2, 2, 5, 3};
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6);
//        List<Integer> list = Arrays.asList(0, 1, 4, 2, 5, 6, 3, 2);
//        for (int value:list) {
//            System.out.println(value);
//        }
        ArrayList<Integer> arrayList = new ArrayList<>(list);
        List<Integer> tempList = arrayList.subList(0, 3);

        Stream stream = tempList.stream();
        stream.forEach(p -> System.out.println(p));
//        List<Integer> tempList2 = (List<Integer>) stream.limit(6).sorted((p1, p2) -> ((Integer)p1 - (Integer)p2)).collect(Collectors.toList());
//        for (int value : tempList2) {
//            System.out.println(value);
//        }


    }


}
