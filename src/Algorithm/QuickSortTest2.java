package Algorithm;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Sort
 * @Description:
 * @date 2017/7/30 20:20
 */
public class QuickSortTest2 {

    public static void sort(int array[], int low, int high) {
        if (low > high) {
            return;
        }
        int i = low;
        int j = high;
        int temp = array[i];
        while (i < j) {  // while 循环保证一个数一直填坑
            while (i < j && array[j] >= temp) {
                j--;
            }
            array[i] = array[j];
            while (i < j && array[i] < temp) {
                i++;
            }
            array[j] = array[i];
        }
        array[j] = temp;  // i j 都可以，此时 i j 相等
        sort(array, low, j - 1);
        sort(array, j + 1, high);
    }

    public static void quickSort(int array[]) {
        sort(array, 0, array.length - 1);
    }

    public static void main(String[] args) {
        long startTime;
        long endTime;
        startTime = System.nanoTime();
        int array[] = {3, 34, 43, 2, 543, 23, 13, 324};
//        int array[] = getRandomArray(2300);
        quickSort(array);
        System.out.println("length:" + array.length);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        endTime = System.nanoTime();
        System.out.println(String.format("this take %s ns", (endTime - startTime)));
    }



    public static int[] getRandomArray(int length) {
        int array[] = new int[length];
        for (int i = 0; i < length; i++) {
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            array[i] = threadLocalRandom.nextInt(5000);
        }
        return array;
    }

}
