package Singleton;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Singleton
 * @Description: 懒汉模式
 * @date 2018/9/11 下午11:21
 */
public class SingletonExample4 {

    // 私有构造函数
    private SingletonExample4() {

    }

    // 1、memory = allocate() 分配对象的内存空间
    // 2、ctorInstance() 初始化对象
    // 3、instance = memory 设置 instace 指向刚分配的内存

    // jvm 和 cpu 优化，发生了指令重排

    // 1、memory = allocate() 分配对象的内存空间
    // 3、instance = memory 设置 instace 指向刚分配的内存
    // 2、ctorInstance() 初始化对象


    // 单例对象
    private static SingletonExample4 instance = null;

    // 静态的工厂方法
    public static  SingletonExample4 getInstance() {
        if (instance == null) {   //  双重检测机制
            synchronized (SingletonExample4.class) {  // B  看到有值，跳过到下面 return instance
                if (instance == null) {
                    instance = new SingletonExample4();   // A - 3 , A 还有2 初始化对象
                }
            }
        }
        return instance;
    }

}
