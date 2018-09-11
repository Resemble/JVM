package Singleton;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Singleton
 * @Description: 枚举 线程安全
 * @date 2018/9/11 下午11:21
 */
public class SingletonExample7 {


    // 私有构造函数
    private SingletonExample7() {

    }


    // 静态的工厂方法
    public static SingletonExample7 getInstance() {
        return Singleton.INSTANCE.getSingleton();
    }


    private enum Singleton {

        INSTANCE;
        private SingletonExample7 singleton;

        // JVM 保证这个方法绝对只调用一次
        Singleton() {
            singleton = new SingletonExample7();
        }

        public SingletonExample7 getSingleton() {
            return singleton;
        }
    }

}
