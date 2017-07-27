# Java 相关琐碎知识
### Java IO库的两个设计模式
装饰器模式：是在不必改变原类文件和使用继承的情况下，动态的扩展一个对象的功能
适配器模式：将一个类的接口转换成客户希望的另外一个接口
（1）装饰模式：装饰模式在Java语言中的最著名的应用莫过于java I/O标准库的设计了。
（2）适配器模式：适配器模式是Java I/O库中第二个最重要的设计模式。

### 排序
#### 时间复杂度
n^2表示n的平方,选择排序有时叫做直接选择排序或简单选择排序

排序方法	            平均时间	    最好时间	    最坏时间
桶排序(不稳定)	     O(n)	     O(n)	      O(n)
基数排序(稳定)	     O(n)	     O(n)	      O(n)
归并排序(稳定)	     O(nlogn)	 O(nlogn)	  O(nlogn)
快速排序(不稳定)	     O(nlogn)	 O(nlogn)	  O(n^2)
堆排序(不稳定)	     O(nlogn)	 O(nlogn)	  O(nlogn)
希尔排序(不稳定)	     O(n^1.25)	  	 
冒泡排序(稳定)	     O(n^2)	     O(n)	      O(n^2)
选择排序(不稳定)	     O(n^2)	     O(n^2)	      O(n^2)
直接插入排序(稳定)	 O(n^2)	     O(n)	      O(n^2)

#### 空间复杂度
冒泡排序,简单选择排序,堆排序,直接插入排序,希尔排序的空间复杂度为O(1),因为需要一个临时变量来交换元素位置,
(另外遍历序列时自然少不了用一个变量来做索引)
快速排序空间复杂度为logn(因为递归调用了) ,归并排序空间复杂是O(n),需要一个大小为n的临时数组.
基数排序的空间复杂是O(n),桶排序的空间复杂度不确定
 
最快的排序算法是桶排序
所有排序算法中最快的应该是桶排序(很多人误以为是快速排序,实际上不是.不过实际应用中快速排序用的多)但桶排序一般用的不多,因为有几个比较大的缺陷.
1.待排序的元素不能是负数,小数.
2.空间复杂度不确定,要看待排序元素中最大值是多少.
所需要的辅助数组大小即为最大元素的值.


### java I/O库具有两个对称性
（1）输入-输出对称：比如`InputStream 和OutputStream` 各自占据`Byte流`的输入和输出的两个平行的等级结构的根部；
而`Reader和Writer`各自占据`Char流`的输入和输出的两个平行的等级结构的根部。
（2）byte-char对称：InputStream和Reader的子类分别负责byte和Char流的输入；OutputStream和Writer的子类分别负责byte和Char流的输出
可以看出，这个类接受一个类型为inputStream的`System.in对象(字节流)`，将之`适配`成Reader类型(`字符流`)，然后再使用 
BufferedReader类“`装饰`”它，将缓冲功能加上去。这样一来，就可以使用BufferedReader对象的readerLine() 
方法读入整行的输入数据，数据类型是String。 
```java
public class IO {

    public static void main(String[] args) {
        String line;
        InputStreamReader input = new InputStreamReader(System.in);
        System.out.println("Enter data and push enter:");
        BufferedReader reader = new BufferedReader(input);
        try {
            line  = reader.readLine();
            System.out.println("Data entered: " + line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

```

### 多线程 一个 wait 一个 notifyAll
当线程执行wait()时，会把当前的锁释放，然后让出CPU，进入等待状态。
当执行notify/notifyAll方法时，会唤醒一个处于等待该 对象锁 的线程，然后继续往下执行，
`直到执行完退出对象锁锁住的区域（synchronized修饰的代码块）后再释放锁`。


wait方法是`Object类`的方法，这意味着所有的Java类都可以调用该方法。sleep方法是`Thread类`的静态方法。
wait是在当前线程持有wait对象锁的情况下，`暂时放弃锁，并让出CPU资源`，并积极等待其它线程调用同一对象的notify或者
notifyAll方法。注意，即使只有一个线程在等待，并且有其它线程调用了notify或者notifyAll方法，等待的线程只是被激活，
但是`它必须得再次获得锁才能继续往下执行`。换言之，即使notify被调用，但只要锁没有被释放，原等待线程因为未获得锁仍然
无法继续执行。

## Java 锁
### 重入锁
Java中的重入锁（即ReentrantLock）与Java内置锁一样，是一种排它锁。`使用synchronized的地方一定可以用ReentrantLock代替。`

`重入锁需要显示请求获取锁，并显示释放锁`。为了避免获得锁后，没有释放锁，而造成其它线程无法获得锁而造成死锁，一般建议将释放锁操作
放在finally块里。
如果重入锁已经被其它线程持有，则当前线程的lock操作会被阻塞。除了lock()方法之外，重入锁（或者说锁接口）还提供了其它获取锁的
方法以实现不同的效果。
lockInterruptibly() 该方法尝试获取锁，若获取成功立即返回；若获取不成功则阻塞等待。
tryLock() 该方法试图获取锁，若该锁当前可用，则该方法立即获得锁并立即返回true；若锁当前不可用，则立即返回false。该方法不会阻塞，并提供给用户对于成功获利锁与获取锁失败进行不同操作的可能性。
tryLock(long time, TimeUnit unit) 该方法试图获得锁，若该锁当前可用，则立即获得锁并立即返回true。
若锁当前不可用，则等待相应的时间（由该方法的两个参数决定）：1）若该时间内锁可用，则获得锁，并返回true；
2）若等待期间当前线程被打断，则抛出InterruptedException；
3）若等待时间结束仍未获得锁，则返回false。

### 读写锁（ReadWriteLock）
对于读多写少的场景，一个读操作无须阻塞其它读操作，只需要保证读和写或者写与写不同时发生即可。此时，如果使用重入锁（即排它锁），
对性能影响较大。Java中的读写锁（ReadWriteLock）就是为这种读多写少的场景而创造的。

实际上，ReadWriteLock接口并非继承自Lock接口，ReentrantReadWriteLock也只实现了ReadWriteLock接口而未实现Lock接口。
ReadLock和WriteLock，是ReentrantReadWriteLock类的静态内部类，它们实现了Lock接口。

### 条件锁
条件锁只是一个帮助用户理解的概念，实际上并没有条件锁这种锁。对于每个重入锁，都可以通过newCondition()方法绑定若干个条件对象。
