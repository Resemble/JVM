package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * @date 2017/8/29 8:55
 */
public class NumJudge {
    public static void main(String[] args) {
        int[] testArray = {1, 2, 3, 4};
        for (int i = 0; i < testArray.length; i++) {
//            System.out.println(numberOf1(testArray[i]));
            System.out.println(numberOf1Two(testArray[i]));
        }

    }

    public static int numberOf1Two(int n) {
        int count = 0;
        while(n>0) {
            count++;
//            n &= (n -1);  // 三种都行
            n &= n -1;
//            n = (n -1) & n;
        }
        return count;
    }

    public static int numberOf1(int n) {  // n = 0 会有死循环
        int count = 0;
        while(n>0) {
            if((n & 1) == 1)
                count++;
            n >>= 1;
        }
        return count;
    }
}
