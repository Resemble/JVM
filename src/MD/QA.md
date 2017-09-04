## 简答题
#### GC线程是否为守护线程？（）
答案：是
解析：线程分为守护线程和非守护线程（即用户线程）。
只要当前JVM实例中尚存在任何一个非守护线程没有结束，守护线程就全部工作；只有当最后一个非守护线程结束时，守护线程随着JVM一同结束工作。
`守护线程最典型的应用就是 GC (垃圾回收器)`

#### volatile关键字是否能保证线程安全？（）
答案：不能
解析：volatile关键字用在多线程同步中，可保证读取的可见性，JVM只是保证从主内存加载到线程工作内存的值是最新的读取值，而非cache中。但多个线程对
volatile的写操作，无法保证线程安全。例如假如线程1，线程2 在进行read,load 操作中，发现主内存中count的值都是5，那么都会加载这个最新的值，
在线程1堆count进行修改之后，会write到主内存中，主内存中的count变量就会变为6；线程2由于已经进行read,load操作，在进行运算之后，
也会更新主内存count的变量值为6；导致两个线程及时用volatile关键字修改之后，还是会存在并发的情况。

####下面关于java.lang.Exception类的说法正确的是（）
A 继承自Throwable      B Serialable      CD 不记得，反正不正确
答案：A
解析：Java异常的基类为java.lang.Throwable，java.lang.Error和java.lang.Exception继承 Throwable，RuntimeException和其它的
Exception等继承Exception，具体的RuntimeException继承RuntimeException。
#### 扩展：错误和异常的区别(Error vs Exception) 
1) java.lang.Error: Throwable的子类，用于标记严重错误。合理的应用程序不应该去try/catch这种错误。绝大多数的错误都是非正常的，就根本不该出现的。
java.lang.Exception: Throwable的子类，用于指示一种合理的程序想去catch的条件。即它仅仅是一种程序运行条件，而非严重错误，并且鼓励用户程序去catch它。
2)  Error和RuntimeException 及其子类都是未检查的异常（unchecked exceptions），而所有其他的Exception类都是检查了的异常（checked exceptions）.
checked exceptions: 通常是从一个可以恢复的程序中抛出来的，并且最好能够从这种异常中使用程序恢复。比如FileNotFoundException, 
ParseException等。检查了的异常发生在编译阶段，必须要使用try…catch（或者throws）否则编译不通过。

#### 描述forward 和redirect的区别
forward是服务器请求资源，服务器直接访问目标地址的URL，目标地址可以接收request 请求参数，然后把结果发给浏览器，浏览器根本不知道服务器发送的内容是从哪儿来的，所以它的地址栏中还是原来的地址。
redirect就是服务端根据逻辑,发送一个状态码,告诉浏览器重新去请求哪个地址，浏览器会重新进行请求，此时不能用request传值，浏览器的地址栏会变成新的地址

#### Static变量是什么含义
static是静态变量,就是变量值不随函数执行结束而消失，下次调用同一函数时，上次所赋予的值仍存在。

#### 垃圾回收器的基本原理是什么？垃圾回收器可以马上回收内存吗？有什么办法主动通知虚拟机进行垃圾回收
对于GC来说，当程序员创建对象时，GC就开始监控这个对象的地址、大小以及使用情况。通常，GC采用有向图的方式记录和管理堆(heap)中的所有对象。
通过这种方式确定哪些对象是`"可达的"`，哪些对象是"不可达的"。当GC确定一些对象为"不可达"时，GC就有责任回收这些内存空间。可以。
程序员可以手动执行`System.gc()，通知GC运行`，但是Java语言规范并不保证GC一定会执行。

#### Overload和Override的区别。Overloaded的方法是否可以改变返回值的类型?
方法的重写Overriding和重载Overloading是Java多态性的不同表现。重写Overriding是父类与子类之间多态性的一种表现，重载Overloading是
一个类中多态性的一种表现。如果在子类中定义某方法与其父类有相同的名称和参数，我们说该方法被重写 (Overriding)。子类的对象使用这个方法时，
将调用子类中的定义，对它而言，父类中的定义如同被“屏蔽”了。如果在一个类中定义了多个同名的方法，它们或有不同的参数个数或有不同的参数类型，
则称为方法的重载(Overloading)。Overloaded的方法是可以改变返回值的类型。



#### swtich是否能作用在byte上，是否能作用在long上，是否能作用在String上?
switch（expr1）中，expr1是一个整数表达式。因此传递给 switch 和 case 语句的参数应该是 int、 short、 char 或者 byte。
long,string 都不能作用于swtich。

#### short s1 = 1; s1 = s1 + 1;有什么错? short s1 = 1; s1 +=1;有什么错?
答：对于short s1=1;s1=s1+1来说，在s1+1运算时会自动提升表达式的类型为int，那么将int赋予给short类型的变量s1会出现类型转换错误。对于short s1=1;s1+=1来说，+=是java语言规定的运算符，Java编译器会对它进行特殊处理，因此可以正确编译。
char类型变量能不能储存一个中文的汉子，为什么？
答：char类型变量是用来储存Unicode编码的字符的，unicode字符集包含了汉字，所以char类型当然可以存储汉字的，还有一种特殊情况就是某个生僻字没有包含在unicode编码字符集中，那么就char类型就不能存储该生僻字。补充说明，Unicode编码占用两个字节，所以，char类型的变量也是占用两个字节。
#### Integer和int的区别
int是java的8种内置的原始数据类型。Java为每个原始类型都提供了一个封装类，Integer就是int的封装类。int变量的默认值为0，Integer变量的默认值为null，这一点说明Integer可以区分出未赋值和值为0的区别，比如说一名学生没来参加考试，另一名学生参加考试全答错了，那么第一名考生的成绩应该是null，第二名考生的成绩应该是0分。关于这一点Integer应用很大的。Integer类内提供了一些关于整数操作的一些方法，例如上文用到的表示整数的最大值和最小值。
#### switch语句能否作用在byte上，能否作用在long上，能否作用在string上？
答：byte的存储范围小于int，可以向int类型进行隐式转换，所以switch可以作用在byte上。long的存储范围大于int，不能向int进行隐式转换，只能强制转换，所以switch不可以作用在long上。string在1.7版本之前不可以，1.7版本之后switch就可以作用在string上了。
#### Math.round(11.5)等于多少？Math.round(-11.5)等于多少？
答：Math类中提供了三个与取整有关的方法：ceil、floor、round，这些方法的作用与它们的英文名称的含义相对应。例如，ceil的英文意义是天花板，该方法就表示向上取整，Math.ceil(11.3)的结果为12,Math.ceil(-11.3)的结果是-11；floor的英文意义是地板，该方法就表示向下取整，Math.ceil(11.6)的结果为11,Math.ceil(-11.6)的结果是-12；最难掌握的是round方法，它表示“四舍五入”，算法为Math.floor(x+0.5)，即将原来的数字加上0.5后再向下取整，所以，Math.round(11.5)的结果为12，Math.round(-11.5)的结果为-11。


#### Throwable、Error、Exception、RuntimeException 区别 联系
1.Throwable 类是 Java 语言中所有错误或异常的超类。它的两个子类是Error和Exception；
2.Error 是 Throwable 的子类，用于指示合理的应用程序不应该试图捕获的严重问题。大多数这样的错误都是异常条件。虽然 ThreadDeath 错误是一个“正规”的条件，但它也是 Error 的子类，因为大多数应用程序都不应该试图捕获它。在执行该方法期间，无需在其 throws 子句中声明可能抛出但是未能捕获的 Error 的任何子类，因为这些错误可能是再也不会发生的异常条件。
3.Exception 类及其子类是` Throwable 的一种形式，它指出了合理的应用程序想要捕获的条件。`
4.RuntimeException 是那些可能在` Java 虚拟机正常运行期间抛出的异常的超类`。可能在执行方法期间抛出但未被捕获的RuntimeException 的任何子类都无需在 throws 子句中进行声明。它是Exception的子类。


#### Linux笔试题10．什么是符号链接(软连接)，什么是硬链接？符号链接与硬链接的区别是什么？
参考答案：
链接分硬链接和符号链接。
符号链接可以建立对于文件和目录的链接。符号链接可以`跨文件系统`，即可以跨磁盘分区。符号链接的`文件类型位是 l` ，链接文件具有新的i节点。
`硬链接不可以跨文件系统`。它只能建立对文件的链接，硬链接的`文件类型位是 － `，且`硬链接文件的i节点同被链接文件的i节点相同`。

#### Linux笔试题3. 简述DNS进行域名解析的过程。
参考答案：
首先，客户端发出DNS请求翻译IP地址或主机名。DNS服务器在收到客户机的请求后：
（1）检查DNS服务器的`缓存`，若查到请求的地址或名字，即向客户机发出应答信息；
（2）若没有查到，则在`数据库中`查找，若查到请求的地址或名字，即向客户机发出应答信息；
（3）若没有查到，则将请求发给`根域DNS服务器`，并依序从根域查找`顶级域`，由顶级查找`二级域`，二级域查找`三级`，直至找到要解析的地址或名字，即向客户机所在网络的DNS服务器发出应答信息，DNS服务器收到应答后现在缓存中存储，然后，将解析结果发给客户机。
（4）若没有找到，则返回错误信息。

#### Linux笔试题6．什么是静态路由，其特点是什么？什么是动态路由，其特点是什么？
参考答案：
静态路由是由`系统管理员设计与构建的路由表规定的路由`。适用于`网关数量有限`的场合，且网络拓朴结构不经常变化的网络。其`缺点是不能动态地
适用网络状况的变化`，当网络状况变化后必须由网络管理员修改路由表。
动态路由是由`路由选择协议而动态构建的`，路由协议之间通过交换各自所拥有的路由信息`实时更新路由表的内容`。动态路由可以自动学习网络的拓朴
结构，并更新路由表。其缺点是`路由广播更新信息将占据大量的网络带宽`。


####  String类型传递是值传递，char[]类型传递是引用传递


### 前台后台都做吗?
碰到过一个聪明人,他是这么回答的: 前台js写的比较熟练,html的框架模板也能搭建的非常整齐美观,只是特效能力比较差

### 事务,什么是事务,为何用事务
保证数据的一致性和完整性




### 网易笔试
#### mysql中查看SQL模式的命令是（）
MySQL数据库中，变量分为 系统变量（以"@@"开头）和用户自定义变量。系统变量分为全局系统变量(global)和会话系统变量(session)。
@@global     仅用于访问全局系统变量的值；
@@session  仅用于访问会话系统变量的值；
@@              先访问会话系统变量的值，若不存在则去访问全局系统变量的值；
sql_mode 为系统变量，既是全局系统变量，又是会话系统变量。


总时差就是拖拖拖，可以拖多少天才做工作M，自由时差就是早早完成，然后在下次工作委派之前可以休息多少天。

磁盘阵列的配置文件为/etc/raidtab 逻辑设备为 /dev/md0磁盘阵列

### Java中的Object类是所有类的父类，它提供了以下11个方法：

public final native Class<?> getClass()
public native int hashCode()
public boolean equals(Object obj)
protected native Object clone() throws CloneNotSupportedException
public String toString()
public final native void notify()
public final native void notifyAll()
public final native void wait(long timeout) throws InterruptedException
public final void wait(long timeout, int nanos) throws InterruptedException
public final void wait() throws InterruptedException
protected void finalize() throws Throwable { }


### @Autowired和@Resource的区别是什么？
1、@Autowired与@Resource都可以用来装配bean. 都可以写在字段上,或写在setter方法上。
2、@Autowired默认`按类型装配（这个注解是属业spring的）`，默认情况下必须要求依赖对象必须存在，如果要允许null值，可以设置它的required属性为false，如：
@Autowired(required=false) 
3、@Resource 是`JDK1.6支持的注解`，默认`按照名称进行装配`，名称可以通过name属性进行指定，如果没有指定name属性，当注解写在字段上时，
默认取字段名，按照名称查找，如果注解写在setter方法上默认取属性名进行装配。



### 静态方法可以被重写，没有意义的重写。静态方法不会被初始化
JAVA静态方法形式上可以重写，但从本质上来说不是JAVA的重写。因为静态方法只与类相关，不与具体实现相关，声明的是什么类，则引用相应类的静态方法(本来静态无需声明，可以直接引用)，看下例子：
Java代码  
```java
class Base{  
        static void a( ){System.out.println("A");  }  
                 void b( ){System.out.println("B"); }  
}  
public class  Inherit extends Base{  
          static void a( ){System.out.println("C");  }  
                  void b( ){System.out.println("D"); }  
           public static void main(String args[]){  
                    Base b=new Base();  
                    Base  c=new Inherit();  
                    b.a();  
                    b.b();  
                    c.a();  
                    c.b();  
         }  
}  
```
 
以上输出的结果是：A
                                 B
                                 A
                                 D
非静态方法 按重写规则调用相应的类实现方法，而静态方法只与类相关。
`所谓静态，就是在运行时，虚拟机已经认定此方法属于哪个类。`



#### 具有12个关键字的有序表，折半查找的平均查找长度（）
将12个数画成完全二叉树，第一层有1个、第二次2个、第三层4个，第四层只有5个。
二分查找时：
第一层需要比较1次
第二两个数，每个比较2次
第三层四个数，每个比较3次
第四层五个数，每个比较4次
则平均查找长度即为：（1+2*2+3*4+4*5）/12 = 37/12 = 3.0833 即为 A、3.1

#### 设查找表中有 100 个元素，如果用二分法查找方法查找数据元素 X ，则最多需要比较（      ）次就可以断定数据元素 X 是否在查找表中。
   n
log  + 1                   6+1 = 7
   2

#### 14、一个长度为32的有序表，若采用二分查找一个不存在的元素，则比较次数最多是（  ）
A、4
B、5
C、6
D、7
6
log2n   +1    （log2n 向下取整）

2^n - 1 = 32


#### 单链表判断环
判断一个链表是否有环（我回答快慢指针，因此引出下一个问题）
假设一个节点为100的环形单链表，你这方法要走多少步判断出有环，99个节点呢？
环大小的整倍，取决于环外长度是环长度的倍数，如果环外长度是环长度的1倍多，那么就是要2倍环长度的，如果环外长度小于环长度，
那么就是1倍环大小，依次类推
 

#### 5、字符串通常采用的两种存储方式是（   ）？
顺序存储和链式存储时两种最基本的存储结构，字符串通常采用顺序存储，但是字符串较长而没有那么大的连续空间时，可以把一个字符串分成多个小串，串与串之间采用链式存储


#### 线性表如果要频繁的执行插入和删除操作，该线性表采取的存储结构应该是（）
A，散列更适合查找，不适合频繁更新
B，顺序存储插入和删除都需要移动大量元素
C，链式存储适合插入和删除操作
D，索引结构，每次插入和删除都需要更新索引，费时

18、下列关于AOE网的叙述中,不正确的是(  ) 
A、关键活动不按期完成就会影响整个工程的完成时间
B、任何一个关键活动提前完成,那么整个工程将会提前完成
C、所有的关键活动提前完成,那么整个工程将会提前完成
D、某些关键活动若提前完成,那么整个工程将会提前完成

关键活动组成了关键路径，`关键路径是图中的最长路径，关键路径长度代表整个工期的最短完成时间`，关键活动延期完成，必将导致关键路径长度增加，
即整个工期的最短完成时间增加，因此A正确。关键路径并不唯一，当有多条关键路径存在时，其中一条关键路径上的关键活动时间缩短，只能导致本条
关键路径变成非关键路径，而无法缩短整个工期，因为其他关键路径没有变化，因此B项不正确。对于A，B两项要搞懂的是，任何一条关键路径上的关键
活动变长了，都会使这条关键路径变成更长的关键路径，并且导致其他关键路径变成非关键路径（如果关键路径不唯一），因此整个工期延长。而某些
关键活动缩短则不一定缩短整个工期。理解了A,B两项，C,D就很容易理解了


#### 设有字母序列{Q,D,F,X,A,P,N,B,Y,M,C,W}，请写出按二路归并方法对该序列进行一趟扫描后的结果为 
DQFXAPBNMYCW，解析：二路归并：如果序列中有n 个记录，可以先把它看成n个子序列，每个子序列中只包含一个记录，因而都是排好序的。二路归并
排序先将每相邻的两个子序列合并，得到n/2(向上取整)个较大的有序子序列，每个子序列包含2个记录。再将这些子序列两两合并。如此反复，直到
最后合并成一个有序序列，排序即告完成。


#### 设顺序线性表的长度为30，分成5块，每块6个元素，如果采用分块查找并且索引表和块内均采用顺序查找，则其平均查找长度为(     )。
A、6
B、11
C、5
D、6.5
分块查找会分两部分进行,第一步先进行索引表查找判断其在那个字表中,第二步然后进行在字表中的查找
索引表有5个元素 所以平均查找长度为:(1+5)/2=3    (1+2+3+4+5)/5
字表中有6个元素,所以平均查找长度为:(1+6)/2=3.5
所以总的平均查找长度为3+3.5=6.5

#### 13、下列关于线性表，二叉平衡树，哈希表存储数据的优劣描述错误的是（ ）
A、哈希表是一个在时间和空间上做出权衡的经典例子。如果没有内存限制，那么可以直接将键作为数组的索引。那么所有的查找时间复杂度为O(1)；
B、线性表实现相对比较简单
C、平衡二叉树的各项操作的时间复杂度为O（logn）
D、平衡二叉树的插入节点比较快
平衡二叉查找树 
(1) 查找代价：查找效率最好，最坏情况都是O(logN)数量级的。
(2) 插入代价：总体上插入操作的代价仍然在O(logN)级别上(插入结点需要首先查找插入的位置)。
(3) 删除代价：每一次删除操作最多需要O(logN)次旋转。因此，删除操作的时间复杂度为O(logN)+O(logN)=O(2logN)
AVL 效率总结 :
查找的时间复杂度维持在O(logN)，不会出现最差情况。
 AVL树在执行每个插入操作时最多需要1次旋转，其时间复杂度在O(logN)左右。 
AVL树在执行删除时代价稍大，执行每个删除操作的时间复杂度需要O(2logN)。


#### 2、在为传统面向对象语言的程序做单元测试的时候,经常用到mock对象。Mock对象通过反射数。请问反射最大程度破坏了面向对象的以下哪个特性？
A、封装
B、多态
C、继承
D、抽象
java的封装性：指的是将对象的状态信息隐藏在对象内部，不允许外部程序直接访问对象内部信息，通过该类提供的方法实现对内部信息的操作访问。
反射机制：在运行状态中，对于任意一个类，都能够知道这个类的所有属性和方法;对于任意一个对象，都能够调用它的任意一个方法和属性

#### 3、经过强制类型转换以后，变量a, b的值分别为（ ）short a = 128; byte b = (byte) a;
A、128，127
B、128，-128
C、128,128
D、编译错误
a=0000000010000000，当进行强制转换时，byte是八位的，截取a的后八位，b=10000000；最高位是符号位，说明b是负数，在计算机中以补码表示，
求其源码，先减1得到反码01111111，取反得到源码10000000，也就是-128.

#### 7、Java 中的集合类包括 ArrayList 、 LinkedList 、 HashMap 等，下列关于集合类描述错误的是（  ）（点击查看答案>>>>>>>>）
A、ArrayList和LinkedList均实现了List接口
B、ArrayList的访问速度比LinkedList快
C、随机添加和删除元素时，ArrayList的表现更佳
D、HashMap实现Map接口，它允许任何类型的键和值对象
ArrayList是基于数组实现的，所以查询快，增删慢；LinkedList是基于链表实现的，所以查找慢，增删快。

#### 11、关于抽象类的叙述正确的是？(  ) 
A、抽象类不可以实例化，或者说抽象类不能声明为对象
B、抽象类就是一种特殊的接口
C、抽象类的方法都是抽象方法
D、抽象类的导出类一定不是抽象类

选项中，抽象类不能实例化，这句是正确的。后面，抽象类不能申明为对象，是错误的。java中，接口和抽象类，都可以声明为对象，
只需要在实例化的时候，用一个该接口/抽象类的实现类实例化就行了。即：
interface / abstract class obj = new class();其中，new后面的class是前面接口/抽象类的实现类。
B选项中，接口是一种特殊的抽象类。其中，成员变量都是默认public static final修饰的，方法都是public abstract修饰的，并且除了default和static的以外，只有声明，没有方法体。
C选项中，抽象类的方法，可以是抽象abstract的，也可以是普通的。就算全部是普通方法，也可以用abstract修饰。
D选项中，导出类，及子类，抽象类的子类可以是抽象的，也可以是普通的。


#### 有如下4条语句：()
Integer i01=59;
int i02=59;
Integer i03=Integer.valueOf(59);
Integer i04=new Integer(59);
以下输出结果为false的是:
System.out.println(i01==i02);
System.out.println(i01==i03);
System.out.println(i03==i04);
System.out.println(i02==i04);


通过看源码能够知道，整数类型在-128～127之间时，会使用缓存，造成的效果就是，如果已经创建了一个相同的整数，使用valueOf创建第二次时，
不会使用new关键字，而用已经缓存的对象。所以使用valueOf方法创建两次对象，若对应的数值相同，且数值在-128～127之间时，两个对象都指向同一个地址。
最后，使用Integer i = 400这样的方式来创建Integer对象，与valueOf方法的效果是一样的，不再赘述。
总之，包装类对象不可使用“==”符做比较运算，如果要进行比较运算时，最好使用java类库中的compareTo方法


#### 16、在类Tester中定义方法如下，
public double max(int x, int y) { // 省略 }
则在该类中定义如下哪个方法头是对上述方法的重载(Overload)? （   ）
A、public int max(int a, int b) {}
B、public int max(double a, double b) {}
C、public double max(int x, int y) {}
D、private double max(int a, int b) {}



#### 执行以下程序后的输出结果是（）
```java
public class Test {
   public static void main(String[] args) {
       StringBuffer a = new StringBuffer("A");
       StringBuffer b = new StringBuffer("B");
       operator(a, b);
       System.out.println(a + "," + b);
   }

   public static void operator(StringBuffer x, StringBuffer y) {
       x.append(y);
       y = x;
   }
}
```
a AB
b B
x AB
y AB


#### 20、JAVA语言的下面几种数组复制方法中，哪个效率最高？（  ）
A、for循环逐一复制
B、System.arraycopy
C、Arrays.copyof
D、使用clone方法

结论：
效率：System.arraycopy > clone > Arrays.copyOf > for循环
理由：
A：for循环，效率最低，随便写个程序验证一下，效率慢的不是一点.....我测试的时候比clone和System.arraycopy差了100多倍
B：System.arraycopy：原型是
public static native void arraycopy(Object src,  int  srcPos , Object dest, int destPos, int length);
C：Arrays.copyOf底层调用了上面的System.copyOf效率比上面两个低。
D：clone()的完整定义：protected native Object clone() throws CloneNotSupportedException;
高票答案说的clone()返回的是Object类型，其实是错误的，只有Object[]数组的clone()方法才返回Object类型，子类重写了父类的方法。
其实，一般情况下，前三个的效率差别几乎忽略不计，但是从Arrays.copyOf底层调用的是System.arraycopy，自然System.arrayCopy效率自然要低一些。
而clone()和System.arraycopy只是从实验的结果来看是System.arraycopy的效率高。

#### 13、下列对接口的说法，正确的是( B  ) 
A、接口与抽象类是相同的概念
B、若要实现一个接口为普通类则必须实现接口的所有方法
C、接口之间不能有继承关系
D、一个类只能实现一个接口
实现接口必须实现接口所有方法，没毛病~
继承抽象类不需要实现所有方法，只需要实现所有抽象方法~

#### 11、下列关于修饰符混用的说法，错误的是(  D ) 
A、abstract不能与final并列修饰同一个类
B、abstract类中不应该有private的成员
C、abstract方法必须在abstract类或接口中
D、staic方法中能处理非static的属性
A选项毋庸置疑是对的，一个类要么能继承要么不能继承，只能选一个。
B选项我做的时候没看清，还以为写的是不能有，确实不应该有，不管是成员变量，还是非抽象方法都不建议用private修饰，抽象方法是禁止使用
private修饰。原因就是我们创建抽象类的目的就是要实现代码复用，方便子类继承，private修饰的是不能继承的，同时抽象类不能实例化对象，
所以用private修饰的成员毫无用处。
抽象类的中抽象方法只允许用public和默认修饰（JDK1.8之前默认是用protected修饰，但在JDK1.8之后则是默认为default修饰）
C选项，一个类若有抽象方法，其本身也必须声明为抽象类；接口中的方法默认就是public abstract；综上所诉：abstract方法必须在abstract类或接口中。
D选项，说的不严谨啊，staic方法中能处理非static的属性，当然我可以在static方法里创建对象，然后通过方法调用我的非static成员变量啊。



