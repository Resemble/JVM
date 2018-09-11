package Singleton;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Singleton
 * @Description: 懒汉模式
 * @date 2018/9/11 下午11:21
 */
public class SingletonExample1 {

    // 私有构造函数
    private  SingletonExample1() {

    }

    // 单例对象
    private static SingletonExample1 instance = null;

    // 静态的工厂方法
    public static  SingletonExample1 getInstance() {
        if (instance == null) {  // 多线程这里可能出问题
            instance = new SingletonExample1();
        }
        return instance;
    }

}
