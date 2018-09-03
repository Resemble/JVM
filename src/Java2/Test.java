package Java2;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Java2
 * @Description:
 * @date 2018/7/5 下午3:14
 */
public class Test {

    static class Person {
        public int age;
    }

    public static void method(Person p) {
        p = new Person();
        p.age = 20;
    }



    public static void main(String[] args) {
        //        Person p = new Person();
        //        p.age = 10;
        //        method(p);
        //        System.out.println(p.age);
        System.out.println((16 + (1024 * 256 * 4) + 64 * 100000 + 48.0) / (1024 * 1024));
        System.out.println((16 + (1024 * 1024 * 4) + 64 * 500000 + 48.0) / (1024 * 1024));
        System.out.println((16 + (1024 * 1024 * 2 * 4) + 64 * 1000000 + 48.0) / (1024 * 1024));
        System.out.println((16 + (1024 * 1024 * 2 * 4) + 64 * 1000000 + 48));
        System.out.println((16 + (1024 * 1024 * 2 * 4 * 2) + 64 * 2000000 + 48) / (1024 * 1024));

        System.out.println((100000 * (32 + 24 + 16) + 1024 * 128 * 4 * 2 + 16.0) / (1024 * 1024));
        System.out.println((1000000 * (32 + 24 + 16) + 1024 * 1024 * 4 * 2 + 16.0) / (1024 * 1024));
        System.out.println((1000000 * (32 + 16 + 16) + 1024 * 1024 * 4 * 2 + 16.0) / (1024 * 1024));
        System.out.println((500000 * (32 + 24 + 16) + 1024 * 1024 * 4 + 16.0) / (1024 * 1024));
        System.out.println(255735080.0 / (1024 * 1024));
        System.out.println(1024 * 256);  // 10 w
        String string = "dsfsd";
        string.hashCode();
        System.out.println(0.04+0.01);
        System.out.println(0.05+0.01);
        System.out.println(0.06+0.01);
        System.out.println(String.valueOf((0.06+0.01)));
        double result = 0.06+0.01;
        System.out.println(result);
        System.out.println(0.07+0.01);
    }


}
