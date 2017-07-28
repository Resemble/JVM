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

### wait（）方法
wait方法是`Object类`的方法，这意味着所有的Java类都可以调用该方法。sleep方法是`Thread类`的静态方法。
wait是在当前线程持有wait对象锁的情况下，`暂时放弃锁，并让出CPU资源`，并积极等待其它线程调用同一对象的notify或者
notifyAll方法。注意，即使只有一个线程在等待，并且有其它线程调用了notify或者notifyAll方法，等待的线程只是被激活，
但是`它必须得再次获得锁才能继续往下执行`。换言之，即使notify被调用，但只要锁没有被释放，原等待线程因为未获得锁仍然
无法继续执行。

### sleep（）方法
sleep()使当前线程进入停滞状态（阻塞当前线程），`让出CUP的使用`、目的是不让当前线程独自霸占该进程所获的CPU资源，以留一定时间给其他线程执行的机会;
sleep()是Thread类的Static(静态)的方法；因此他不能改变对象的机锁，所以当在一个Synchronized块中调用Sleep()方法是，线程虽然休眠了，
但是对象的机锁并木有被释放，其他线程无法访问这个对象（即使睡着也持有对象锁）。
`在sleep()休眠时间期满后，该线程不一定会立即执行`，这是因为其它线程可能正在运行而且没有被调度为放弃执行，除非此线程具有更高的优先级。 

#### 所以sleep()和wait()方法的最大区别是：
sleep()睡眠时，保持`对象锁`，仍然占有该锁；
而wait()睡眠时，释放对象锁。
但是wait()和sleep()都可以通过interrupt()方法打断线程的暂停状态，从而使线程立刻抛出InterruptedException（但不建议使用该方法）。

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


### Java NIO
NIO（Non-blocking I/O，在Java领域，也称为New I/O），是一种同步非阻塞的I/O模型，也是I/O多路复用的基础，已经被越来越多地应用到
大型应用服务器，成为解决高并发与大量连接、I/O处理问题的有效方式。
传统的`BIO`里面socket.read()，如果TCP RecvBuffer里没有数据，函数会一直`阻塞`，直到收到数据，返回读到的数据。

对于`NIO`，如果TCP RecvBuffer有数据，就`把数据从网卡读到内存`，并且返回给用户；反之则直接返回0，永远不会阻塞。

最新的`AIO`(Async I/O)里面会更进一步：`不但等待就绪是非阻塞的，就连数据从网卡到内存的过程也是异步的`。

换句话说，BIO里用户最关心“我要读”，NIO里用户最关心"我可以读了"，在AIO模型里用户更需要关注的是“读完了”。

NIO一个重要的特点是：socket主要的读、写、注册和接收函数，在等待就绪阶段都是非阻塞的，真正的I/O操作是同步阻塞的（消耗CPU但性能非常高）。
NIO的读写函数可以立刻返回，这就给了我们不开线程利用CPU的最好机会：如果一个连接不能读写（socket.read()返回0或者socket.write()返回0），
我们可以把这件事记下来，记录的方式通常是在`Selector`上注册标记位，然后`切换到其它就绪的连接（channel）`继续进行读写。

注意，select是阻塞的，无论是通过操作系统的通知（epoll）还是不停的`轮询`(select，poll)，这个函数是阻塞的。所以你可以放心大胆地在一个
while(true)里面调用这个函数而不用担心CPU空转。
#### NIO高级主题
#### Proactor与Reactor
一般情况下，I/O 复用机制需要事件分发器（event dispatcher）。 `事件分发器的作用，即将那些读写事件源分发给各读写事件的处理者`，就像
送快递的在楼下喊: 谁谁谁的快递到了， 快来拿吧！开发人员在开始的时候需要在分发器那里注册感兴趣的事件，并提供相应的处理者（event 
handler)，或者是回调函数；事件分发器在适当的时候，会将请求的事件分发给这些handler或者回调函数。

涉及到`事件分发器`的两种模式称为：`Reactor和Proactor`。 Reactor模式是基于`同步I/O`的，而Proactor模式是和`异步I/O`相关的。在Reactor模式中，
事件分发器等待某个事件或者可应用或个操作的状态发生（比如文件描述符可读写，或者是socket可读写），事件分发器就把这个事件传给事先注册的
事件处理函数或者回调函数，由后者来做实际的读写操作。

而在Proactor模式中，事件处理者（或者代由事件分发器发起）直接发起一个异步读写操作（相当于请求），而实际的工作是由操作系统来完成的。
发起时，需要提供的参数包括用于存放读到数据的缓存区、读的数据大小或用于存放外发数据的缓存区，以及这个请求完后的回调函数等信息。事件分
发器得知了这个请求，它默默等待这个请求的完成，然后转发完成事件给相应的事件处理者或者回调。举例来说，在Windows上事件处理者投递了一个
异步IO操作（称为overlapped技术），事件分发器等IO Complete事件完成。这种异步模式的典型实现是基于操作系统底层异步API的，所以我们可
称之为“系统级别”的或者“真正意义上”的异步，因为具体的读写是由操作系统代劳的。