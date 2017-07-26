[学习网站]
## Map 的实现原理
### HashMap 的实现原理
HashMap 是基于哈希表的 Map 接口的非同步实现。此实现提供所有可选的映射操作，并允许使用 null 值和 null 键。
此类不保证映射的顺序，特别是它不保证该顺序恒久不变。
首先HashMap里面实现一个静态内部类Entry，其重要的属性有 key , value, next，从属性key,value我们就能很明显的看出来Entry就是
HashMap键值对实现的一个基础bean，我们上面说到HashMap的基础就是一个线性数组，这个数组就是Entry[]，Map里面的内容都保存在Entry[]里面。
HashMap 底层就是一个数组结构，数组中的每一项又是一个链表。当新建一个 HashMap 的时候，就会初始化一个数组。
当系统决定存储 HashMap 中的 key-value 对时，完全没有考虑 Entry 中的 value，`仅仅只是根据 key` 来计算并决定每个 Entry 的存储位置。
我们完全可以把 Map 集合中的 value 当成 key 的附属，当系统决定了 key 的存储位置之后，value 随之保存在那里即可。
HashMap 的容量总是 2 的 n 次方，即底层数组的长度总是为` 2 的 n 次方(容量左移实现)`。
所以说，当数组长度为 2 的 n 次幂的时候，不同的 key 算得得 index 相同的几率较小，那么数据在数组上分布就比较均匀，
也就是说碰撞的几率小，相对的，查询的时候就不用遍历某个位置上的链表，这样查询效率也就较高了。
#### 归纳
简单地说，HashMap 在底层将 key-value 当成一个整体进行处理，这个整体就是一个 Entry 对象。HashMap 底层采用一个 Entry[] 数组来保存
所有的 key-value 对，当需要存储一个 Entry 对象时，会根据 hash 算法来决定其在数组中的存储位置，在根据 equals 方法决定其在该数组位
置上的链表中的存储位置；当需要取出一个Entry 时，也会根据 hash 算法找到其在数组中的存储位置，再根据 equals 方法从该位置上的链表中
取出该Entry。
#### 扩容
默认的的负载因子 0.75 是对空间和时间效率的一个平衡选择。
默认情况下，数组大小为 16，那么当 HashMap 中元素个数超过 16*0.75=12 的时候，就把数组的大小扩展为 2*16=32，即扩大一倍，然后重新计
算每个元素在数组中的位置，而这是一个非常消耗性能的操作，所以如果我们已经预知 HashMap 中元素的个数，那么预设元素的个数能够有效的提高
HashMap 的性能。
#### 疑问：如果两个key通过hash%Entry[].length得到的index相同，会不会有覆盖的危险？
　　这里HashMap里面用到链式数据结构的一个概念。上面我们提到过Entry类里面有一个next属性，作用是指向下一个Entry。打个比方， 
第一个键值对A进来，通过计算其key的hash得到的index=0，记做:Entry[0] = A。一会后又进来一个键值对B，通过计算其index也等于0，
现在怎么办？HashMap会这样做:B.next = A,Entry[0] = B,如果又进来C,index也等于0,那么C.next = B,Entry[0] = C；这样我们发现index=0
的地方其实存取了A,B,C三个键值对,他们通过next这个属性链接在一起。所以疑问不用担心。也就是说数组中存储的是最后插入的元素。到这里为止，
####  解决hash冲突的办法
1.开放定址法（线性探测再散列，二次探测再散列，伪随机探测再散列）
2.再哈希法
3.链地址法
4.建立一个公共溢出区
Java中hashmap的解决办法就是采用的链地址法。
#### 2种典型遍历
```java
public class HahsMapTest {

    public static void main(String[] args) {

        HashMap<Integer, String> hashMap = new HashMap();
        hashMap.put(2, "23");
        hashMap.put(22, "232");
        hashMap.put(21, "er3");

        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }

        for (Integer key : hashMap.keySet()) {
            System.out.println("Key = " + key);
        }
        for (String value : hashMap.values()) {
            System.out.println("Value = " + value);
        }
    }

}
```
### 常用的哈希函数
参考[哈希表博客]
#### 1.直接寻址法
取关键字或者关键字的某个线性函数值作为哈希地址,即H(Key)=Key或者H(Key)=a*Key+b(a,b为整数),这种散列函数也叫做自身函数.如果H(Key)的
哈希地址上已经有值了,那么就往下一个位置找,知道找到H(Key)的位置没有值了就把元素放进去.

#### 2.数字分析法
分析一组数据,比如一组员工的出生年月,这时我们发现出生年月的前几位数字一般都相同,因此,出现冲突的概率就会很大,但是我们发现年月日的后几位
表示月份和具体日期的数字差别很大,如果利用后面的几位数字来构造散列地址,则冲突的几率则会明显降低.因此数字分析法就是找出数字的规律,
尽可能利用这些数据来构造冲突几率较低的散列地址.

#### 3.平方取中法
取关键字平方后的中间几位作为散列地址.一个数的平方值的中间几位和数的每一位都有关。因此，有平方取中法得到的哈希地址同关键字的每一位都
有关，是的哈希地址具有较好的分散性。该方法适用于关键字中的每一位取值都不够分散或者较分散的位数小于哈希地址所需要的位数的情况。

#### 4.折叠法
折叠法即将关键字分割成位数相同的几部分,最后一部分位数可以不同,然后取这几部分的叠加和(注意:叠加和时去除进位)作为散列地址.数位叠加可以
有移位叠加和间界叠加两种方法.移位叠加是将分割后的每一部分的最低位对齐,然后相加;间界叠加是从一端向另一端沿分割界来回折叠,然后对齐相加.

#### 5.随机数法
选择一个随机数,去关键字的随机值作为散列地址,通常用于关键字长度不同的场合.

#### 6.除留余数法
取关键字被某个不大于散列表表长m的数p除后所得的余数为散列地址.即H(Key)=Key MOD p,p<=m.不仅可以对关键字直接取模,也可在折叠、平方取
中等运算之后取模。对p的选择很重要，一般取素数或m，若p选得不好，则很容易产生冲突。一般p取值为表的长度tableSize。

### 哈希冲突的处理方法
####1、开放定址法——线性探测
线性探测法的地址增量di = 1, 2, ... , m-1，其中，i为探测次数。该方法一次探测下一个地址，知道有空的地址后插入，若整个空间都找不到
空余的地址，则产生溢出。
线性探测容易产生“聚集”现象。当表中的第i、i+1、i+2的位置上已经存储某些关键字，则下一次哈希地址为i、i+1、i+2、i+3的关键字都将企图填入
到i+3的位置上，这种多个哈希地址不同的关键字争夺同一个后继哈希地址的现象称为“聚集”。聚集对查找效率有很大影响。

####2、开放地址法——二次探测
二次探测法的地址增量序列为 di = 12， -12， 22， -22，… ， q2, -q2 (q <= m/2)。二次探测能有效避免“聚集”现象，但是不能够探测到
哈希表上所有的存储单元，但是至少能够探测到一半。

####3、链地址法
链地址法也成为拉链法。其基本思路是：将所有具有相同哈希地址的而不同关键字的数据元素连接到同一个单链表中。如果选定的哈希表长度为m，
则可将哈希表定义为一个有m个头指针组成的指针数组T[0..m-1]，凡是哈希地址为i的数据元素，均以节点的形式插入到T[i]为头指针的单链表中。
并且新的元素插入到链表的前端，这不仅因为方便，还因为经常发生这样的事实：新近插入的元素最优可能不久又被访问。



### ConcurrentHashMap 的实现原理
ConcurrentHashMap 的结构是比较复杂的，都深究去本质，其实也就是数组和链表而已。 `锁分段技术`
ConcurrentHashMap 数据结构为一个 Segment 数组，Segment 的数据结构为 HashEntry 的数组，而 `HashEntry 存的是我们的键值对`，可以构成链表。
ConcurrentHashMap 的成员变量中，包含了一个 Segment 的数组（final Segment<K,V>[] segments;），而 Segment 是 ConcurrentHashMap 
的内部类，然后在 Segment 这个类中，包含了一个 HashEntry 的数组（`transient volatile HashEntry<K,V>[] table;`）。而 HashEntry 
也是 ConcurrentHashMap 的`内部类`。HashEntry 中，包含了 key 和 value 以及 next 指针（类似于 HashMap 中 Entry），所以 HashEntry 
可以构成一个链表。

ConcurrentHashMap 的结构中包含的 Segment 的数组，在默认的并发级别会创建包含 16 个 Segment 对象的数组。通过我们上面的知识，我们
知道每个 Segment 又包含若干个散列表的桶，每个桶是由 HashEntry 链接起来的一个链表。如果 key 能够均匀散列，每个 Segment 大约守护
整个散列表桶总数的 1/16。

`加锁操作是针对的 hash 值对应的某个 Segment`，而不是整个 ConcurrentHashMap。因为 put 操作只是在这个 Segment 中完成，所以并不需要对
整个 ConcurrentHashMap 加锁。所以，此时，`其他的线程也可以对另外的 Segment 进行 put 操作`，因为虽然该 Segment 被锁住了，但其他的
 Segment 并没有加锁。同时，`读线程并不会因为本线程的加锁而阻塞。`

#### 总结
散列表一般的应用场景是：除了少数插入操作和删除操作外，绝大多数都是读取操作，而且读操作在大多数时候都是成功的。正是基于这个前提，
ConcurrentHashMap 针对读操作做了大量的优化。通过`HashEntry 对象的不变性`和用 `volatile 型变量协调线程间的内存可见性`，使得大多数时候，
读操作不需要加锁就可以正确获得值。这个特性使得 ConcurrentHashMap 的并发性能在分离锁的基础上又有了近一步的提高。

ConcurrentHashMap 是一个并发散列映射表的实现，它允许完全并发的读取，并且支持给定数量的并发更新。相比于 HashTable 和用同步包装器
包装的 HashMap（Collections.synchronizedMap(new HashMap())），ConcurrentHashMap 拥有更高的并发性。在 HashTable 和由
同步包装器包装的 HashMap 中，使用一个全局的锁来同步不同线程间的并发访问。同一时间点，只能有一个线程持有锁，也就是说在同一时间点，
只能有一个线程能访问容器。这虽然保证多线程间的安全并发访问，但同时也导致对容器的访问变成串行化的了。

ConcurrentHashMap 的高并发性主要来自于三个方面：
1. 用`分离锁`实现多个线程间的更深层次的共享访问。
2. 用 HashEntery 对象的不变性来降低执行读操作的线程在遍历链表期间对加锁的需求。
3. 通过对同一个 Volatile 变量的写 / 读访问，协调不同线程间读 / 写操作的内存可见性。
使用分离锁，减小了请求 同一个锁的频率。


### LinkedHashMap 的实现原理
LinkedHashMap 可以选择按照访问顺序进行排序。
LinkedHashMap 实现与 HashMap 的不同之处在于，LinkedHashMap 维护着一个运行于所有条目的`双重链接列表`。此链接列表定义了迭代顺序，
该`迭代顺序可以是插入顺序或者是访问顺序`。
注意，此实现不是同步的。如果多个线程同时访问链接的哈希映射，而其中至少一个线程从结构上修改了该映射，则它必须保持外部同步。
根据链表中元素的顺序可以分为：按插入顺序的链表，和按访问顺序(调用 get 方法)的链表。默认是按插入顺序排序，如果指定按访问顺序排序，
那么调用get方法后，会将这次访问的元素移至链表尾部，不断访问可以形成按访问顺序排序的链表。
其实 LinkedHashMap 几乎和 HashMap 一样：从技术上来说，不同的是它定义了一个 Entry<K,V> header，这个 header 不是放在 Table 里，
它是额外独立出来的。LinkedHashMap 通过继承 hashMap 中的 Entry<K,V>,并`添加两个属性 Entry<K,V> before,after`,和 header 结合起来
组成一个双向链表，来实现按插入顺序或访问顺序排序。


### LRU 缓存介绍
LRU 缓存利用了这样的一种思想。LRU 是` Least Recently Used `的缩写，翻译过来就是“最近最少使用”，也就是说，`LRU 缓存把最近最少使用的
数据移除，让给最新读取的数据`。而往往最常读取的，也是读取次数最多的，所以，利用 LRU 缓存，我们能够提高系统的 performance。
是一种页面置换算法，最近最少使用的被替换掉。LRU算法是以时间轴为依据进行替换，而不是使用频率为依据替换。
 
#### 实现
要实现 LRU 缓存，我们首先要用到一个类 `LinkedHashMap`。
用这个类有两大好处：一是它本身已经实现了按照访问顺序的存储，也就是说，最近读取的会放在最前面，最最不常读取的会放在最后（当然，它也
可以实现按照插入顺序存储）。第二，LinkedHashMap 本身有一个方法用于判断是否需要移除最不常读取的数，但是，原始方法默认不需要移除
（这是，LinkedHashMap 相当于一个linkedlist），所以，我们需要 override 这样一个方法，使得当缓存里存放的数据个数超过规定个数后，
就把最不常用的移除掉。关于 LinkedHashMap 中已经有详细的介绍。


### Hashtable
#### Hashtable 与 HashMap 的简单比较
HashTable 基于 Dictionary 类，而 HashMap 是基于 AbstractMap。Dictionary 是任何可将键映射到相应值的类的抽象父类，而 AbstractMap 
是基于 Map 接口的实现，它以最大限度地减少实现此接口所需的工作。
`HashMap 的 key 和 value 都允许为 null`，而 Hashtable 的 key 和 value 都不允许为 null。HashMap 遇到 key 为 null 的时候，
调用` putForNullKey `方法进行处理，而对 value 没有处理；`Hashtable遇到 null，直接返回 NullPointerException`。
Hashtable 方法是同步，而HashMap则不是。我们可以看一下源码，Hashtable 中的几乎所有的 public 的方法都是 `synchronized `的，
而有些方法也是在内部通过 synchronized 代码块来实现。所以有人一般都建议如果是涉及到多线程同步时采用 HashTable，没有涉及就采用 HashMap，
但是在 Collections 类中存在一个静态方法：synchronizedMap()，该方法创建了一个线程安全的 Map 对象，并把它作为一个封装的对象来返回。


### ArrayList
```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
}
```
ArrayList 继承了 AbstractList，实现了 List。它是一个数组队列，提供了相关的添加、删除、修改、遍历等功能。
ArrayList 实现了 RandmoAccess 接口，即提供了随机访问功能。RandmoAccess 是 java 中用来被 List 实现，为 List 提供快速访问功能的。
在 ArrayList 中，我们即可以通过元素的序号快速获取元素对象；这就是快速随机访问。
ArrayList 实现了 Cloneable 接口，即覆盖了函数 clone()，能被克隆。 ArrayList 实现 java.io.Serializable 接口，这意味着 ArrayList
 `支持序列化`，能通过序列化去传输。
 
#### 扩容
数组进行扩容时，会将老数组中的元素重新拷贝一份到新的数组中，每次数组容量的增长大约是其原容量的` 1.5 `倍
（从int newCapacity = oldCapacity + (oldCapacity >> 1)这行代码得出）。这种操作的代价是很高的，因此在实际使用时，
我们应该尽量避免数组容量的扩张。


### LinkedList
LinkedList 和 ArrayList 一样，都实现了 List 接口，但其内部的数据结构有本质的不同。LinkedList 是基于链表实现的
（通过名字也能区分开来），所以它的插入和删除操作比 ArrayList 更加高效。但也是由于其为基于链表的，所以随机访问的效率要比 ArrayList 差。

#### 数据结构
LinkedList 是基于链表结构实现，所以在类中包含了 first 和 last 两个指针(Node)。Node 中包含了上一个节点和下一个节点的引用，这样就
构成了双向的链表。每个 Node 只能知道自己的前一个节点和后一个节点，但对于链表来说，这已经足够了。



### Fail-Fast 机制
ArrayList HashMap ...
#### 原理
我们知道 java.util.HashMap 不是线程安全的，因此如果在使用迭代器的过程中有其他线程修改了 map，那么将抛出 
ConcurrentModificationException，这就是所谓 fail-fast 策略。
fail-fast 机制是 java 集合(Collection)中的一种错误机制。 当`多个线程对同一个集合的内容进行操作`时，就可能会产生 fail-fast 事件。
例如：当某一个线程 A 通过 iterator去遍历某集合的过程中，若该集合的内容被其他线程所改变了；那么线程 A 访问集合时，就会抛出
 ConcurrentModificationException 异常，产生 fail-fast 事件。
这一策略在源码中的实现是通过 modCount 域，`modCount 顾名思义就是修改次数`，对 HashMap 内容（当然不仅仅是 HashMap 才会有，其他例如
 ArrayList 也会）的修改都将增加这个值（大家可以再回头看一下其源码，在很多操作中都有 modCount++ 这句），那么在迭代器初始化过程中会
 将这个值赋给迭代器的 expectedModCount。









[哈希表博客]:http://blog.csdn.net/u011080472/article/details/51177412
[学习网站]:http://wiki.jikexueyuan.com/project/java-collection/hashmap.html