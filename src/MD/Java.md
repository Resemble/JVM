# Java 相关琐碎知识
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

### Java IO库的两个设计模式
装饰器模式：是在不必改变原类文件和使用继承的情况下，动态的扩展一个对象的功能
适配器模式：将一个类的接口转换成客户希望的另外一个接口
（1）装饰模式：装饰模式在Java语言中的最著名的应用莫过于java I/O标准库的设计了。
（2）适配器模式：适配器模式是Java I/O库中第二个最重要的设计模式。
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

#### 被wait的线程，想要继续运行的话，它必须满足2个条件：
- 由其他线程notify或notifyAll了，并且当前线程被通知到了
- 经过和其他线程进行锁竞争，成功`获取到锁`了
2个条件，缺一不可。其实在实现层面，`notify和notifyAll都达到相同的效果，都只会有一个线程继续运行。`但notifyAll免去了，
线程运行完了通知其他线程的必要，因为已经通知过了。什么时候用notify，什么时候使用notifyAll，这就得看实际的情况了。

当有线程调用了对象的 `notifyAll()方法（唤醒所有 wait 线程）或 notify()方法（只`随机`唤醒一个 wait 线程）`，被唤醒的的线程便会进入
该对象的锁池中，锁池中的线程会去竞争该对象锁。也就是说，调用了notify后只要一个线程会由等待池进入锁池，而notifyAll会将该对象等待池
内的所有线程移动到锁池中，等待锁竞争


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

### 共享锁【S锁】 读锁，读时别人不能加写锁可以加读锁
又称`读锁`，若事务T对数据对象A加上S锁，则事务T可以读A但不能修改A，其他事务只能再对A加S锁，而不能加X锁，直到T释放A上的S锁。这保证了其他事务可以读A，但在T释放A上的S锁之前不能对A做任何修改。

### 排他锁【X锁】 写锁，写时别人不能加任何锁 
又称`写锁`。若事务T对数据对象A加上X锁，事务T可以读A也可以修改A，其他事务不能再对A加任何锁，直到T释放A上的锁。这保证了其他事务在T释放A上的锁之前不能再读取和修改A。

### 重入锁 ReentrantLock
Java中的重入锁（即ReentrantLock）与Java内置锁一样，是一种`排它锁`。`使用synchronized的地方一定可以用ReentrantLock代替。`

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

ReentrantLock类提供了一些高级功能，主要有以下3项：
1.等待可中断，持有锁的线程长期不释放的时候，`正在等待的线程可以选择放弃等待`，这相当于Synchronized来说可以避免出现死锁的情况。
2.公平锁，多个线程等待同一个锁时，`必须按照申请锁的时间顺序获得锁`，Synchronized锁非公平锁，`ReentrantLock默认的构造函数是创建的
非公平锁`，可以通过参数true设为公平锁，但公平锁表现的性能不是很好。
3.`锁绑定多个条件`，一个ReentrantLock对象可以同时绑定对个对象。

### 公平锁 VS 非公平锁
公平锁（Fair）：加锁前检查是否有排队等待的线程，`优先排队等待的线程，先来先得 `
非公平锁（Nonfair）：加锁时不考虑排队等待问题，`直接尝试获取锁，获取不到自动到队尾等待`
`非公平锁性能比公平锁高5~10倍`，因为公平锁需要在多核的情况下维护一个队列

### 读写锁（ReadWriteLock）
对于读多写少的场景，一个读操作无须阻塞其它读操作，只需要保证读和写或者写与写不同时发生即可。此时，如果使用重入锁（即排它锁），
对性能影响较大。Java中的读写锁（ReadWriteLock）就是为这种`读多写少的场景`而创造的。

实际上，ReadWriteLock接口并非继承自Lock接口，ReentrantReadWriteLock也只实现了ReadWriteLock接口而未实现Lock接口。
ReadLock和WriteLock，是ReentrantReadWriteLock类的静态内部类，它们实现了Lock接口。

### 条件锁
条件锁只是一个帮助用户理解的概念，实际上并没有条件锁这种锁。对于每个重入锁，都可以通过newCondition()方法绑定若干个条件对象。

### synchronize 锁
使得该代码具有 原子性（atomicity）和 可见性（visibility）。原子性意味着一个线程一次只能执行由一个指定监控对象（lock）保护的代码，
从而防止多个线程在更新共享状态时相互冲突。 可见性类似volatile关键字。
synchronize的用法(修饰类，方法，代码块等)
它的修饰对象有几种：
- 修饰一个类，其作用的范围是synchronized后面括号`括起来的部分`， 作用的对象是`这个类的所有对象`。
- 修饰一个方法，被修饰的方法称为同步方法，其`作用的范围是整个方法`， 作用的对象是`调用这个方法的对象`；
- 修改一个静态的方法，其作用的范围是`整个静态方法`， 作用的对象是`这个类的所有对象`；
- 修饰一个代码块，被修饰的代码块称为同步语句块，其作用的范围是`大括号{}括起来的代码， 作用的对象是调用这个代码块的对象；`

### synchronize 和 Lock 区别
- 用法不一样
synchronize 既可以加在方法上，也可以加在特定代码块中，括号中表示需要锁的对象。而 Lock 需要显式地指定起始位置和终止位置。
synchronize 交给 JVM 执行，而 Lock 地锁定是通过代码实现的。
- 性能不一样 
在资源竞争不激烈时，synchronize 性能高；竞争激励 ReentrantLock 性能基本不变，synchronize 性能下降很厉害
- 锁机制不一样 
synchronize 获得锁和释放锁的方式都是在块结构中，当获得多个锁时，必须以相反的顺序释放，并且是自动解锁的。
Lock一定要求程序员手工释放，并且必须在finally从句中释放。Lock 的 tryLock() 方法可以采用非阻塞(zu se)方式获取锁。


### synchronize和ReentrantLock的区别
ReentrantLock 是 Lock 接口的实现类
（1）什么情况下可以使用 ReentrantLock
使用synchronized 的一些限制： 
- 无法中断正在等候获取一个锁的线程；
- 无法通过投票得到一个锁；synchronized 是一个非公平锁， ReentrantLock 可以设置为公平锁
- 释放锁的操作只能与获得锁所在的代码块中进行，无法在别的代码块中释放锁；
ReentrantLock 没有以上的这些限制，且必须是手工释放锁。

（2）简单对比
主要相同点：Lock能完成synchronized所实现的所有功能
主要不同点：Lock有比synchronized更精确的线程语义和更好的性能，当许多线程都在争用同一个锁时，使用 ReentrantLock 的总体开支通常要比
 synchronized 少得多。 synchronized会自动释放锁，而`Lock一定要求程序员手工释放，并且必须在finally从句中释放`。

#### 简单的总结
#### synchronized：
在资源竞争不是很激烈的情况下，偶尔会有同步的情形下，synchronized是很合适的。原因在于，编译程序通常会尽可能的进行优化synchronize，
另外可读性非常好，不管用没用过5.0多线程包的程序员都能理解。
#### ReentrantLock:
ReentrantLock提供了多样化的同步，比如有时间限制的同步，可以被Interrupt的同步（synchronized的同步是不能Interrupt的）等。在资源竞争
不激烈的情形下，性能稍微比synchronized差点点。但是当同步非常激烈的时候，synchronized的性能一下子能下降好几十倍。而ReentrantLock
确还能维持常态。
#### Atomic:
和上面的类似，不激烈情况下，性能比synchronized略逊，而激烈的时候，也能维持常态。激烈的时候，Atomic的性能会优于ReentrantLock一倍左右。
但是其有一个缺点，就是只能同步一个值，`一段代码中只能出现一个Atomic的变量，多于一个同步无效。`因为他不能在多个Atomic之间同步。
大部分采用 CAS 

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


### ExecutorService
#### 四类 Pool
1. CachedThreadPool
CachedThreadPool首先会`按照需要创建足够多的线程来执行任务(Task)`。随着程序执行的过程，有的线程执行完了任务，可以被重新循环使用时，才不再创建新的线程来执行任务

2. FixedThreadPool
FixedThreadPool模式会使用一个`优先固定数目的线程`来处理若干数目的任务。规定数目的线程处理所有任务，`一旦有线程处理完了任务就会被
用来处理新的任务(如果有的话)`。这种模式与上面的CachedThreadPool是不同的，CachedThreadPool模式下处理一定数量的任务的线程数目是不确
定的。而FixedThreadPool模式下最多 的线程数目是一定的。

3. SingleThreadExecutor模式
SingleThreadExecutor模式只会创建一个线程。它和FixedThreadPool比较类似，不过`线程数是一个`。如果多个任务被提交给SingleThreadExecutor的话，
那么这些任务会被保存在`一个队列中`，并且会按照任务提交的顺序，一个先执行完成再执行另外一个线程。
SingleThreadExecutor模式可以保证只有一个任务会被执行。这种特点可以被用来处理共享资源的问题而不需要考虑同步的问题。
4. ScheduledExecutorService执行`周期性或定时任务`
schedule方法被用来延迟指定时间来执行某个指定任务。如果你需要周期性重复执行定时任务可以使用scheduleAtFixedRate或者
scheduleWithFixedDelay方法，它们不同的是前者以固定频率执行，后者以相对固定频率执行。

任务分两类：一类是实现了Runnable接口的类，一类是实现了Callable接口的类。两者都可以被ExecutorService执行，但是Runnable任务没有返回值，
而Callable任务有返回值。并且Callable的call()方法只能通过ExecutorService的(<T> task) 方法来执行，并且返回一个 <T><T>，是表示任务等待完成的 Future。
### execute方法和submit方法三个区别：
1、接收的参数不一样
2、submit有返回值，而execute没有
Method submit extends base method Executor.execute by creating and returning a Future that can be used to cancel execution and/or wait for completion. 
用到返回值的例子，比如说我有很多个做validation的task，我希望所有的task执行完，然后每个task告诉我它的执行结果，是成功还是失败，如果是失败，原因是什么。然后我就可以把所有失败的原因综合起来发给调用者。
个人觉得cancel execution这个用处不大，很少有需要去取消执行的。
而最大的用处应该是第二点。
3、submit方便Exception处理

### 创建多线程的三种方法及其区别
1. 新建类继承 Thread 类实现线程
2. 通过实现接口 Runnable 实现创建线程
3. 使用ExecutorService、Callable、Future实现有返回结果可抛异常的多线程(JDK5.0以后)
`可返回值的任务必须实现Callable接口`，类似的，`无返回值的任务必须Runnable接口`。执行Callable任务后，可以获取一个Future的对象，
在该对象上调用get就可以获取到Callable任务返回的Object了，再结合线程池接口ExecutorService就可以实现传说中有返回结果的多线程了。
```java
public class Test {  
public static void main(String[] args) throws ExecutionException,  
    InterruptedException {  
   System.out.println("----程序开始运行----");  
   Date date1 = new Date();  
  
   int taskSize = 5;  
   // 创建一个线程池  
   ExecutorService pool = Executors.newFixedThreadPool(taskSize);  
   // 创建多个有返回值的任务  
   List<Future> list = new ArrayList<Future>();  
   for (int i = 0; i < taskSize; i++) {  
    Callable c = new MyCallable(i + " ");  
    // 执行任务并获取Future对象  
    Future f = pool.submit(c);  
    // System.out.println(">>>" + f.get().toString());  
    list.add(f);  
   }  
   // 关闭线程池  
   pool.shutdown();  
  
   // 获取所有并发任务的运行结果  
   for (Future f : list) {  
    // 从Future对象上获取任务的返回值，并输出到控制台  
    System.out.println(">>>" + f.get().toString());  
   }  
  
   Date date2 = new Date();  
   System.out.println("----程序结束运行----，程序运行时间【"  
     + (date2.getTime() - date1.getTime()) + "毫秒】");  
}  
}
class MyCallable implements Callable<Object> {  
    private String taskNum;  
  
    MyCallable(String taskNum) {  
    this.taskNum = taskNum;  
    }  
  
    public Object call() throws Exception {  
       System.out.println(">>>" + taskNum + "任务启动");  
       Date dateTmp1 = new Date();  
       Thread.sleep(1000);  
       Date dateTmp2 = new Date();  
       long time = dateTmp2.getTime() - dateTmp1.getTime();  
       System.out.println(">>>" + taskNum + "任务终止");  
       return taskNum + "任务返回运行结果,当前任务时间【" + time + "毫秒】";  
    }  
}
```


### 引用
#### 强引用
强引用就是指代码中普遍存在，类似 `Object obj = new Object()` 这类的引用，只要强引用还存在，垃圾收集器永远不会回收掉被引用的对象。
#### 软引用
软引用是用来描述一些`还有用但并非必需的对象`。对于软引用关联着的对象，在系统将要发生`内存溢出异常之前`，将会把这些对象`列进回收回收范围`
之中进行`第二次回收`。如果这次回收还没有足够的内存，才会抛出内存溢出异常。JDK 提供 `SoftReference 类`实现软引用。
#### 弱引用
弱引用也是用来描述`非必需对象`的，但是它的强度比软引用更弱一些，被弱引用关联的`对象只能生存道下一次垃圾收集发生之前`。当垃圾收集器工作时，
无论当前内存是否足够，都会`回收掉只被弱引用关联的对象`。JDK 提供 `WeakReference 类`来实现弱引用。
#### 虚引用 或 幽灵引用 或 幻影引用
最弱引用。一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，也无法通过虚引用来来取得一个对象实例。为一个对象设置虚引用
关联的`唯一目的`就是能在这个`对象被收集器回收时`收到`一个系统通知`。JDK 提供 `PhantomReference 类` 来实现虚引用。


### 聚合
聚在一起，弱的拥有关系，例如雁群和大雁  空心菱形表示
### 组合 或 合成
强引用关系，整体和部分的关系，例如鸟和翅膀  实心菱形表示
### 依赖
例如动物依赖氧气和水。虚线表示

### jsp VS servlet 区别、共同点、各自应用的范围
`JSP是Servlet技术的扩展，本质上就是Servlet的简易方式。`JSP编译后是“类servlet”。Servlet和JSP最主要的不同点在于，Servlet的应用逻辑
是在Java文件中，并且完全从表示层中的HTML里分离开来。而JSP的情况是Java和HTML可以组合成一个扩展名为.jsp的文件。JSP侧重于视图，
Servlet主要用于`控制逻辑。`在struts框架中,JSP位于MVC设计模式的`视图层`,而Servlet位于`控制层`.

### cookie和session的作用、区别、应用范围，session的工作原理？？？
- Cookie:主要用在保存`客户端`，其值在客户端与服务端之间传送，不安全，存储的数据量有限。
- Session:保存在`服务端`，每一个session在服务端有一个sessionID作一个标识。存储的数据量大，安全性高。占用服务端的内存资源。
服务器使用session把用户的信息临时保存在了服务器上，用户离开网站后session会被销毁。这种用户信息存储方式相对cookie来说更安全，
可是session有一个缺陷：`如果web服务器做了负载均衡，那么下一个操作请求到了另一台服务器的时候session会丢失`。

- Session只提供一种简单的认证，即有此 `SID`，即认为有此 User的全部权利。是需要严格保密的，这个数据应该只保存在站方，不应该共享给其它网站或者第三方App。 所以简单来说，如果你的用户数据可能需要和第三方共享，或者允许第三方调用 API 接口，用 Token 。如果永远只是自己的网站，自己的 App，用什么就无所谓了。
- token就是令牌，比如你`授权（登录）一个程序`时，他就是个依据，判断你是否已经授权该软件；cookie就是写在客户端的一个txt文件，
里面包括你登录信息之类的，这样你下次在登录某个网站，就会自动调用cookie自动登录用户名；session和cookie差不多，只是session是写在
服务器端的文件，也需要在客户端写入cookie文件，但是文件里是你的浏览器编号.Session的状态是存储在服务器端，客户端只有session id；
而Token的状态是存储在客户端。

### 过滤器和拦截器的区别
1、`拦截器是基于java的反射机制的`，而`过滤器是基于函数回调`
2、`过滤器依赖于servlet容器`，而拦截器不依赖于servlet容器
3、`拦截器只能对action请求`起作用，而`过滤器则可以对几乎所有的请求起作用`
4、`拦截器可以访问action上下文、值栈里的对象`，而过滤器不能
5、在action的生命周期中，`拦截器可以多次被调用`，而`过滤器只在容器初始化时调用一次`
- 拦截器(interceptor)(before after)：是在面向切面编程的就是在你的service或者一个方法，前调用一个方法，或者在方法后调用一个方法比如动态代理就是
拦截器的简单实现，在你调用方法前打印出字符串（或者做其它业务逻辑的操作），也可以在你调用方法后打印出字符串，甚至在你抛出异常的时候做业务逻辑的操作。
- 过滤器(filter)：是在Java Web中，你传入的request,response提前过滤掉一些信息，或者提前设置一些参数，然后再传入servlet或者struts
的 action进行业务逻辑，比如过滤掉非法url（不是login.do的地址请求，如果用户没有登陆都过滤掉）,或者在传入servlet或者 struts的action
前统一设置字符集，或者去除掉一些非法字符.

1. 过滤器（Filter）：所谓过滤器顾名思义是用来过滤的，Java的过滤器能够为我们提供系统级别的过滤，也就是说，能过滤所有的web请求，
这一点，是拦截器无法做到的。在Java Web中，你传入的request,response提前过滤掉一些信息，或者提前设置一些参数，然后再传入servlet或
者struts的action进行业务逻辑，比如`过滤掉非法url`（不是login.do的地址请求，如果用户没有登陆都过滤掉）,或者在传入servlet或者struts
的action前统一设置字符集，或者去除掉一些非法字符（聊天室经常用到的，一些骂人的话）。filter 流程是线性的，url传来之后，检查之后，
可保持原来的流程继续向下执行，被下一个filter, servlet接收。
2. 监听器（Listener）：Java的监听器，也是系统级别的监听。监听器随web应用的启动而启动。Java的监听器在c/s模式里面经常用到，它
会对特定的事件产生产生一个处理。`监听在很多模式下用到，比如说观察者模式，就是一个使用监听器来实现的`，在比如`统计网站的在线人数`。
又比如struts2可以用监听来启动。Servlet监听器用于监听一些重要事件的发生，监听器对象可以在事情发生前、发生后可以做一些必要的处理。
3. 拦截器（Interceptor）：java里的拦截器提供的是非系统级别的拦截，也就是说，`就覆盖面来说，拦截器不如过滤器强大`，但是更有针对性。
Java中的拦截器是`基于Java反射机制实现的，更准确的划分，应该是基于JDK实现的动态代理`。它依赖于具体的接口，在运行期间动态生成字节码。
拦截器是动态拦截Action调用的对象，它提供了一种机制可以使开发者在`一个Action执行的前后执行一段代码`，也可以在一个Action执行前阻止其
执行，同时也提供了一种可以提取Action中可重用部分代码的方式。在AOP中，拦截器用于在某个方法或者字段被访问之前，进行拦截然后再之前或
者之后加入某些操作。java的拦截器主要是用在插件上，扩展件上比如 Hibernate Spring Struts2等，有点类似面向切片的技术，在用之前先要在
配置文件即xml，文件里声明一段的那个东西。

### Hibernate是如何延迟加载？
当Hibernate在查询数据的时候，`数据并没有存在与内存中`，当程序真正对数据的操作时，对象才存在与内存中，就实现了延迟加载，他节省了服务器的内存开销，从而提高了服务器的性能。

### 说下Hibernate的缓存机制
1. 内部缓存存在Hibernate中又叫一级缓存，属于`应用事物级缓存`
2. 二级缓存：
  a) 应用及缓存
  b) 分布式缓存
  c) 第三方缓存的实现
  

### spring工作机制及为什么要用?【spring是一个轻量的控制反转和面向切面的容器框架】
1. springmvc把所有的请求都提交给DispatcherServlet,它会委托应用系统的其他模块负责对请求进行真正的处理工作。
2. DispatcherServlet查询一个或多个HandlerMapping,找到处理请求的Controller.
3. DispatcherServlet把请求提交到目标Controller
4. Controller进行业务逻辑处理后，会返回一个ModelAndView
5. Dispathcher查询一个或多个ViewResolver视图解析器,找到ModelAndView对象指定的视图对象
6. 视图对象负责渲染返回给客户端。
IoC就是由容器来控制业务对象之间的依赖关系。控制反转的本质，是`控制权由应用代码转到了外部容器`，控制器的转移既是所谓的反转。控制权的转移带来的好处就是降低了业务对象之间的依赖程度，即实现了解耦。
DI/IOC,对持久层和表示层的控制与分配，增加系统的灵活性和稳定性. AOP,面向切面,利用代理对程序的有效管理.
spring是一个轻量级的IOC和AOP框架，通过spring的`IOC实现松耦合`，而作为`一个AOP框架他又能分离系统服务，实现内聚开发` Spring 最好的
地方是它有助于您替换对象。有了 Spring，只要用 `JavaBean 属性和配置文件加入依赖性（协作对象）`。然后可以很容易地在需要时替换具有类似
接口的协作对象。}
Spring对多种ORM框架提供了很好的支持

### Tomcat的优化经验
- `去掉对web.xml的监视`，把jsp提前编辑成Servlet。有富余物理内存的情况，`加大tomcat使用的jvm的内存`
- `服务器资源`
　　服务器所能提供CPU、内存、硬盘的性能对处理能力有决定性影响。
　　(1) 对于高并发情况下会有大量的运算，那么CPU的速度会直接影响到处理速度。
　　(2) 内存在大量数据处理的情况下，将会有较大的内存容量需求，可以用-Xmx -Xms -XX:MaxPermSize等参数`对内存不同功能块进行划分。`我们之前就遇到过内存分配不足，导致虚拟机一直处于full GC，从而导致处理能力严重下降。
　　(3) 硬盘主要问题就是读写性能，当大量文件进行读写时，磁盘极容易成为性能瓶颈。最好的办法还是利用下面提到的缓存。
- 利用`缓存和压缩`
　　对于`静态页面最好是能够缓存起来`，这样就不必每次从磁盘上读。这里我们采用了 Nginx 作为缓存服务器，将图片、css、js文件都进行了缓存，有效的减少了后端tomcat的访问。
　　另外，为了能加快网络传输速度，`开启gzip压缩`也是必不可少的。但考虑到tomcat已经需要处理很多东西了，所以把这个压缩的工作就交给前端的Nginx来完成。
　　除了文本可以用gzip压缩，其实很多`图片`也可以用图像处理工具预先进行`压缩`，找到一个平衡点可以让画质损失很小而文件可以减小很多。曾经我就见过一个图片从300多kb压缩到几十kb，自己几乎看不出来区别。
- 采用`集群`
　　单个服务器性能总是有限的，最好的办法自然是实现横向扩展，那么组建tomcat集群是有效提升性能的手段。我们还是采用了Nginx来作为请求分流的服务器，后端多个tomcat共享session来协同工作。
- `优化tomcat参数`
　　这里以tomcat7的参数配置为例，需要修改conf/server.xml文件，主要是优化连接配置，`关闭客户端dns查询`。
- 改用APR库 `APR(Apache portable Run-time libraries，Apache可移植运行库)`的目的如其名称一样，主要为上层的应用程序提供一个可以跨越多操作系统平台使用的底层支持接口库。
   tomcat默认采用的BIO模型，在几百并发下性能会有很严重的下降。tomcat自带还有`NIO的模型`，另外也可以调用APR的库来实现操作系统级别控制。
    NIO模型是内置的，调用很方便，只需要将上面配置文件中protocol修改成org.apache.coyote.http11.Http11NioProtocol，重启即可生效。上面配置我已经改过了，默认的是HTTP/1.1。
    APR则需要安装第三方库，在高并发下会让性能有明显提升。
此外，代码中的System.out语句会严重影响代码执行的效率，就将这些为必要的语句在调试完成之后全部删除以提高效率。


### jsp有哪些内置对象?作用分别是什么? 分别有什么方法？
答:JSP共有以下9个内置的对象：
- request 用户端请求，此请求会包含来自GET/POST请求的参数
- response 网页传回用户端的回应
- pageContext 网页的属性是在这里管理
- session 与请求有关的会话期
- application servlet正在执行的内容
- out 用来传送回应的输出
- config servlet的构架部件 该对象用于存取servlet实例的初始化参数。
- page JSP网页本身 表示从该页面产生的一个servlet实例
- exception 针对错误网页，未捕捉的例外

### jsp有哪些动作?作用分别是什么?
答:JSP共有以下6种基本动作
- jsp:include：在页面被请求的时候引入一个文件。
- jsp:useBean：寻找或者实例化一个JavaBean。
- jsp:setProperty：设置JavaBean的属性。
- jsp:getProperty：输出某个JavaBean的属性。
- jsp:forward：把请求转到一个新的页面。
- jsp:plugin：根据浏览器类型为Java插件生成OBJECT或EMBED标记

### 反射和代理
### java 反射
#### getFields() 和 getDeclaredFields()
getFields()获得某个类的所有的`公共（public）的字段`，包括父类。 
getDeclaredFields()获得某个类的`所有申明的字段`，即包括`public、private 和 protected`，但是不包括父类的申明字段。 
同样类似的还有getConstructors()和getDeclaredConstructors()，getMethods()和getDeclaredMethods()。
  
#### Java 反射机制
JAVA反射机制是在运行状态中，对于任意一个类，都能够知道这个`类的所有属性和方法`；对于任意一个`对象，都能够调用它的任意一个方法和属性`；
这种`动态获取的信息`以及`动态调用对象的方法的功能`称为java语言的反射机制。 
#### Java 动态代理
通过反射在被调用方法前后加上自己的操作，而不需要更改被调用类的源码，大大地降低了模块之间的耦合性，体现了极大的优势。
#### 关于类加载器
在Proxy类中的newProxyInstance()方法中需要一个ClassLoader类的实例，ClassLoader实际上对应的是类加载器，在Java中主要有以下三种类加载器：
① Booststrap ClassLoader：此加载器采用C++编写，通常加载jre/lib/rt.jar，一般开发中是看不到的； 
② Extendsion ClassLoader：用来进行扩展类的加载，通常加载jre/lib/ext/*.jar; 
③ AppClassLoader：(默认)加载classpath指定的类，是最常使用的是一种加载器；

### 面向切面(AOP),原理是什么 
- AOP技术是建立在Java语言的`反射机制与动态代理机制`之上的。业务逻辑组件在运行过程中，AOP容器会动态创建一个`代理对象`供使用者调用，
该代理对象已经按Java EE程序员的意图将切面成功切入到目标方法的`连接点`上，从而使`切面的功能与业务逻辑的功能同时得以执行`。从原理上讲，
调用者直接调用的其实是`AOP容器动态生成的代理对象`，再由代理对象调用目标对象完成原始的业务逻辑处理，而`代理对象则已经将切面与业务逻辑方法进行了合成`。
- AOP在Java里是利用反射机制实现（你也可以认为是动态代理，不过动态代理也是反射机制实现的）
- 面向切面编程AOP技术就是为解决这个问题而诞生的，`切面就是横切面`(类似有日志切面、权限切面)，抽取众多方法中的所有共有代码，放置到某个地方集中管理，然后在具体运行时，再由容器动态织入这些共有代码。

切面（Aspect）：其实就是共有功能的实现。如`日志切面、权限切面、事务切面`等。在实际应用中通常是一个存放共有功能实现的普通Java类，之所以能被AOP容器识别成切面，是在配置中指定的。
通知（Advice）：是切面的具体实现。以目标方法为参照点，根据放置的地方不同，可分为`前置通知（Before）、后置通知（AfterReturning）、异常通知（AfterThrowing）、最终通知（After）与环绕通知（Around）5种`。在实际应用中通常是切面类中的一个方法，具体属于哪类通知，同样是在配置中指定的。

### 你对Spring的理解，Spring里面的代理是怎么实现的，如果让你设计，你怎么设计
AOP呢，它实现的就是容器的另一大好处了，就是可以让容器中的对象都享有容器中的公共服务。
代理模式是一种静态代理，而动态代理就是利用反射和动态编译将代理模式变成动态的。原理跟动态注入一样，`代理模式在编译的时候就已经确定代理类
将要代理谁，而动态代理在运行的时候才知道自己要代理谁`。
Spring的动态代理有两种：`一是JDK的动态代理`；`另一个是cglib动态代理（通过修改字节码来实现代理）`
如果使用JDK的Proxy来生成代理对象，那么需要通过`InvocationHandler`来设置拦截器回调； 而如果使用CGLIB来生成代理对象，
就需要根据CGLIB的使用要求，通过`DynamicAdvisedInterceptor`来完成回调。
1.JDK动态代理
此时代理对象和目标对象实现了相同的接口，目标对象作为代理对象的一个属性，具体接口实现中，可以在调用目标对象相应方法前后加上其他业务处理逻辑。
代理模式在实际使用时需要指定具体的目标对象，如果为每个类都添加一个代理类的话，会导致类很多，同时如果不知道具体类的话，怎样实现代理模式呢？这就引出动态代理。
JDK动态代理只能针对实现了接口的类生成代理。
2.CGLIB代理 动态字节码生成（cglib）
CGLIB（CODE GENERLIZE LIBRARY）代理是针对类实现代理，
主要是对指定的类生成一个子类，覆盖其中的所有方法，所以该类或方法不能声明称final的。
#### JDK动态代理和CGLIB代理生成的区别
- `JDK动态代理只能对实现了接口的类生成代理`，而不能针对类。
- `CGLIB是针对类实现代理`，主要是对指定的类生成一个子类，覆盖其中的方法 。因为是继承，所以该类或方法最好不要声明成final ，final可以阻止继承和多态。


Spring 框架的一个关键组件是面向方面的编程(AOP)框架。面向方面的编程需要把程序逻辑分解成不同的部分称为所谓的关注点。跨一个应用程序的
多个点的功能被称为横切关注点，这些横切关注点在概念上独立于应用程序的业务逻辑。有各种各样的常见的很好的方面的例子，如`日志记录、审计、
声明式事务、安全性和缓存等`。
在 `OOP 中，关键单元模块度是类`，而在 `AOP 中单元模块度是方面`。依赖注入帮助你对应用程序对象相互解耦和 AOP 可以帮助你从它们所影响的对象
中对横切关注点解耦。
Spring AOP 模块提供拦截器来拦截一个应用程序，例如，当执行一个方法时，你可以在方法执行之前或之后添加额外的功能。
在典型的面向对象开发方式中，可能要将日志记录语句放在所有方法和 Java 类中才能实现日志功能。在 AOP 方式中，可以反过来将日志服务模块化，
并以声明的方式将它们应用到需要日志的组件上。当然，优势就是 Java 类不需要知道日志服务的存在，也不需要考虑相关的代码。所以，用 Spring 
AOP 编写的应用程序代码是松散耦合的。 AOP 的功能完全集成到了 Spring 事务管理、日志和其他各种特性的上下文中。
Spring AOP中，当拦截对象实现了接口时，生成方式是用JDK的Proxy类。当没有实现任何接口时用的是GCLIB开源项目生成的拦截类的子类.

#### AOP:面向切面、面向方面、面向接口是一种横切技术 
横切技术运用： 
1.事务管理: (1)数据库事务:(2)编程事务(3)声明事物:Spring AOP-->声明事物    
2.日志处理: 
3.安全验证: Spring AOP---OOP升级   
#### 静态代理原理：目标对象：调用业务逻辑    代理对象：日志管理 
表示层调用--->代理对象(日志管理)-->调用目标对象
#### 动态代理原理：spring AOP采用动态代理来实现 
(1)实现InvocationHandler接口
(2)创建代理类(通过java API)
Proxy.newProxyInstance(动态加载代理类,代理类实现接口,使用handler);
(3)调用invoke方法(虚拟机自动调用方法)
日志处理 
 //调用目标对象 
 method.invoke("目标对象","参数"); 
 日志处理
通过代理对象--(请求信息)-->目标对象---(返回信息)----> 代理对象
```java
class JdkDPQueryHandler implements InvocationHandler{
    private Arithmetic real;
    public JdkDPQueryHandler(Arithmetic real){
        this.real = real;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        System.out.println(method);
        System.out.println("the method: " + methodName + "开始, 参数: "+Arrays.asList(args));
        Object result = method.invoke(real, args);
        System.out.println("the method: "+methodName+"结束, 结果: " + result);
        return result;
    }
}
public class Main{
    private static int a = 4, b = 2;
    
    public static Object createJDKProxy(Arithmetic real){
        Object proxyArithmetic = Proxy.newProxyInstance(real.getClass().getClassLoader(),
                real.getClass().getInterfaces(), new JdkDPQueryHandler(real)); 
        return proxyArithmetic;
    }
    
    public static void main(String[] args){
        Arithmetic real = new Arithmetic();
        Object proxyArithmetic = createJDKProxy(real);
        ((AddInterface)proxyArithmetic).add(a, b);
        ((SubInterface)proxyArithmetic).sub(a, b);
    }
}
```




### Spring IOC (Inversion of Control控制反转) 即依赖注入（Dependency Injection）
简单的说，Spring就是通过工厂+反射将我们的bean放到它的容器中的，当我们想用某个bean的时候，只需要调用getBean("beanID")方法。
为了解决对象之间的耦合度过高的问题
获得依赖对象的过程被反转了。控制被反转之后，获得依赖对象的过程由自身管理变为了由IOC容器主动注入。
`通过引入IOC容器，利用依赖关系注入的方式，实现对象之间的解耦。`
我们可以把IOC容器的工作模式看做是`工厂模式的升华`，可以把IOC容器看作是一个工厂，这个工厂里要生产的对象都在配置文件中给出定义，然后利用
编程语言的的反射编程，根据配置文件中给出的类名生成相应的对象。从实现来看，IOC是把以前在工厂方法里写死的对象生成代码，改变为由配置文件
来定义，也就是把工厂和对象生成这两者独立分隔开来，目的就是提高灵活性和可维护性。


### 面向对象思想
简单来说就是把复杂系统分解成相互合作的对象，这些对象类通过封装以后，内部实现对外部是透明的，从而降低了解决问题的复杂度，而且可以灵活地被重用和扩展。

### Java 集合框架基本接口有哪些
Collection：代表一组对象，每一个对象都是它的子元素。
    Set：不包含重复元素的Collection。
    List：有顺序的collection，并且可以包含重复元素。
    Queue:队列
Map：可以把键(key)映射到值(value)的对象，键不能重复。

### Java 虚拟机运行时数据区
JAVA的JVM的内存可分为3个区：堆(heap)、栈(stack)和方法区(method) 

#### 堆区: 
1.存储的全部是对象，每个对象都包含一个与之对应的class的信息。(class的目的是得到操作指令) 
2.jvm只有`一个堆区(heap)被所有线程共享`，堆中不存放基本类型和对象引用，只存放`对象本身 ` new 的对象
#### 栈区: 
1.每个线程包含一个栈区，栈中只保存`基础数据类型的对象`和`自定义对象的引用`(不是对象)，对象都存放在堆区中 
2.每个栈中的数据(原始类型和对象引用)都是`私有`的，其他栈不能访问。 
3.栈分为3个部分：基本类型变量区、执行环境上下文、操作指令区(存放操作指令)。 
##### 虚拟机栈
栈帧存局部变量表、操作数栈、动态链接 
##### 本地方法栈
为虚拟机使用到的 native 方法服务
#### 方法区: 
1.又叫静态区，跟堆一样，被所有的线程共享。方法区包含`所有的class和static变量`。 
2.方法区中包含的都是在整个程序中永远唯一的元素，如class，static变量。
#### 程序计数器
虚拟机字节码指令的地址。当前线程所执行的字节码的行号指示器。
```java
  class AppMain {               //运行时, jvm 把appmain的信息都放入方法区
     public static void main(String[] args)  //main 方法本身放入方法区。   
     {
         Sample test1 = new Sample(" 测试1 ");   //test1是引用，所以放到栈区里， Sample是自定义对象应该放到堆里面   
         Sample test2 = new Sample(" 测试2 ");
 
         test1.printName();
         test2.printName();
     }
 } 
 
  class Sample {        //运行时, jvm 把appmain的信息都放入方法区
 /** 范例名称 */
     private String name;      //new Sample实例后， name 引用放入栈区里，  name 对象放入堆里   
 
     /**
      * 构造方法
      */
     public Sample(String name) {
         this.name = name;
     }
 
     /**
      * 输出
      */
     public void printName()   //print方法本身放入 方法区里。   
     {
         System.out.println(name);
     }
 } 
```
### JMM
Java Memory Model java内存模型 

### 对象已死了吗
引用计数算法 可达性分析算法( jvm 采用这个，从 root 开始检索)
### GC 需要完善
#### 你能不能谈谈，GC是在什么时候，对什么东西，做了什么事情？
#### 什么时候
3. 能说出新生代、老年代结构，能提出minor gc/full gc 
  分析：到了这个层次，基本上能说对GC运作有概念上的了解，譬如看过《深入JVM虚拟机》之类的。
4. 能说明minor gc/full gc的触发条件、OOM的触发条件，降低GC的调优的策略。     
分析：列举一些我期望的回答：eden满了minor gc，升到老年代的对象大于老年代剩余空间full gc，或者小于时被HandlePromotionFailure参数
强制full gc；gc与非gc时间耗时超过了GCTimeRatio的限制引发OOM，调优诸如通过NewRatio控制新生代老年代比例，通过MaxTenuringThreshold
控制进入老年前生存次数等……

#### 对什么东西
因为引用计数法无法解决循环引用问题，JVM并没有采用这种算法来判断对象是否存活。
3. 从gc root开始搜索，搜索不到的对象。     
分析：根对象查找、标记已经算是不错了，小于5%的人可以回答道这步，估计是引用计数的方式太“深入民心”了。基本可以得到这个问题全部分数。    
 PS：有面试者在这个问补充强引用、弱引用、软引用、幻影引用区别等，不是我想问的答案，但可以加分。     
4. 从root搜索不到，而且经过第一次标记、清理后，仍然没有复活的对象。     
分析：我期待的答案。但是的确很少面试者会回答到这一点，所以在我心中回答道第3点我就给全部分数。

#### 做什么事情
3. 能说出诸如新生代做的是复制清理、from survivor、to survivor是干啥用的、老年代做的是标记清理、标记清理后碎片要不要整理、复制清理和标记清理有有什么优劣势等。     
分析：也是看过《深入JVM虚拟机》的基本都能回答道这个程度，其实到这个程度我已经比较期待了。同样小于10%。    
4. 除了3外，还能讲清楚串行、并行（整理/不整理碎片）、CMS等搜集器可作用的年代、特点、优劣势，并且能说明控制/调整收集器选择的方式。

####（3）有哪些方法可以判断一个对象已经可以被回收，JVM怎么判断一个对象已经消亡可以被回收？     
- 引用计数算法         
给对象中添加一个引用计数器，每当有一个地方引用它时，计数器就加1；当引用失效时，计数器值就减1；任何时刻计数器都为0的对象就是不可能再被使用的。         
Java语言没有选用引用计数法来管理内存，因为引用计数法不能很好的解决循环引用的问题。    
- 根搜索算法       在主流的商用语言中，都是使用根搜索算法来判定对象是否存活的。       
GC Root Tracing 算法思路就是通过一系列的名为"GC  Roots"的对象作为起始点，从这些节点开始向下搜索，搜索所走过的路径称为引用链（Reference Chain），
当一个对象到GC Roots没有任何引用链相连，即从GC Roots到这个对象不可达，则证明此对象是不可用的。
####（4）那些对象可以作为GC Roots？    
- 虚拟机栈（栈帧中的本地变量表）中的引用的对象    
- 方法区中的类静态属性引用的对象    
- 方法区中的常量引用的对象    
- 本地方法栈中JNI（Native方法）的引用对象
#####（5）Java代码编译的结果是什么？         
是字节码文件.class
#### (7)Java中的static变量和static方法在JVM运行中内存的分配管理有什么不同和一般变量方法？         
            静态对象                非静态对象 
拥有属性： 是类共同拥有的           是类各对象独立拥有的
内存分配： 内存空间上是固定的        空间在各个附属类里面分配 
分配顺序： 先分配静态对象的空间      继而再对非静态对象分配空间,也就是初 始化顺序是先静态                      

###（8）Java类的加载过程？ 7阶段
加载 -> 验证 -> 准备 -> 解析 -> 初始化 -> 使用 -> 卸载
       (      连接         )
- 加载
根据查找路径找到相对应的 class 文件，然后导入
“加载”(Loading)阶段是“类加载”(Class Loading)过程的第一个阶段，在此阶段，虚拟机需要完成以下三件事情：
1、 通过一个类的`全限定名来获取定义此类的二进制字节流。`
 如Object类， 在源文件中的全限定名是java.lang.Object 。 而class文件中的全限定名是将点号替换成“/” 。 例如， Object类在class文件中的全限定名是 java/lang/Object 
2、 将这个字节流所代表的`静态存储结构转化为方法区的运行时数据结构`。
3、 在`Java堆中生成一个代表这个类的java.lang.Class对象`，作为方法区这些数据的访问入口。
加载阶段即可以使用系统提供的类加载器在完成，也可以由用户自定义的类加载器来完成。加载阶段与连接阶段的部分内容(如一部分字节码文件格式验证动作)是交叉进行的，加载阶段尚未完成，连接阶段可能已经开始。

- 验证
检查待加载的 class 文件的正确性
验证是连接阶段的第一步，这一阶段的目的是为了确保Class文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全。
Java语言本身是相对安全的语言，使用Java编码是无法做到如访问数组边界以外的数据、将一个对象转型为它并未实现的类型等，如果这样做了，编译器将拒绝编译。但是，Class文件并不一定是由Java源码编译而来，可以使用任何途径，包括用十六进制编辑器(如UltraEdit)直接编写。如果直接编写了有害的“代码”(字节流)，而虚拟机在加载该Class时不进行检查的话，就有可能危害到虚拟机或程序的安全。
文件格式验证: 是要验证字节流是否符合Class文件格式的规范，并且能被当前版本的虚拟机处理。
元数据验证: 是对字节码描述的信息进行语义分析，以保证其描述的信息符合Java语言规范的要求
字节码验证: 主要工作是进行数据流和控制流分析，保证被校验类的方法在运行时不会做出危害虚拟机安全的行为
符号引用验证: 发生在虚拟机将符号引用转化为直接引用的时候，这个转化动作将在“解析阶段”中发生。验证符号引用中通过字符串描述的权限定名是否能找到对应的类；在指定类中是否存在符合方法字段的描述符及简单名称所描述的方法和字段；符号引用中的类、字段和方法的访问性(private、protected、public、default)是否可被当前类访问

- 准备
准备阶段是`为类的静态变量分配内存并将其初始化为默认值`，这些内存都将在方法区中进行分配。准备阶段不分配类中的实例变量的内存，实例变量将会在对象实例化时随着对象一起分配在Java堆中。
 public static int value=123;//在准备阶段value初始值为0 。在初始化阶段才会变为123 
 
-解析
解析阶段是虚拟机将常量池内的`符号引用替换为直接引用`的过程。
符号引用（Symbolic Reference）：符号引用以一组符号来描述所引用的目标，`符号可以是任何形式的字面量，只要使用时能无歧义地定位到目标即可`。符号引用与虚拟机实现的内存布局无关，引用的目标并不一定已经加载到内存中。
直接引用（Direct Reference）：直接引用可以是直接指向`目标的指针、相对偏移量或是一个能间接定位到目标的句柄。`直接引用是与虚拟机实现的内存布局相关的，如果有了直接引用，那么引用的目标必定已经在内存中存在。

- 初始化
类初始化是类加载过程的最后一步，前面的类加载过程，除了在加载阶段用户应用程序可以通过自定义类加载器参与之外，其余动作完全由虚拟机主导和控制。到了初始化阶段，才真正开始执行类中定义的Java程序代码。
收集类中的`所有类变量的赋值动作和静态语句块(static{}块)`中的语句合并产生的。

#### 3个类加载器
- Bootstrap Loader 负责加载`系统类` 启动类加载器
- ExtClassLoader 负责加载`扩展类`
- AppClassLoader 负责加载`应用类`
 
#### 双亲委派模型 
AppClassLoader -> ExtClassLoader -> BootStrapClassLoader 找到 ？ 结束 : -> ExtClassLoader 找到 ？ 结束 : -> AppClassLoader
双亲委派模型的工作流程是：如果一个类加载器收到了类加载的请求，`它首先不会自己去尝试加载这个类，而是把请求委托给父加载器去完成，依次向上，`
因此，所有的类加载请求最终都应该被传递到顶层的启动类加载器中，只有当父加载器在它的搜索范围中没有找到所需的类时，即无法完成该加载，
子加载器才会尝试自己去加载该类。
##### 双亲委派机制:
1、当AppClassLoader加载一个class时，它首先不会自己去尝试加载这个类，而是把类加载请求委派给父类加载器ExtClassLoader去完成。
2、当ExtClassLoader加载一个class时，它首先也不会自己去尝试加载这个类，而是把类加载请求委派给BootStrapClassLoader去完成。
3、如果BootStrapClassLoader加载失败（例如在$JAVA_HOME/jre/lib里未查找到该class），会使用ExtClassLoader来尝试加载；
4、若ExtClassLoader也加载失败，则会使用AppClassLoader来加载，如果AppClassLoader也加载失败，则会报出异常ClassNotFoundException。
Java类随着它的类加载器一起具备了一种带有优先级的层次关系。例如类Object，它放在rt.jar中，无论哪一个类加载器要加载这个类，
最终都是委派给启动类加载器进行加载，因此Object类在程序的各种类加载器环境中都是同一个类
判断两个类是否相同是通过classloader.class这种方式进行的，所以哪怕是同一个class文件如果被两个classloader加载，那么他们也是不同的类
##### 双亲委派模型意义：
- 系统类防止内存中出现多份同样的字节码
- 保证Java程序安全稳定运行
 
### java 程序初始化顺序
详见 java 面试宝典 p50
父类静态变量 父类静态代码块 子类静态变量 子类静态代码块 父类非静态变量 父类非静态代码块 父类构造函数 子类非静态变量 子类非静态代码块 子类构造函数
`注意没有方法，这也是单例模式内部静态类 lazy loading 延迟加载 的原因，当然还是线程安全的`


### 说出Servlet的生命周期  5阶段                                                                
加载 -> 创建实例 -> 初始化(init) -> 处理客户端请求(service) -> 销毁(destroy)
Servlet被服务器`实例化`后，容器运行其`init`方法，请求到达时运行其`service`方法，service方法自动派遣运行与请求对应的doXXX方法
（doGet，doPost）等，当服务器决定将实例销毁的时候调用其`destroy方`法。
#### 并说出Servlet和CGI的区别
与cgi的区别在于servlet处于服务器进程中，它通过`多线程方式运行其service方法`，一个实例可以服务于多个请求，并且其实例一般不会销毁，
而`CGI对每个请求都产生新的进程`，服务完成后就销毁，所以效率上低于servlet

#### 在heap中没有类实例的时候，类信息还存在于JVM吗？ 存在于什么地方？


#### Java concurrent 包的常用类
- Executor                  ：具体Runnable任务的执行者。Executor接口是所有线程执行类的父接口，这个接口可以建立线程池，然后执行线程。

- ExecutorService           ：一个线程池管理者，其实现类有多种，我会介绍一部分。我们能把Runnable,Callable提交到池中让其调度。

- Semaphore                 ：一个计数信号量
信号量Semaphore是一个计数信号量，用来保护一个或多个共享资源的访问，是Java Concurrent包下提供的另一种同步方式，就像synchronized
一样的呢，它就是替代synchronized的。 Semaphore可以控制某个资源可被同时访问的个数，通过 acquire() 获取一个许可，如果没有就等待，
而 release() 释放一个许可。

- Condition 
其中，Lock 替代了 synchronized 方法和语句的使用，Condition 替代了 Object 监视器方法的使用。
在Condition中，用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()，传统线程的通信方式，Condition都可以实现，这里注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
Condition的强大之处在于它可以为多个线程间建立不同的Condition， 使用synchronized\/wait()只有一个阻塞队列，notifyAll会唤起所有阻塞队列下的线程，而使用lock\/condition，可以实现多个阻塞队列，signalAll只会唤起某个阻塞队列下的阻塞线程。

- ReentrantLock             ：一个可重入的互斥锁定 Lock，功能类似synchronized，但要强大的多。

- Callable                  ：Callable接口与Runnable接口实现的功能都是一样的，`不过它有返回值的 抛异常`，我们可以知道线程是否执行完毕呢。通常与Future，FutureTask连着用。

- Future                    ：可以得到Callable接口与Runnable接口执行的返回值，也可以调用cancel方法取消线程的执行。
`Future就是对于具体的Runnable或者Callable任务的执行结果进行取消、查询是否完成、获取结果`。必要时可以通过get方法获取执行结果，该方法会阻塞直到任务返回结果。
也就是说Future提供了三种功能：
1）判断任务是否完成；
2）能够中断任务；
3）能够获取任务执行结果。
因为Future只是一个接口，所以是无法直接用来创建对象使用的，因此就有了下面的FutureTask。

- BlockingQueue             ：阻塞队列。
生产者消费者问题中，如果不用阻塞队列，采用非阻塞队列存放产品的话，需要用synchronized来对produce()和consume()操作进行同步，而采用
阻塞队列就不需要这样了，`它自身就带同步功能，当空时自然不能取，满时自然不能继续生产`，这就是BlockingQueue的作用。
阻塞队列使用最经典的场景就是socket客户端数据的读取和解析，读取数据的线程不断将数据放入队列，然后解析线程不断从队列取数据解析。
还有其他类似的场景，只要符合生产者-消费者模型的都可以使用阻塞队列。

- ThreadLocal
线程私有变量，就是我们在建立类的时候可以把成员变量声明为线程私有的， 最常见的ThreadLocal使用场景为 用来解决 `数据库连接、Session管理`等。 
ThreadLocal为变量在每个线程中都创建了一个`副本`，那么每个线程可以访问自己内部的副本变量。
准确的说，应该是ThreadLocal类型的变量内部的注册表（Map<Thread,T>）发生了变化，但ThreadLocal类型的变量本身的确是一个，这才是本质！+
虽然ThreadLocal变量只有一个，各个线程共享，但是`线程内部维护一个ThreadLocalMap<Thread,T>，通过线程Id每个线程都维护唯一的一个变量`。

- CompletionService         : ExecutorService的扩展，可以获得线程执行结果的
- CountDownLatch            ：一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。 
- CyclicBarrier             ：一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 


### Struts和springmvc的区别，两者分别是多线程还是单线程的

### 一致性 hash 算法
1. 一般 hash 算法, Hash(图片名称) / N，N为服务器个数，分布均匀但是如果有台服务器挂了，就会让所有图片移动
2，一致性 hash 算法，Hash(服务器 IP 地址) % 2^32，找到服务器位置，Hash(图片名称) % 2^32，找到图片位置，顺时针缓存到最近服务器，
这样即使有一个服务器挂了，只会影响该服务器的图片寻找新的最近服务器。Hash 环倾斜，但是可能所有的服务器都在一般，这样距离就远了。
3. 虚拟节点，虚拟节点是实际节点(服务器)的复制点。使得 Hash 环分配均匀。



### Java8 新增了非常多的特性，我们主要讨论以下几个：
- Lambda 表达式 − Lambda允许把函数作为一个方法的参数（函数作为参数传递进方法中。->
- 方法引用 − 方法引用提供了非常有用的语法，可以直接引用已有Java类或对象（实例）的方法或构造器。与lambda联合使用，方法引用可以使语言
的构造更紧凑简洁，减少冗余代码。 `方法引用使用一对冒号(::)。`
- 函数式接口 - 函数式接口可以被隐式转换为lambda表达式。函数式接口可以现有的函数友好地支持 lambda。
- 默认方法 − 默认方法就是一个在`接口里面有了一个实现的方法。`
- 新工具 − 新的编译工具，如：Nashorn引擎 jjs、 类依赖分析器jdeps。
- Stream API −新添加的Stream API（java.util.stream） 把真正的`函数式编程风格引入到Java中。`
List<Integer> transactionsIds = 
widgets.stream()
             .filter(b -> b.getColor() == RED)
             .sorted((x,y) -> x.getWeight() - y.getWeight())
             .mapToInt(Widget::getWeight)
             .sum();
             
- Date Time API − `加强对日期与时间的处理。`
// 获取当前的日期时间
LocalDateTime currentTime = LocalDateTime.now();

- Optional 类 − Optional 类已经成为 Java 8 类库的一部分，用来解决空指针异常。`允许为 null`
- Nashorn, JavaScript 引擎 − Java 8提供了一个新的Nashorn javascript引擎，它允许我们在JVM上运行特定的javascript应用。
- concurrent 包修改 ConcurrentHashMap 大数组、HashEntry 里面值超过8个自动转为红黑树,Java 8在链表长度超过一定`阈值（8）`时
将链表（寻址时间复杂度为O(N)）转换为`红黑树`（寻址时间复杂度为O(log(N))）。

- Base64 在Java 8中，Base64编码已经成为Java类库的标准。Java 8 内置了 Base64 编码的编码器和解码器。
 // 使用基本编码
String base64encodedString = Base64.getEncoder().encodeToString("runoob?java8".getBytes("utf-8"));

### Jdk9 
jdk9将对Unsafe方法调整， sun.misc.Unsafe 将全部实现委托给 jdk.internal.misc.Unsafe
Unsafe 利用反射
- `对象的反序列化` 控制好字节，比如 int 4个字节
- `线程安全的直接获取内存` Unsafe的另外一个用途是线程安全的获取非堆内存。ByteBuffer函数也能使你安全的获取非堆内存或是DirectMemory，
但它不会提供任何线程安全的操作。你在进程间共享数据时使用Unsafe尤其有用。
FileChannel fc = new RandomAccessFile(counters, "rw").getChannel()
MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, 0, 1024);

Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
theUnsafe.setAccessible(true);
long value = UNSAFE.`getLongVolatile`(null, address);
UNSAFE.`compareAndSwapLong`(null, address, value, value + 1)

### Atomic
Java.util.concurrent.atomic包中几乎大部分类都采用了CAS操作
### CAS Compare And Swap
#### CAS 算法大致原理是
从最基础的 Java 中的 i++ 操作来说，`i++ 并非原子操作`，实质上相当于先读取 i 值，然后在内存中创建缓存变量保存 ++ 后结果，最后写会变
量 i；而在这期间 i 变量都可能被其他线程读或写，从而造成线程安全性问题
在对变量进行计算之前(如 ++ 操作)，首先读取原变量值，称为 `旧的预期值 A`，然后在更新之前再获取当前内存中的值，称为 `当前内存值 V`，如果
 A==V 则说明变量从未被其他线程修改过，此时将会写入新值 B，如果 A!=V 则说明变量已经被其他线程修改过，当前线程应当什么也不做；
 
#### openjdk 中 CAS 实现
翻了一下 AtomicInteger 的源码，发现其实质上都会调用到 Unsafe 类中的方法，而` Unsafe 中大部分方法是 native `的，也就是说实质使用
 JNI 上调用了 `C 来沟通底层硬件完成 CAS`；
 
#### CAS 缺点
##### ABA 问题
由于 CAS 设计机制就是获取某两个时刻(初始预期值和当前内存值)变量值，并进行比较更新，所以说如果在获取初始预期值和当前内存值这段时间
间隔内，`变量值由 A 变为 B 再变为 A`，那么对于 CAS 来说是不可感知的，但实际上变量已经发生了变化；解决办法是在每次获取时`加版本号`，
并且每次更新对版本号 +1，这样当发生 ABA 问题时通过版本号可以得知变量被改动过
JDK 1.5 以后的 `AtomicStampedReference 类就提供了此种能力`，其中的 `compareAndSet `方法就是 首先检查当前引用是否等于预期引用，
并且当前标志是否等于预期标志，如果全部相等，则以原子方式将该引用和该标志的值设置为给定的更新值。
##### 循环时间长开销大
所谓循环时间长开销大问题就是当 CAS 判定变量被修改了以后则放弃本次修改，`但往往为了保证数据正确性该计算会以循环的方式再次发起 CAS，`
如果多次 CAS 判定失败，则会产生`大量的时间消耗和性能浪费`；如果JVM能支持处理器提供的pause指令那么效率会有一定的提升，pause指令有两个
作用，第一它可以延迟流水线执行指令（de-pipeline）,使CPU不会消耗过多的执行资源，延迟的时间取决于具体实现的版本，在一些处理器上延迟
时间是零。第二它可以避免在退出循环的时候因内存顺序冲突（memory order violation）而引起CPU流水线被清空（CPU pipeline flush），
从而提高CPU的执行效率。
##### 只能保证一个共享变量的原子操作
CAS 只对单个共享变量有效，当操作涉及跨`多个共享变量时 CAS 无效`；
比如有两个共享变量i＝2,j=a，合并一下ij=2a，然后用CAS来操作ij。
从 JDK 1.5开始提供了 `AtomicReference 类来保证引用对象之间的原子性`，你可以把`多个变量放在一个对象`里来进行 CAS 操作

### volatile
volatile可以保证`线程可见性`且提供了一定的`有序性`，`但是无法保证原子性`。在JVM底层volatile是采用“内存屏障”来实现的。内存屏障(Memory Barriers)是一组处理器指令，用于实现对内存操作的顺序限制。
上面那段话，有两层语义
- 保证可见性、不保证原子性
- 禁止指令重排序
synchronized，只有在某些场合才能够使用volatile。使用它必须满足如下两个条件：
- 对变量的写操作不依赖当前值；
- 该变量没有包含在具有其他变量的不变式中。
volatile经常用于两个两个场景：`状态标记、double check`


### 32的jvm可以跑多大的程序？ 
`所谓32位处理器就是一次只能处理32位，也就是4个字节的数据，而64位处理器一次就能处理64位，即8个字节的数据。`
理论上来说32位的JVM有4G的堆大小限制。但是因为各种条件限制比如交换区，内核地址空间使用，内存碎片，虚拟管理机的管理开销，实际上可用的
堆的大小远远比理论上的4G要少。 
在32位windows的机器上，堆最大可以达到1.4G至1.6G。 
在32位solaris的机器上，堆最大可以达到2G 
而在64位的操作系统上，32位的JVM，堆大小可以达到4G 


### 维持一个固定大小的最大最小堆就是维持一个固定大小的 Array


### Mybatis 使用
##### {}和${}的区别是什么？
{}是预编译处理，${}是字符串替换。
Mybatis在处理#{}时，会将sql中的`#{}替换为?号`，调用PreparedStatement的set方法来赋值；
Mybatis在处理${}时，就是把`${}替换成变量的值`。
使用#{}可以有效的`防止SQL注入`，提高系统安全性。

#### 正则表达式
\       将下一字符标记为特殊字符、文本、反向引用或八进制转义符。例如，"n"匹配字符"n"。"\n"匹配换行符。序列"\\"匹配"\"，"\("匹配"("。
*       `零次或多次`匹配前面的字符或子表达式。例如，zo* 匹配"z"和"zoo"。* 等效于 {0,}。
+       `一次或多次`匹配前面的字符或子表达式。例如，"zo+"与"zo"和"zoo"匹配，但与"z"不匹配。+ 等效于 {1,}。
?       `零次或一次`匹配前面的字符或子表达式。例如，"do(es)?"匹配"do"或"does"中的"do"。? 等效于 {0,1}。
?       当此字符紧随任何其他限定符（*、+、?、{n}、{n,}、{n,m}）之后时，匹配模式是"`非贪心的`"。"非贪心的"模式匹配搜索到的、尽可能短的字符串，而默认的"贪心的"模式匹配搜索到的、尽可能长的字符串。例如，在字符串"oooo"中，"o+?"只匹配单个"o"，而"o+"匹配所有"o"。
\d      数字字符匹配。等效于 [0-9]。
\s      匹配任何空白字符，包括空格、制表符、换页符等。与 [ \f\n\r\t\v] 等效。
\S      匹配任何非空白字符。与 [^ \f\n\r\t\v] 等效。
\w      匹配任何字类字符，包括下划线。与"[A-Za-z0-9_]"等效。
\W      与任何非单词字符匹配。与"[^A-Za-z0-9_]"等效。











