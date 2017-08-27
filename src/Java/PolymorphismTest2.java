package Java;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description: 多态测试
 * @date 2017/8/27 16:33
 */

class Father {
    public void func1() {
        func2();
    }

    public void func2() {
        System.out.println("AAAA");
    }
}

class Child extends Father {

    public void func1() {
        System.out.println("BBB 00000");
    }
    public void func1(int i) {
        System.out.println("BBB 111111");
    }
    public void func2() {
        System.out.println("CCC");
    }
    public void func3() {
        System.out.println("DDDD");
    }
}


class AntherTest {
    public int func1() {
        return 2;
    }
    public int func1(int i) {
        return 2;
    }
    public float func1(int i, int j) {
        return 2f;
    }
}

public class PolymorphismTest2 {

    public static void main(String[] args) {
        // 子类和父类方法名相同，必须有相同的返回类型
        Father child = new Child();
        child.func1(); // 子类没有func1就调用父类
//        child.func3(); // 不能调用，父类没有
        // 子类可以覆盖父类方法，参数不同，但是返回值一定要相同
    }

}
