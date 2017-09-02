package Algorithm;


/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Sort
 * @Description: 最大堆排序自己实践  参考 http://www.cnblogs.com/MOBIN/p/5374217.html
 * @date 2017/8/9 18:25
 */
public class HeapSortTest2 {

    public static void myMaxHeapSort(int[] array) {
        int length = array.length;
        // 构建堆
        for (int i = length / 2 - 1; i >= 0 ; i--) {              // 比如 0 1 2 3 4 5 6 7 8，这9个数 9 / 2 - 1 = 3，只有 0 1 2 3 才有子节点
            adjustMaxHeapSort(array, i, length-1);
        }
        // 交换堆顶元素和最后一个元素，最后一个元素保持不变输出
        for (int i = length - 1; i >= 0; i--) {
            swap(array, 0, i); // 尾元素最大
            adjustMaxHeapSort(array, 0, i-1); // 再次调整堆
        }

    }

    public static void adjustMaxHeapSort(int[] array, int i, int length) {
        int left, right, j;

        while((left = 2 * i + 1) <= length) {
            right = left + 1;
            j = left;
            if (j < length && array[left] < array[right]) {
                j++; // 右节点大，指针指向右节点
            }
            if (array[i] < array[j]) {
                swap(array, i, j);
            } else {
                break;
            }
            i = j;
        }

    }

    public static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void main(String[] args) {
        long startTime;
        long endTime;
        startTime = System.nanoTime();
        int array[] = {3, 34, 43, 2, 543, 23, 13, 324, 32, 53332};
        myMaxHeapSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
        endTime = System.nanoTime();
        System.out.println(String.format("this take %s ns", (endTime - startTime)));
    }

}
