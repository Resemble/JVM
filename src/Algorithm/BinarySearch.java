package First;

import java.util.Scanner;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package First
 * @Description: 二分查找非递归实现
 * @date 2017/9/2 16:02
 */
public class BinarySearch {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int target = sc.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = sc.nextInt();
        }

        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[mid] == target) {
                System.out.println(mid);
                return;
            } else if (array[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }

        }
        System.out.println(-1);
    }


}
