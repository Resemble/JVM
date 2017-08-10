package Java;

import java.lang.reflect.*;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package Java
 * @Description: 反射例子
 * @date 2017/8/10 17:34
 */
class B {
    public int b;

    public B() {
    }
}

interface IA {
}

class A extends B implements IA {
    public A() {
    }

    public A(String str) {
    }

    public A(String str1, String str2) {

    }

    private String str;
    public int age;

    public int func1(String name) {
        System.out.println("hello " + name);
        return 8;
    }

    public void func1(String name1, String name2) {
        System.out.println("hello " + name1 + "," + name2);
    }
}


public class ReflectDemo {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        // 根据字符串获取类
        Class<?> demoClass = Class.forName("Java.A");

        // 获取类的完整名字
        System.out.println("类名：" + demoClass.getName());

        System.out.println("类加载器：" + demoClass.getClassLoader().getClass().getName());

        // 根据 Class 的共有无参构造方法创建一个实例
        A newAObj = (A) demoClass.newInstance();

        // 获取类中声明的属性
        Field[] publicFileds = demoClass.getFields();  // 获取当前类和父类的 public 类型的所有属性返回 public 属性 age
        Field[] declareFileds = demoClass.getDeclaredFields();   // 获取当前类(不包含父类)声明的所有属性，包括 private 和 public，返回: str age
        Field specifyField = demoClass.getField("age");
        specifyField.setAccessible(true);

        // 修改属性
        specifyField.set(newAObj, 99);

        // 获得类方法
        Method[] publicMethods = demoClass.getMethods();  // 同上
        Method[] declareMethods = demoClass.getDeclaredMethods();
        Method specifyMethod = demoClass.getDeclaredMethod("func1", new Class<?>[]{java.lang.String.class}); // 根据方法名和方法参数类型指定获取一个方法
        // 反射调用对象的方法
        specifyMethod.invoke(newAObj, "hans");

        // 获取构造函数
        Constructor<?>[] publicConstructors = demoClass.getConstructors();
        Constructor<?>[] declareConstructors = demoClass.getDeclaredConstructors();
        Constructor<?> constructor = demoClass.getConstructor(new Class<?>[]{java.lang.String.class}); // 根据指定类型获取构造方法
        A newAObj2 = (A) constructor.newInstance("hello2"); // 根据指定构造函数创建实例

        // 获得实现的接口
        Class<?>[] interfaces = demoClass.getInterfaces();

        // 获得继承的父类
        Class<?> superclass = demoClass.getSuperclass();
        getMethodDetail(specifyMethod);
    }

    // 反射获得一个方法的明确定义
    private static void getMethodDetail(Method method) {
        String methodModifier = Modifier.toString(method.getModifiers());  // 方法修饰符
        String returnType = method.getReturnType().getName(); // 方法返回值
        Class<?>[] parameterTypes = method.getParameterTypes();
        System.out.println(methodModifier + " " + returnType + " " + method.getName() + "(");
        int i = 1;
        for (Class<?> parameterType : parameterTypes) {
            System.out.println(parameterType.getName() + " arg" + (i++));
            if ( i <= parameterTypes.length) {
                System.out.print(",");
            }
        }
        System.out.println(") {}");
    }


}
