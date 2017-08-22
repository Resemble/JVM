package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description:
 * ceil()：将小数部分一律向整数部分进位。
    floor()：一律舍去，仅保留整数。
    round()：进行四舍五入
 * @date 2017/8/22 10:21
 */
public class MathTest {

    public static void main(String[] args) {
        System.out.println(Math.round(4.5));
        System.out.println(Math.round(-4.5));
        System.out.println(Math.floor(4.5));
        System.out.println(Math.ceil(4.5));
        System.out.println(1/3*3);
    }

}
