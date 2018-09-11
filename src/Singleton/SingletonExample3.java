package Singleton;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Singleton
 * @Description: 懒汉模式
 * @date 2018/9/11 下午11:21
 */
public class SingletonExample3 {

    // 私有构造函数
    private SingletonExample3() {

    }

    // 单例对象
    private static SingletonExample3 instance = null;

    // 静态的工厂方法
    public static synchronized SingletonExample3 getInstance() {   // 加锁线程安全
        if (instance == null) {
            instance = new SingletonExample3();
        }
        return instance;
    }

}
