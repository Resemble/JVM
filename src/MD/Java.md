# Java 相关琐碎知识

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


### Java 集合框架基本接口有哪些
Collection：代表一组对象，每一个对象都是它的子元素。
    Set：不包含重复元素的Collection。
    List：有顺序的collection，并且可以包含重复元素。
    Queue:队列
Map：可以把键(key)映射到值(value)的对象，键不能重复。


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
1、 通过一个类的`全限定名来获取定义此类的二进制字节流。`（可以是class文件，可以是jar包等等）
我们先说第一步，通过一个类的全限定名来获取此类的二进制字节流。这个说法实际上是很大的一个操作的空间，这也是各种Java技术能够实现的基础。
比如，我们可以通过zip包中获取，那么就有了后来的jar,war，我们还可以通过网络中获取这个类，这就是applet，甚至动态代理，
运行时在生成特定的class二进制流，或者由其他文件生成，比如JSP，由JSP文件生成对应的Class文件。
 如Object类， 在源文件中的全限定名是java.lang.Object 。 而class文件中的全限定名是将点号替换成“/” 。 例如， Object类在class文件中的全限定名是 java/lang/Object 
2、 将这个字节流所代表的`静态存储结构转化为方法区的运行时数据结构`。
3、 在`Java堆中生成一个代表这个类的java.lang.Class对象`，作为方法区这些数据的访问入口。
加载阶段即可以使用系统提供的类加载器在完成，也可以由用户自定义的类加载器来完成。加载阶段与连接阶段的部分内容(如一部分字节码文件格式验证动作)是交叉进行的，加载阶段尚未完成，连接阶段可能已经开始。
加载阶段完成了之后，二进制字节流就按照虚拟机所需求的格式存储在方法区里面，然后初始化一个class实例，并且这个实例是放在方法区而不是java堆里面，然后这个实例是用来作为方法区里面数据的访问入口的。


- 验证
检查待加载的 class 文件的正确性
验证是连接阶段的第一步，这一阶段的目的是为了确保Class文件的字节流中包含的信息符合当前虚拟机的要求，并且不会危害虚拟机自身的安全。
Java语言本身是相对安全的语言，使用Java编码是无法做到如访问数组边界以外的数据、将一个对象转型为它并未实现的类型等，如果这样做了，编译器将拒绝编译。但是，Class文件并不一定是由Java源码编译而来，可以使用任何途径，包括用十六进制编辑器(如UltraEdit)直接编写。如果直接编写了有害的“代码”(字节流)，而虚拟机在加载该Class时不进行检查的话，就有可能危害到虚拟机或程序的安全。
文件格式验证: 是要验证字节流是否符合Class文件格式的规范，并且能被当前版本的虚拟机处理。
元数据验证: 是对字节码描述的信息进行语义分析，以保证其描述的信息符合Java语言规范的要求
字节码验证: 主要工作是进行数据流和控制流分析，保证被校验类的方法在运行时不会做出危害虚拟机安全的行为
符号引用验证: 发生在虚拟机将符号引用转化为直接引用的时候，这个转化动作将在“解析阶段”中发生。验证符号引用中通过字符串描述的权限定名是否能找到对应的类；在指定类中是否存在符合方法字段的描述符及简单名称所描述的方法和字段；符号引用中的类、字段和方法的访问性(private、protected、public、default)是否可被当前类访问

- 准备
准备阶段是`为类的静态变量分配内存并将其初始化为默认值`，这些内存都将在`方法区中进行分配。`准备阶段不分配类中的实例变量的内存，
实例变量将会在对象实例化时随着对象一起分配在Java堆中。
 public static int value=123;//在准备阶段value初始值为0 。在初始化阶段才会变为123 
 
-解析
解析阶段是虚拟机将常量池内的`符号引用替换为直接引用`的过程。
符号引用（Symbolic Reference）：符号引用以一组符号来描述所引用的目标，`符号可以是任何形式的字面量，只要使用时能无歧义地定位到目标即可`。符号引用与虚拟机实现的内存布局无关，引用的目标并不一定已经加载到内存中。
直接引用（Direct Reference）：直接引用可以是直接指向`目标的指针、相对偏移量或是一个能间接定位到目标的句柄。`直接引用是与虚拟机实现的内存布局相关的，如果有了直接引用，那么引用的目标必定已经在内存中存在。

- 初始化
类初始化是类加载过程的最后一步，前面的类加载过程，除了在加载阶段用户应用程序可以通过自定义类加载器参与之外，其余动作完全由虚拟机主导和控制。到了初始化阶段，才真正开始执行类中定义的Java程序代码。
收集类中的`所有类变量的赋值动作和静态语句块(static{}块)`中的语句合并产生的。
初始化阶段是执行类构造器<clinit>（）方法的过程。类构造器<clinit>（）方法是由编译器自动收藏类中的所有类变量的赋值动作和静态语句块(static块)中的语句合并产生
`当初始化一个类的时候，如果发现其父类还没有进行过初始化，则需要先触发其父类的初始化`
`虚拟机会保证一个类的<clinit>（）方法在多线程环境中被正确加锁和同步`


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

### servlet
servlet：servlet是一种运行服务器端的java应用程序，具有独立于平台和协议的特性，并且可以动态的生成web页面，它工作在客户端请求与
服务器响应的中间层。Servlet 的主要功能在于交互式地浏览和修改数据，生成动态 Web 内容。这个过程为：
1) 客户端发送请求至服务器端；
2) 服务器将请求信息发送至 Servlet；
3) Servlet 生成响应内容并将其传给服务器。响应内容动态生成，通常取决于客户端的请求；
4) 服务器将响应返回给客户端。
### 说出Servlet的生命周期  5阶段                                                                
加载 -> 创建实例 -> 初始化(init) -> 处理客户端请求(service) -> 销毁(destroy)
Servlet被服务器`实例化`后，容器运行其`init`方法，请求到达时运行其`service`方法，service方法自动派遣运行与请求对应的doXXX方法
（doGet，doPost）等，当服务器决定将实例销毁的时候调用其`destroy方`法。
#### 并说出Servlet和CGI的区别
与cgi的区别在于servlet处于服务器进程中，它通过`多线程方式运行其service方法`，一个实例可以服务于多个请求，并且其实例一般不会销毁，
而`CGI对每个请求都产生新的进程`，服务完成后就销毁，所以效率上低于servlet
### filter：
filter是一个可以复用的代码片段，可以用来转换HTTP请求、响应和头信息。Filter不像Servlet，它不能产生一个 请求或者响应，它只是修改对
某一资源的请求，或者修改从某一的响应。Servlet中的过滤器Filter是实现了javax.servlet.Filter接口的服务器端程序，主要的用途是
过滤字符编码、做一些业务逻辑判断等。

#### 但什么是 servlet？
作为一名专业编程人员，您碰到的大多数 Java servlet 都是为`响应 Web 应用程序上下文中的 HTTP 请求而设计的`。因此，javax.servlet 和 
javax.servlet.http 包中特定于 HTTP 的类是您应该关心的。
容器（如 Tomcat）将为 servlet 管理运行时环境。
下面是一个典型场景：
用户在浏览器中输入一个 URL。Web 服务器配置文件确定该 URL 是否指向一个由运行于服务器上的 servlet 容器所管理的 servlet。
如果还没有创建该 servlet 的一个实例（一个应用程序只有一个 servlet 实例），那么该容器就加载该类，并将之实例化。
该容器调用 servlet 上的 init()。
该容器调用 servlet 上的 service()，并在包装的 HttpServletRequest 和 HttpServletResponse 中进行传递。
该 servlet 通常访问请求中的元素，代表其他服务器端类来执行所请求的服务并访问诸如数据库之类的资源，然后使用该信息填充响应。
如果有必要，在 servlet 的有用生命结束时，该容器会调用 servlet 上的 destroy() 来清除它。
#### 如何“运行”servlet
“运行”servlet 就像运行 Java 程序一样。一旦配置了容器，使容器了解 servlet，并知道某些 URL 会致使容器调用该 servlet，该容器就将按照
预定的次序调用生命周期方法。因此，运行 servlet 主要是指正确配置它，然后将浏览器指向正确的 URL。当然，servlet 中的代码正是发现有趣
的业务逻辑的地方。
##### servlet 的替代品
servlet 不是服务于 Web 页面的惟一方式。满足该目的的最早技术之一是公共网关接口（CGI），但那样就要为每个请求派生不同的进程，因而会影
响效率。还有专用服务器扩展，如 Netscape Server API（NSAPI），但那些都是完全专用的。在 Microsoft 的世界里，有活动服务器页面（ASP）标准。



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
可保持原来的流程继续向下执行，被下一个filter, servlet接收。项目中用了 OncePerRequestFilter 顾名思义，他能够确保在一次请求只通过一次filter，而不需要重复执行。
2. 监听器（Listener）：Java的监听器，也是系统级别的监听。监听器随web应用的启动而启动。Java的监听器在c/s模式里面经常用到，它
会对特定的事件产生产生一个处理。`监听在很多模式下用到，比如说观察者模式，就是一个使用监听器来实现的`，在比如`统计网站的在线人数`。
又比如struts2可以用监听来启动。Servlet监听器用于监听一些重要事件的发生，监听器对象可以在事情发生前、发生后可以做一些必要的处理。
3. 拦截器（Interceptor）：java里的拦截器提供的是非系统级别的拦截，也就是说，`就覆盖面来说，拦截器不如过滤器强大`，但是更有针对性。
Java中的拦截器是`基于Java反射机制实现的，更准确的划分，应该是基于JDK实现的动态代理`。它依赖于具体的接口，在运行期间动态生成字节码。
拦截器是动态拦截Action调用的对象，它提供了一种机制可以使开发者在`一个Action执行的前后执行一段代码`，也可以在一个Action执行前阻止其
执行，同时也提供了一种可以提取Action中可重用部分代码的方式。在AOP中，拦截器用于在某个方法或者字段被访问之前，进行拦截然后再之前或
者之后加入某些操作。java的拦截器主要是用在插件上，扩展件上比如 Hibernate Spring Struts2等，有点类似面向切片的技术，在用之前先要在
配置文件即xml，文件里声明一段的那个东西。

#### Filter有如下几个用处。
在HttpServletRequest到达Servlet之前，拦截客户的HttpServletRequest。
根据需要检查HttpServletRequest，也可以修改HttpServletRequest头和数据。
在HttpServletResponse到达客户端之前，拦截HttpServletResponse。
根据需要检查HttpServletResponse，也可以修改HttpServletResponse头和数据。
#### Filter有如下几个种类。
用户授权的Filter：Filter负责检查用户请求，根据请求过滤用户非法请求。
日志Filter：详细记录某些特殊的用户请求。
负责解码的Filter：包括对非标准编码的请求解码。
能改变XML内容的XSLT Filter等。
Filter可负责拦截多个请求或响应；一个请求或响应也可被多个请求拦截。

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




### 一致性 hash 算法
1. 一般 hash 算法, Hash(图片名称) / N，N为服务器个数，分布均匀但是如果有台服务器挂了，就会让所有图片移动
2，一致性 hash 算法，Hash(服务器 IP 地址) % 2^32，找到服务器位置，Hash(图片名称) % 2^32，找到图片位置，顺时针缓存到最近服务器，
这样即使有一个服务器挂了，只会影响该服务器的图片寻找新的最近服务器。Hash 环倾斜，但是可能所有的服务器都在一般，这样距离就远了。
3. 虚拟节点，虚拟节点是实际节点(服务器)的复制点。使得 Hash 环分配均匀。




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
JDK 1.5 以后的 `AtomicStampedReference 类就提供了此种能力`，其中的 `compareAndSet `方法就是 首先检查当前引用是否等于`预期引用`，
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
- `对变量的写操作不依赖当前值；`
- `该变量没有包含在具有其他变量的不变式中。`
volatile经常用于两个两个场景：`状态标记、double check`
第一个条件的限制使volatile变量不能用作线程安全计数器。`虽然增量操作（x++）看上去类似一个单独操作，实际上它是一个由读取－修改－写入
操作序列组成的组合操作，`必须以原子方式执行，而volatile不能提供必须的原子特性。实现正确的操作需要使x 的值在操作期间保持不变，而volatile
变量无法实现这点。
例如假如线程1，线程2 在进行read,load 操作中，发现主内存中count的值都是5，那么都会加载这个最新的值，
在线程1堆count进行修改之后，会write到主内存中，主内存中的count变量就会变为6；线程2由于已经进行read,load操作，在进行运算之后，
也会更新主内存count的变量值为6；导致两个线程及时用volatile关键字修改之后，还是会存在并发的情况。
每一个线程运行时都有一个线程栈，线程栈保存了线程运行时候变量值信息。当线程访问某一个对象时候值的时候，首先通过对象的引用找到对应在
堆内存的变量的值，然后把堆内存变量的具体值load到线程本地内存中，建立一个变量副本，之后线程就不再和对象在堆内存变量值有任何关系，
而是直接修改副本变量的值，在修改完之后的某一个时刻（线程退出之前），自动把线程变量副本的值回写到对象在堆中变量。这样在堆中的对象的值
就产生变化了。

而volatile关键字的作用之一便是系统每次用到被它修饰过的变量时都是`直接从主内存当中提取`，而`不是从Cache中提取`，同时对于该变量的更改会
`马上刷新回主存`，以使得各个线程取出的值相同，这里的Cache可以理解为线程的工作内存。

### 编译器的优化
在本次线程内, 当读取一个变量时，为提高存取速度，编译器优化时有时会先把变量读取到一个寄存器中；以后，再取变量值时，就直接从寄存器中取值；当变量值在本线程里改变时，会同时把变量的新值copy到该寄存器中，以便保持一致。
当变量在因别的线程等而改变了值，该寄存器的值不会相应改变，从而造成应用程序读取的值和实际的变量值不一致。
当该寄存器在因别的线程等而改变了值，原变量的值不会改变，从而造成应用程序读取的值和实际的变量值不一致。

### 什么是泛型、为什么要使用以及泛型擦除   
泛型，即“参数化类型”。 创建集合时就指定集合元素的类型，该集合只能保存其指定类型的元素，避免使用强制类型转换。 Java编译器生成的字节码
是不包涵泛型信息的，泛型类型信息将在编译处理是被擦除，这个过程即类型擦除。泛型擦除可以简单的理解为将泛型java代码转换为普通java代码，
只不过编译器更直接点，将泛型java代码直接转换成普通java字节码。 
类型擦除的主要过程如下：
 1）.将所有的泛型参数用其最左边界（最顶级的父类型）类型替换。
  2）.移除所有的类型参数。
  

#### Java中的集合类及关系图   
- List和Set继承自Collection接口。 
- Set无序不允许元素重复。HashSet和TreeSet是两个主要的实现类。 
- List有序且允许元素重复。ArrayList、LinkedList和Vector是三个主要的实现类。 
- Map也属于集合系统，但和Collection接口没关系。Map是key对value的映射集合，其中key列就是一个集合。key不能重复，
但是value可以重复。HashMap、TreeMap和Hashtable是三个主要的实现类。 SortedSet和SortedMap接口对元素按指定规则排序，
SortedMap是对key列进行排序。  
  
#### ArrayList和vector区别    
ArrayList和Vector都实现了List接口，都是通过数组实现的。 
Vector是线程安全的，而ArrayList是非线程安全的。 List第一次创建的时候，会有一个初始大小，随着不断向List中增加元素，
当List 认为容量不够的时候就会进行扩容。`Vector缺省情况下自动增长原来一倍的数组长度，ArrayList增长原来的50%。  `
  
#### Collection和Collections的区别    
java.util.`Collection 是一个集合接口`。它提供了对集合对象进行基本操作的通用接口方法。
Collection接口在Java 类库中有很多具体的实现。Collection接口的意义是为各种具体的集合提供了最大化的统一操作方式。 
java.util.`Collections 是一个包装类`。它包含有各种有关集合操作的静态多态方法。此类不能实例化，就像一个工具类，服务于Java的Collection框架。  
Collections.sort()  
  
### cookie 和 session 的区别
1. cookie 机制采用客户端保持状态的方案，session 采用的是在服务器保持状态的方案
2. cookie 安全性不够。
3. cookie 性能更高一些。由于 session 会在一定时间内保存在服务器上，因此当访问量多时，会降低服务器的性能。
4. 单个 cookie 保存的数据不能超过 4 KB，很多浏览器都限制一个站点最多保存20个 cookie ；而 session 不存在此问题。

### 守护线程(Daemon)
守护线程又称服务进程或后台线程，是指程序运行时在后台提供一种通用服务的线程，这种线程并不属于程序中不可或缺的部分。

  
  
### 32的jvm可以跑多大的程序？ 
`所谓32位处理器就是一次只能处理32位，也就是4个字节的数据，而64位处理器一次就能处理64位，即8个字节的数据。`
理论上来说32位的JVM有4G的堆大小限制。但是因为各种条件限制比如交换区，内核地址空间使用，内存碎片，虚拟管理机的管理开销，实际上可用的
堆的大小远远比理论上的4G要少。 
在32位windows的机器上，堆最大可以达到1.4G至1.6G。 
在32位solaris的机器上，堆最大可以达到2G 
而在64位的操作系统上，32位的JVM，堆大小可以达到4G 


### 维持一个固定大小的最大最小堆就是维持一个固定大小的 Array


### string 类能不能被继承
答案： 不可以，因为String类有final修饰符，而final修饰的类是不能被继承的，实现细节不允许改变。
public final class String implements java.io.Serializable, Comparable<String>, CharSequence 
### final
根据程序上下文环境，Java关键字final有“这是无法改变的”或者“终态的”含义，它可以修饰非抽象类、非抽象类成员方法和变量。你可能出于两种理解而需要阻止改变：设计或效率。 
　　final类不能被继承，没有子类，final类中的方法默认是final的。 
　　final方法不能被子类的方法覆盖，但可以被继承。 
　　final成员变量表示常量，只能被赋值一次，赋值后值不再改变。 
　　final不能用于修饰构造方法。 
　　注意：父类的private成员方法是不能被子类方法覆盖的，因此private类型的方法默认是final类型的。

如果一个类不允许其子类覆盖某个方法，则可以把这个方法声明为final方法。 
　　使用final方法的原因有二： 
　　第一、把方法锁定，防止任何继承类修改它的意义和实现。 
　　第二、高效。编译器在遇到调用final方法时候会转入内嵌机制，大大提高执行效率。
### 还有什么不可以继承
public final class Byte 
public final class Character 
public static final class Character.UnicodeBlock 
public final class Class<T> 
public final class Compile 
public final class Double 
public final class Float 
public final class Integer 
public final class Long 
public final class Math 
public final class ProcessBuilder 
public final class RuntimePermission 
public final class Short 
public final class StackTraceElement 
public final class StrictMath 
public final class String 
public final class StringBuffer 
public final class StringBuilder 
public final class System 
public final class Void

### 关于String类，要了解常量池的概念
String s = new String(“xyz”);  //创建了几个对象
答案： 1个或2个， 如果”xyz”已经存在于常量池中，则只在堆中创建”xyz”对象的一个拷贝，否则还要在常量池中在创建一份
String s = "a"+"b"+"c"+"d"; //创建了几个对象
答案： 这个和JVM实现有关， 如果常量池为空，可能是1个也可能是7个等
### 相关类： StringBuffer, StringBuilder
从源代码的角度聊聊java中StringBuffer、StringBuilder、String中的字符串拼接
String为immutable, 不可更改的，每次String对象做累加时都会创建StringBuilder对象， 效率低下。
// 程序编译期即加载完成对象s1为"ab"，JVM自有优化， 效率并不差
String s1 = "a" + "b";  
// 这种方式，JVM会先创建一个StringBuilder，然后通过其append方法完成累加操作，比较耗资源
// 所以在循环中做字符串累加赋值时应当使用StringBuilder或StringBuffer类
String s1 = "a";
String s2 = "b"; 
String s3 = s1 + s2; // 等效于 String s3 = (new StringBuilder(s1)).append(s2).toString();
StringBuffer是线程安全的 

`String不是基本数据类型，而是一个类（class）`，是Java编程语言中的字符串。String对象是char的有序集合，并且该值是不可变的。
因为java.lang.String类是final类型的，因此不可以继承这个类、不能修改这个类。为了提高效率节省空间，我们应该用StringBuffer类。
##### Java的8大基本数据类型分别是：
整数类 byte, short, int, long
浮点类 double, float
逻辑类 boolean
文本类 char


### JAVA死锁和避免死锁
  概念：两个或多个线程一直在相互等待其他线程完成而使得所有线程都始终处在阻塞的状态
死锁产生的四个必要条件。
1>资源`互斥`使用性，即当资源被一个线程使用(占有)时，别的线程不能使用
2>资源的`不可抢占`，资源请求者不能强制从资源占有者手中夺取资源，资源只能由资源占有者主动释放。
3>执行者`请求和保持`，即当资源请求者在请求其他的资源的同时保持对原有资源的占用。
4>执行者`循环等待`，即存在一个等待队列：P1占有P2的资源，P2占有P3的资源，P3占有P1的资源。这样就形成了一个等待环路。
查看死锁：
1>使用JDK给我们的的工具JConsole，可以通过打开cmd然后输入`jconsole`打开。 JConsole 里面有检查死锁这个按钮
2>直接使用JVM自带的命令
1）首先通过 `jps `命令查看需要查看的Java进程的vmid
2）然后利用 jstack 查看该进程中的堆栈情况，在cmd中输入 `jstack -l 7412。`
 
### 虚拟机性能监控与故障处理工具
- JConsole Java 监视与管理控制台
- jps 虚拟机进程状态工具
- jstack Java 堆栈跟踪工具 
jstack（Stack Trace for Java）命令用于生成虚拟机当前时刻的线程快照。线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈的集合，
生成线程快照的目的主要是定位线程长时间出现停顿的原因，如`线程间死锁、死循环、请求外部资源导致的长时间等待等都是导致线程长时间停顿的原因。`
线程出现停顿的时候通过jstack来查看各个线程的调用堆栈，就可以知道没有响应的线程到底在后台做些什么事情，或者在等待些什么资源。
- jstat：（JVM Statistics Monitoring Tool）虚拟机统计信息监控工具
jstat使用于监视虚拟机各种运行状态信息的命令行工具。它可以显示本地或者远程（需要远程主机提供RMI支持）虚拟机进程中的类信息、内存、
垃圾收集、JIT编译等运行数据，在没有GUI，只提供了纯文本控制台环境的服务器上，它将是运行期间定位虚拟机性能问题的首选工具。
jstat -gc 2764 250 20  监视Java堆状况，包括Eden区、两个Survivor区、、老年代、永久带等的容量、已用空间、GC时间合计等信息
- jinfo：Java配置信息工具
jinfo（Configuration Info for Java）的作用是实时地查看和调整虚拟机各项参数。使用jps命令的-v可以查看虚拟机启动时显式指定的参数列表，但如果想知道未被显式指定的参数的系统默认值，可以使用jinfo的-flag选项进行查询，jinfo还可以使用-sysprops选项把虚拟机进程的System.getProperties()的内容打印出来。
- jmap：Java内存映像工具
jmap（Memory Map for Java）命令用于生成堆转储快照。如果不使用jmap命令，要想获取Java堆转储，可以使用“-XX:+HeapDumpOnOutOfMemoryError”参数，可以让虚拟机在OOM异常出现之后自动生成dump文件，Linux命令下可以通过kill -3发送进程退出信号也能拿到dump文件。
jmap的作用并不仅仅是为了获取dump文件，它还可以查询finalize执行队列、Java堆和永久代的详细信息，如空间使用率、当前使用的是哪种收集器等。和jinfo一样，jmap有不少功能在Windows平台下也是受限制的，除了生成dump文件的-dump选项和用于查看每个类的实例、空间占用统计的-histo选项在所有操作系统都提供之外，其余选项都只能在Linux和Solaris系统下使用。
- Visual VM
这个是到目前为止随JDK发布的功能最为强大的运行监视和故障处理工具，除了最基本的运行监视、 故障处理外，还有性能分析的功能，且十分强大。Visual VM还有一个很大的优点，它对应用程序的实际性能影响很小，使得它可以直接应用在生产环境中。



### Java中equals和==的区别
java中的数据类型，可分为两类： 
1.`基本数据类型`，也称原始数据类型。byte,short,char,int,long,float,double,boolean 
他们之间的比较，应用双等号`（==）,比较的是他们的值`。 
2.复合数据类型(类) 
`当他们用（==）进行比较的时候，比较的是他们在内存中的存放地址`，所以，除非是同一个new出来的对象，他们的比较后的结果为true，否则
比较后结果为false。 JAVA当中所有的类都是继承于Object这个基类的，在Object中的基类中定义了`一个equals的方法，这个方法的初始行为是
比较对象的内存地 址，`但在一些类库当中这个方法`被覆盖掉`了，如`String,Integer,Date`在这些类当中equals有其自身的实现，而不再是比较类
在堆内存中的存放地址了。
对于复合数据类型之间进行equals比较，在没有覆写equals方法的情况下，他们之间的比较还是基于他们在内存中的存放位置的地址值的，因为
Object的equals方法也是用双等号（==）进行比较的，所以比较后的结果跟双等号（==）的结果相同。
 
### JAVA中重写equals()方法为什么要重写hashcode()方法?
注意：当此方法被重写时，通常有必要重写 hashCode 方法，以维护 hashCode 方法的常规协定，`该协定声明相等对象必须具有相等的哈希码`。
如果不重写equals，那么比较的将是对象的引用是否指向同一块内存地址，重写之后目的是为了比较两个对象的value值是否相等。特别指出利用equals比较八大包装对象
（如int，float等）和String类（因为该类已重写了equals和hashcode方法）对象时，默认比较的是值，在比较其它自定义对象时都是比较的引用地址
`hashcode是用于散列数据的快速存取，如利用HashSet/HashMap/Hashtable类来存储数据时，都是根据存储对象的hashcode值来进行判断是否相同的。`
这样如果我们对一个对象重写了euqals，意思是只要对象的成员变量值都相等那么euqals就等于true，但不重写hashcode，那么我们再new一个新的对象，
当原对象.equals（新对象）等于true时，两者的hashcode却是不一样的，由此将产生了理解的不一致，`如在存储散列集合时（如Set类），
将会存储了两个值一样的对象，导致混淆，因此，就也需要重写hashcode()`


### 自动装箱与拆箱    
装箱：`将基本类型用它们对应的引用类型包装起来`；  Integer i = 100;  系统为我们执行了：Integer i = Integer.valueOf(100);
拆箱：`将包装类型转换为基本数据类型`；  Integer -> int
Java使用自动装箱和拆箱机制，节省了常用数值的内存开销和创建对象的开销，提高了效率，`由编译器来完成，编译器会在编译期根据语法决定是否进行装箱和拆箱动作`。
 
### 内存溢出和内存泄漏的区别
内存溢出是指程序在申请内存时，没有足够的内存空间供其使用，出现out of
memory。
内存泄漏是指分配出去的内存不再使用，但是无法回收。 
 
### char可以存储汉字吗 
char是按照字符存储的，不管英文还是中文，固定占用占用2个字节，用来储存Unicode字符。范围在0-65536。
unicode编码字符集中包含了汉字，所以，char型变量中当然可以存储汉字啦。不过，如果某个特殊的汉字没有
被包含在unicode编码字符集中，那么，这个char型变量中就不能存储这个特殊汉字。
1. unicode编码固定占用两个字节，所以，char类型的变量也是占用两个字节。Unicode（统一码、万国码、
单一码）是一种在计算机上使用的字符编码。它为每种语言中的每个字符设定了统一并且唯一的二进制编码，
以满足跨语言、跨平台进行文本转换、处理的要求。传统的编码方式存在的缺陷：
       ①在不同的编码方案下有可能对应不同的字母
       ②采用大字符集的语言其编码长度可能不同
目前的用于实用的 Unicode 版本对应于 UCS-2，使用16位的编码空间。也就是每个字符占用2个字节。
2. 不同的看编码占据字节数也不同：utf-32中文是4字节；
    utf-8码的中文都是3字节的，字母是1字节，因为utf-8是变长编码；
    而 gbk/gbk18030 中文是2字节的，英文是1个字节。

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


#### 编码
- UTF-8：
UTF-8是Unicode的实现方式之一。
Unicode TransformationFormat-8bit，允许含BOM，但通常不含BOM。是用以解决国际上字符的一种多字节编码，它`对英文
使用8位（即一个字节），中文使用24位（三个字节）来编码`。UTF-8包含全世界所有国家需要用到的字符，是国际编码，通用性强。
UTF-8编码的文字可以在各国支持UTF8字符集的浏览器上显示。如，如果是UTF8编码，则在外国人的英文IE上也能显示中文，他们
无需下载IE的中文语言支持包。
- GBK
GBK的`中文编码是双字节来表示的`，`英文编码是用ASC||码表示的，既用单字节表示，GBK编码表中也有英文字符的双字节表示形式`，所以英文字母
可以有2中GBK表示方式。为区分中文，将其最高位都定成1。英文单字节最高位都为0。当用GBK解码时，若高字节最高位为0，则用ASC||码表解码；
若高字节最高位为1，则用GBK编码表解码
byte[] bb="hello".getBytes("gb2312"); 
若byte[] bb＝"ｈｅｌｌｏ".getBytes("gb2312");(注意是全角方式下)，bb字节数组的长度就会是10了。
因此：gb2312中的所有字符都是用二个字节表示（是因为：它的字符都是全角方式）。gb2312中不会对半角方式下的字符编码的
（这是ASCII码的事情），因此：在getBytes("gb2312");转换时，半角的字符都是一个字节，全角的同样字符才是两个字节。
- ASCII码
每一个二进制位（bit）有0和1两种状态，因此八个二进制位就可以组合出256种状态，这被称为一个字节（byte）。也就是说，一个字节一共可以用来
表示256种不同的状态，每一个状态对应一个符号，就是256个符号，从0000000到11111111。
- Unicode
如果有一种编码，将世界上所有的符号都纳入其中。每一个符号都给予一个独一无二的编码，那么乱码问题就会消失。这就是Unicode，就像它的名字都表示的，这是一种所有符号的编码。
- Unicode的问题
这里就有两个严重的问题，第一个问题是，如何才能区别Unicode和ASCII？计算机怎么知道三个字节表示一个符号，而不是分别表示三个符号呢？第二个问题是，我们已经知道，英文字母只用一个字节表示就够了，如果Unicode统一规定，每个符号用三个或四个字节表示，那么每个英文字母前都必然有二到三个字节是0，这对于存储来说是极大的浪费，文本文件的大小会因此大出二三倍，这是无法接受的。

### 字节流(InputStream)和字符流(Reader)
1、 比特、字节和字符的定义
     这个很基础，但是在开头说一下还是有必要的
     比特：bit(binarydigit),信息的基本单元。只有两种取值：0或者1.
     字节：通常由8比特组成。由于通常用来编辑计算机上文本的单个字符，所以字节是很多的计算机架构中的内存的最小可寻址单元
     字符：信息单元；大概相当于1个字母或者符号等。比如数字，字母，标点符号等。英文字符一般由1个字节组成。汉字一般由两个字节组成
 
2、 字节流和字符流的区别？
所谓流就是连续访问文件的一种方式。`字节流就是一个字节一个字节访问文件`。字节流适合除了文本文件外的任何类型的文件。
例如，如果文件使用了unicode编码，每个字符使用的是两个字节表示，那么字节流就会将这两个字节流单独对待，
导致的结果是你必须在后续自己去转换（成原来的字符）
类似地，`字符流就是一个字符一个字符的读取文件`。为了工作正常，`字符流需要提供文件的编码`。
3、 字节流和字符流分别对应的类
     `所有字节流类都是InputStream或OutputStream的子类。`最主要的两个是FileInputStream和FileOutputStream。    
    什么时候不适用字节流？
       `字节流代表的是一种低级别的I/O,所以应该尽量避免使用。`尤其是文本文件，包含的是字符，最好的方式是使用字符流。字节流只考虑用在最原始的I/O上。但是字节流是所有其他流的基础。 
字符流：
   Java平台使用Unicode协议保存字符值。字符流I/O自动在这些内部格式和本地字符集间进行转换。
  所有的字符流类都是Reader和Writer的子类。
字符流经常会“封装”字节流。字符流使用字节流来操作物理I/O，然后字符流执行字节和字符间的转换。比如，FileReader使用FileInputStream、FileWriter使用FileOutputStream。
4、 字节流和字符流之间的转换
使用InputStreamReader可以将字节流转换成字符流；
使用OutputStreamWriter类可以将字符流转换成字节流。
当你创建InputStreamReader和OutputStreamWriter对象，你可以指定你想要转换的编码。比如为了将一个编码为UTF8的文件转换成Unicode，创建InputStreamReader如下：
           FileInputStream fis = new FileInputStream("test.txt");
InputStreamReader isr = newInputStreamReader(fis, "UTF8");
    如果忽略，就会使用默认的编码。可以通过如下获取使用的编码：
               InputStreamReader defaultReader = new InputStreamReader(fis);
                                                       String defaultEncoding =defaultReader.getEncoding();

字节流继承于InputStream OutputStream
字符流继承于InputStreamReader OutputStreamWriter
字符流使用了缓冲区 (buffer),而字节流没有使用缓冲区
`底层设备永远只接受字节数据`
字符是字节通过不同的编码的包装
字符向字节转换时，要注意编码的问题

