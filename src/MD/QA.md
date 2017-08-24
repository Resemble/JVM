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