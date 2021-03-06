[学习网站]
## Map 的实现原理
参考[美团点评技术团队]
### HashMap 的实现原理
HashMap 是`基于哈希表的 Map 接口的非同步实现`。此实现提供所有可选的映射操作，并允许使用 null 值和 null 键。
此类不保证映射的顺序，特别是它不保证该顺序恒久不变。
首先HashMap里面实现一个`静态内部类Entry`，其重要的属性有 key , value, next，从属性key,value我们就能很明显的看出来Entry就是
HashMap键值对实现的一个基础bean，我们上面说到HashMap的基础就是一个线性数组，这个数组就是Entry[]，Map里面的内容都保存在Entry[]里面。
HashMap 底层就是一个数组结构，`数组中的每一项又是一个链表`。当新建一个 HashMap 的时候，就会初始化一个数组。
当系统决定存储 HashMap 中的 key-value 对时，完全没有考虑 Entry 中的 value，`仅仅只是根据 key` 来计算并决定每个 Entry 的存储位置。
我们完全可以把 Map 集合中的 value 当成 key 的附属，当系统决定了 key 的存储位置之后，value 随之保存在那里即可。
HashMap 的容量总是 2 的 n 次方，即底层数组的长度总是为` 2 的 n 次方(容量左移实现)`。
所以说，当数组长度为 2 的 n 次幂的时候，不同的 key 算得得 index 相同的几率较小，那么数据在数组上分布就比较均匀，
也就是说碰撞的几率小，相对的，查询的时候就不用遍历某个位置上的链表，这样查询效率也就较高了。
这里的`Hash算法`本质上就是三步：`取key的hashCode值`、`高位运算`、`取模运算`。

#### put函数的实现
put函数大致的思路为：
- 对key的hashCode()做hash，然后再计算index;
- 如果没碰撞直接放到bucket里；
- 如果碰撞了，以链表的形式存在buckets后；jdk 1.8 是冲突遍历时放入的，放在链表最后。jdk 7 是冲突遍历完放入的，放在链表第一个位置，因为之前没有保存最后一个位置，也省去了再次遍历。
- 如果碰撞导致链表过长(大于等于TREEIFY_THRESHOLD)，就把链表转换成红黑树；
- 如果节点已经存在就替换old value(保证key的唯一性)
- 如果bucket满了(超过load factor*current capacity)，就要resize。

#### get函数的实现
在理解了put之后，get就很简单了。大致思路如下：
- bucket里的第一个节点，直接命中；
- 如果有冲突，则通过key.equals(k)去查找对应的entry
- 若为树，则在树中通过key.equals(k)查找，O(logn)；
- 若为链表，则在链表中通过key.equals(k)查找，O(n)。

#### HashMap并发出现什么情况，为什么。并发导致死循环，原因没答上来。
HashMap在并发环境下多线程put后可能导致get死循环，具体表现为CPU使用率100%
假设这里有两个线程同时执行了put()操作，并进入了transfer()环节
```java
class Test {
    void test() {
        while(null != e) {
            Entry<K,V> next = e.next; //线程1执行到这里被调度挂起了
            e.next = newTable[i];
            newTable[i] = e;
            e = next;
        }
    }
}
```
假设hash算法就是最简单的 key mod table.length（也就是数组的长度）。
最上面的是old hash 表，其中的Hash表的 size = 2, 所以 key = 3, 7, 5，在 mod 2以后碰撞发生在 table[1]
接下来的三个步骤是 Hash表 resize 到4，并将所有的 <key,value> 重新rehash到新 Hash 表的过程
线程1 put 3 被挂起，线程2 put 7 然后产生死循环。

#### HashCode 生成
　String类的value是char[]，char可以转换成UTF-8编码。譬如，’a’、’b’、’c’的UTF-8编码分别是97,98,99；“abc”根据上面的代码转换成公式就是：
　　h = 31 * 0 + 97 = 97；
　　h = 31 * 97 + 98 = 3105；
　　h = 31 * 3105 + 99 = 96354；
　　使用31作为乘数因子是因为它是一个比较合适大小的质数：如值过小，当参与计算hashcode的项数较少时会导致积过小；如为偶数，则相当于是
左位移，当乘法溢出时会造成有规律的位信息丢失。而这两者都会导致重复率增加。
已经得到了重复率很低的hashCode
⑴ 扰动函数
为什么要先无符号右位移16位，然后再执行异或运算？
你会发现只要二进制数后4位不变，前面的二进制位无论如何变化都会出现相同的结果。为了防止出现这种高位变化而低位不变导致的运算结果相同的
情况，因此需要将高位的变化也加入进来，而将整数的二进制位上半部与下半部进行异或操作就可以得到这个效果。

#### 归纳
简单地说，HashMap 在底层将 key-value 当成一个整体进行处理，这个整体就是一个 Entry 对象。HashMap 底层采用一个 Entry[] 数组来保存
所有的 key-value 对，当需要存储一个 Entry 对象时，会根据 hash 算法来决定其在数组中的存储位置，在根据 equals 方法决定其在该数组位
置上的链表中的存储位置；当需要取出一个Entry 时，也会根据 hash 算法找到其在数组中的存储位置，再根据 equals 方法从该位置上的链表中
取出该Entry。
#### 扩容
默认的的`负载因子 0.75` 是对空间和时间效率的一个平衡选择。
默认情况下，数组大小为 16，那么当 HashMap 中元素个数超过 16*0.75=12 的时候，就把数组的大小扩展为` 2*16=32，即扩大一倍，`然后重新计
算每个元素在数组中的位置，而这是一个非常消耗性能的操作，所以如果我们已经预知 HashMap 中元素的个数，那么预设元素的个数能够有效的提高
HashMap 的性能。

#### 负载因子 0.75 的原因
负载因子本身就是在时间和时间之间的折衷。当我使用较小的负载因子时，虽然降低了冲突的可能性，使得单个链表的长度减小了，加快了访问和更新
的速度，但是它占用了更多的控件，使得数组中的大部分控件没有得到利用，元素分布比较稀疏，同时由于 Map 频繁的调整大小，可能会降低性能。
但是如果负载因子过大，会使得元素分布比较紧凑，导致产生冲突的可能性加大，从而访问、更新速度较慢。所以我们一般推荐不更改负载因子的值，
采用默认值 0.75.

#### 疑问：如果两个key通过hash%Entry[].length得到的index相同，会不会有覆盖的危险？
　　这里HashMap里面用到链式数据结构的一个概念。上面我们提到过Entry类里面有一个next属性，作用是指向下一个Entry。打个比方， 
第一个键值对A进来，通过计算其key的hash得到的index=0，记做:Entry[0] = A。一会后又进来一个键值对B，通过计算其index也等于0，
现在怎么办？HashMap会这样做:B.next = A,Entry[0] = B,如果又进来C,index也等于0,那么C.next = B,Entry[0] = C；这样我们发现index=0
的地方其实存取了A,B,C三个键值对,他们通过next这个属性链接在一起。所以疑问不用担心。也就是说数组中存储的是最后插入的元素。到这里为止
### jdk8
`HashMap是数组+链表+红黑树`
先查看 table[i] 是否为 treeNode，如果是红黑树插入键值对，不是遍历链表，`链表长度大于8转换为红黑树`，插入键值对，小于8，链表插入
####  解决hash冲突的办法
1.开放地址法（线性探测再散列，二次探测再散列，伪随机探测再散列）
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
#### 1、开放地址法——线性探测
线性探测法的地址增量di = 1, 2, ... , m-1，其中，i为探测次数。该方法一次探测下一个地址，知道有空的地址后插入，若整个空间都找不到
空余的地址，则产生溢出。
线性探测容易产生“聚集”现象。当表中的第i、i+1、i+2的位置上已经存储某些关键字，则下一次哈希地址为i、i+1、i+2、i+3的关键字都将企图填入
到i+3的位置上，这种多个哈希地址不同的关键字争夺同一个后继哈希地址的现象称为“聚集”。聚集对查找效率有很大影响。

#### 2、开放地址法——二次探测
二次探测法的地址增量序列为 di = 12， -12， 22， -22，… ， q2, -q2 (q <= m/2)。二次探测能有效避免“聚集”现象，但是不能够探测到
哈希表上所有的存储单元，但是至少能够探测到一半。

#### 3、链地址法
链地址法也成为拉链法。其基本思路是：将所有具有相同哈希地址的而不同关键字的数据元素连接到同一个单链表中。如果选定的哈希表长度为m，
则可将哈希表定义为一个有m个头指针组成的指针数组T[0..m-1]，凡是哈希地址为i的数据元素，均以节点的形式插入到T[i]为头指针的单链表中。
并且新的元素插入到链表的前端，这不仅因为方便，还因为经常发生这样的事实：新近插入的元素最优可能不久又被访问。

#### 4、再散列（双重散列，多重散列）
当发生冲突时，使用第二个、第三个、哈希函数计算地址，直到无冲突时。缺点：计算时间增加。

####  建立一个公共溢出区
设哈希函数产生的哈希地址集为[0，m-1]，则分配两个表：
一个基本表ElemType base_tbl[m]；每个单元只能存放一个元素；
一个溢出表ElemType over_tbl[k]；只要关键码对应的哈希地址在基本表上产生冲突，则所有这样的元素一律存入该表中。查找时，对给定值kx 
通过哈希函数计算出哈希地址i，先与基本表的base_tbl[i]单元比较，若相等，查找成功；否则，再到溢出表中进行查找。

### ConcurrentHashMap 的实现原理
### jdk7
ConcurrentHashMap 的结构是比较复杂的，都深究去本质，其实也就是数组和链表而已。 `锁分段技术`
ConcurrentHashMap 数据结构为一个 Segment 数组，Segment 的数据结构为 HashEntry 的数组，而 `HashEntry 存的是我们的键值对`，可以构成链表。
ConcurrentHashMap 的成员变量中，包含了一个 Segment 的数组（final Segment<K,V>[] segments;），而 Segment 是 ConcurrentHashMap 
的`内部类`，然后`在 Segment 这个类中，包含了一个 HashEntry 的数组`（`transient volatile HashEntry<K,V>[] table;`）。而 HashEntry 
也是 ConcurrentHashMap 的`内部类`。HashEntry 中，包含了 key 和 value 以及 next 指针（类似于 HashMap 中 Entry），所以 HashEntry 
可以构成一个链表。
Segment继承自`ReentrantLock`，所以我们可以很方便的对每一个Segment上锁。
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

#### 不同之处
ConcurrentHashMap与HashMap相比，有以下不同点
ConcurrentHashMap线程安全，而HashMap非线程安全
`HashMap允许Key和Value为null，而ConcurrentHashMap不允许`
HashMap不允许通过Iterator遍历的同时通过HashMap修改，而ConcurrentHashMap允许该行为，并且该更新对后续的遍历可见

### jdk8
参考博客 [ConcurrentHashMap博客] 和 [ConcurrentHashMap阿里云栖社区]

`Java 7`为实现并行访问，引入了Segment这一结构，实现了分段锁，`理论上最大并发度与Segment个数相等`。`Java 8为进一步提高并发性`，
摒弃了分段锁的方案，而是直接使用一个`大的数组`。同时为了提高哈希碰撞下的`寻址性能`，Java 8在链表长度超过一定`阈值（8）`时将链表
（寻址时间复杂度为O(N)）转换为`红黑树`（寻址时间复杂度为O(log(N))）。
Java 8的ConcurrentHashMap作者认为引入红黑树后，即使哈希冲突比较严重，寻址效率也足够高

CAS算法；unsafe.compareAndSwapInt(this, valueOffset, expect, update);  `CAS(Compare And Swap)`，意思是如果valueOffset位置
包含的值与expect值相同，则更新valueOffset位置的值为update，并返回true，否则不更新，返回false。
与Java8的HashMap有相通之处，底层依然由`“数组”+链表+红黑树`；
底层结构存放的是TreeBin对象，而不是TreeNode对象；
CAS作为知名无锁算法，那ConcurrentHashMap就没用锁了么？当然不是，hash值相同的`链表的头结点还是会synchronized上锁`。 
在ConcurrentHashMap中，随处可以看到U, 大量使用了U.compareAndSwapXXX的方法，这个方法是利用一个CAS算法实现无锁化的修改值的操作，
他可以大大降低锁代理的性能消耗。
不允许null的键 
可以看出，ConcurrentHashMap和HashMap在对待null键的情况下截然不同，HashMap专门开辟了一块空间用于存储null键的情况，而ConcurrentHashMap则直接抛出空值针异常。 

### LinkedHashMap 的实现原理
`LinkedHashMap 可以选择按照访问顺序进行排序。`
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


### WeekHashMap
WeakHashMap，从名字可以看出它是某种 Map。它的特殊之处在于 WeakHashMap 里的entry可能会被GC自动删除，即使程序员没有调用remove()或者clear()方法。
更直观的说，当使用 WeakHashMap 时，即使没有显示的添加或删除任何元素，也可能发生如下情况：
- 调用两次size()方法返回不同的值；
- 两次调用isEmpty()方法，第一次返回false，第二次返回true；
- 两次调用containsKey()方法，第一次返回true，第二次返回false，尽管两次使用的是同一个key；
- 两次调用get()方法，第一次返回一个value，第二次返回null，尽管两次使用的是同一个对象。
遇到这么奇葩的现象，你是不是觉得使用者一定会疯掉？其实不然，WeekHashMap 的这个特点特别`适用于需要缓存的场景`。在缓存场景下，
由于内存是有限的，不能缓存所有对象；对象缓存命中可以提高系统效率，但缓存MISS也不会造成错误，因为可以通过计算重新得到。
要明白 WeekHashMap 的工作原理，还需要引入一个概念：弱引用（WeakReference）。我们都知道Java中内存是通过GC自动管理的，
GC会在程序运行过程中自动判断哪些对象是可以被回收的，并在合适的时机进行内存释放。GC判断某个对象是否可被回收的依据是，
是否有有效的引用指向该对象。如果没有有效引用指向该对象（基本意味着不存在访问该对象的方式），那么该对象就是可回收的。
这里的“有效引用”并不包括弱引用。也就是说，虽然弱引用可以用来访问对象，但进行垃圾回收时弱引用并不会被考虑在内，仅有弱引用指向的对象仍然会被GC回收。
`WeakHashMap 内部是通过弱引用来管理entry的`， WeakHashMap 将一对key, value放入到 WeakHashMap里并不能避免该key值被GC回收，
除非在 WeakHashMap 之外还有对该key的强引用。


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
ArrayList 实现了 RandomAccess 接口，即提供了随机访问功能。RandomAccess 是 java 中用来被 List 实现，为 List 提供快速访问功能的。
在 ArrayList 中，我们即可以通过元素的序号快速获取元素对象；这就是快速随机访问。
ArrayList 实现了 Cloneable 接口，即覆盖了函数 clone()，能被克隆。 
ArrayList 实现 java.io.Serializable 接口，这意味着 ArrayList `支持序列化`，能通过序列化去传输。
 
#### 扩容
 if (minCapacity - elementData.length > 0)
            grow(minCapacity);
分析：如果最低要求的存储能力>ArrayList已有的存储能力，这就表示ArrayList的存储能力不足，因此需要调用 grow();方法进行扩容             
数组进行扩容时，会将老数组中的元素重新拷贝一份到新的数组中，每次数组容量的增长大约是其原容量的` 1.5 `倍
（从int newCapacity = oldCapacity + (oldCapacity >> 1)这行代码得出）。这种操作的代价是很高的，因此在实际使用时，
我们应该尽量避免数组容量的扩张。


### LinkedList
LinkedList 和 ArrayList 一样，都实现了 List 接口，但其内部的数据结构有本质的不同。LinkedList 是基于链表实现的
（通过名字也能区分开来），所以它的插入和删除操作比 ArrayList 更加高效。但也是由于其为基于链表的，所以随机访问的效率要比 ArrayList 差。

#### 数据结构
LinkedList 是基于链表结构实现，所以在类中包含了 `first 和 last `两个指针(Node)。Node 中包含了上一个节点和下一个节点的引用，这样就
构成了双向的链表。每个 Node 只能知道自己的前一个节点和后一个节点，但对于链表来说，这已经足够了。



### Fail-Fast 机制
ArrayList HashMap ...
#### 原理
一种错误检测机制 多线程 源代码中通过 modCount 来实现，修改次数

（1）单线程环境
集合被创建后，在遍历它的过程中修改了结构。  比如在 iterator 中遍历时 put 进去
注意 remove()方法会让expectModcount和modcount 相等，所以是不会抛出这个异常。
（2）多线程环境
当一个线程在遍历这个集合，而另一个线程对这个集合的结构进行了修改。

```java
public class test {
    public static void main(String[] args){
      Iterator<String> iter = list.iterator();
      int j=0;
      while(iter.hasNext())
      {
          System.out.println(iter.next());
          if(j==3)
          {
               list.remove(0);  // 出现 ConcurrentModificationException。
               iter.remove();   // （单线程下）不会引发ConcurrentModificationException。但迭代器也只有这个修改相关的操作。
          }
          j++;
      }
    }
}
```

我们知道 java.util.HashMap 不是线程安全的，因此如果在使用迭代器的过程中有其他线程修改了 map，那么将抛出 
ConcurrentModificationException，这就是所谓 fail-fast 策略。
fail-fast 机制是 java 集合(Collection)中的一种错误机制。 当`多个线程对同一个集合的内容进行操作`时，就可能会产生 fail-fast 事件。
例如：`当某一个线程 A 通过 iterator去遍历某集合的过程中，若该集合的内容被其他线程所改变了；那么线程 A 访问集合时，就会抛出
 ConcurrentModificationException 异常，产生 fail-fast 事件。`
这一策略在源码中的实现是通过 modCount 域，`modCount 顾名思义就是修改次数`，对 HashMap 内容（当然不仅仅是 HashMap 才会有，其他例如
 ArrayList 也会）的修改都将增加这个值（大家可以再回头看一下其源码，在很多操作中都有 modCount++ 这句），那么在迭代器初始化过程中会
 将这个值赋给迭代器的 expectedModCount。
#### 哪些符合
- Map:
 HashMap
 LinkedHashMap
 TreeMap
 Hashtable 虽然 Hashtable 是线程安全的
- Set:
 HashSet
 LinkedHashSet
 TreeSet
- List:
 ArrayList
 LinkedList
 Vector 虽然 Vector 是线程安全的
使用 Collections.synchronizedXXX() 创建的线程安全的集合也是 Fail-fast
###### java.util包下面的所有的集合类都是快速失败的，而java.util.concurrent包下面的所有的类都是安全失败的。
### fail-safe机制
fail-safe任何对集合结构的修改都会在一个`复制的集合上`进行修改，因此不会抛出ConcurrentModificationException
fail-safe机制有两个问题
（1）需要复制集合，产生大量的无效对象，`开销大`
（2）无法保证读取的数据是目前原始数据结构中的数据。 `无法保证数据最新`
 
### ThreadLocal 
ThreadLocal 的作用是提供`线程内的局部变量`，这种变量在线程的生命周期内起作用，减少同一个线程内多个函数或者组件之间一些公共变量的传递的复杂度。
参考 [深入分析 ThreadLocal 内存泄漏问题]
ThreadLocal是一个关于`创建线程局部变量的类`。
通常情况下，我们创建的变量是可以被任何一个线程访问并修改的。而使用ThreadLocal创建的变量只能被当前线程访问，其他线程则无法访问和修改。
#### 基本工作原理
ThreadLocal的实现是这样的：每个Thread 维护一个 ThreadLocalMap 映射表，这个映射表的 key 是 ThreadLocal 实例本身，value 是真正需要存储的 Object。
也就是说 ThreadLocal 本身并不存储值，它只是作为一个 key 来让线程从 ThreadLocalMap 获取 value。值得注意的是图中的虚线，表示 
ThreadLocalMap 是使用 ThreadLocal 的弱引用作为 Key 的，弱引用的对象在 GC 时会被回收。

每个线程对象的 threadLocals 属性内部都有一个 Hash 表，用来存放我们通过 ThreadLocal 对象在当前线程设置的值，Hash 值根据 
ThreadLocal 对象的 hashCode 计算得出，当获取值的时候，就通过这个 Hash 值在当前线程对象的 threadLocals 属性内部的 Hash 表里寻找。

#### 设置值
下面是ThreadLocal的set方法，大致意思为
- 首先获取当前线程 
- 利用当前线程作为句柄获取一个`ThreadLocalMap的对象` getMap(t)函数
- 如果上述ThreadLocalMap对象不为空，则设置值，否则创建这个ThreadLocalMap对象并设置值
总结：实际上`ThreadLocal的值是放入了当前线程的一个ThreadLocalMap实例中`，所以只能在本线程中访问，其他线程无法访问。
#### 对象存放在哪里
在Java中，栈内存归属于单个线程，每个线程都会有一个栈内存，其存储的变量只能在其所属线程中可见，即`栈内存可以理解成线程的私有内存`。
`而堆内存中的对象对所有线程可见`。堆内存中的对象可以被所有线程访问。
#### 问：那么是不是说ThreadLocal的实例以及其值存放在栈上呢？
其实不是，因为ThreadLocal实例实际上也是被其创建的类持有（更顶端应该是被线程持有）。而ThreadLocal的值其实也是被线程实例持有。
它们都是`位于堆上`，只是通过一些技巧将可见性修改成了线程可见。

#### 内存泄漏
其实，ThreadLocalMap的设计中已经考虑到这种情况，也加上了一些防护措施：使用弱引用，`在ThreadLocal的get(),set(),remove()的时候都会清除
线程ThreadLocalMap里所有key为null的value。`但是这些被动的预防措施并不能保证不会内存泄漏：
- 使用static的ThreadLocal，延长了ThreadLocal的生命周期，可能导致的内存泄漏。
- 分配使用了ThreadLocal又不再调用get(),set(),remove()方法，那么就会导致内存泄漏。

#### 使用场景
- 实现单个线程单例以及单个线程上下文信息存储，比如交易id等
- 实现线程安全，非线程安全的对象使用ThreadLocal之后就会变得线程安全，因为每个线程都会有一个对应的实例
- 承载一些线程相关的数据，避免在方法中来回传递参数

```java
public class ThreadLocalTest {  
      
    private static People people = new People();  
      
    private static ThreadLocal<People> threadLocal = new ThreadLocal<People>(){  
        public People initialValue(){  
            return people;  
        }  
    };  
      
    public static void main(String[] args) throws InterruptedException {  
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                threadLocal.get().age = 5;  
                System.out.println("people = " + threadLocal.get() +  "age = " + threadLocal.get().age);  
            }  
        }).start();  
          
        Thread.sleep(10);  
          
        new Thread(new Runnable() {  
            @Override  
            public void run() {  
                System.out.println("people = " + threadLocal.get() +  "age = " + threadLocal.get().age);  
            }  
        }).start();  
    }  
  
}  
  
class People {  
    public int age;  
}
```


### TreeMap
TreeMap的实现是红黑树算法的实现
参考 [TreeMap和红黑树]
### 红黑树
红黑树又称红-黑二叉树，它首先是一颗二叉树，它具体二叉树所有的特性。同时红黑树更是一颗`自平衡的排序二叉树`。

平衡二叉树必须具备如下特性：它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
也就是说该二叉树的任何一个等等子节点，其左右子树的高度都相近。

红黑树顾名思义就是节点是红色或者黑色的平衡二叉树，它通过颜色的约束来维持着二叉树的平衡。对于一棵有效的红黑树二叉树而言我们必须增加如下规则：
1、每个节点都只能是红色或者黑色
2、根节点是黑色
3、每个叶节点（NIL节点，空节点）是黑色的。
4、如果一个结点是红的，则它两个子节点都是黑的。也就是说在一条路径上不能出现相邻的两个红色结点。
5、从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。

对于红黑二叉树而言它主要包括三大基本操作：`左旋、右旋、着色`。
`红黑树（Red-Black Tree，以下简称RBTree）`的实际应用非常广泛，比如Linux内核中的完全公平调度器、`高精度计时器`、`ext3文件系统`等等，
各种语言的函数库如Java的`TreeMap`和`TreeSet`，C++ STL的map、multimap、multiset等。
TreeMap 按照 key 排序，TreeSet 按照里面的值排序跟 priorityQueue 一样，不过优先队列是最小堆实现。


### CopyOnWriteArrayList
CopyOnWriteArrayList是jdk `concurrent`包中提供的一个`非阻塞型`的，`线程安全`的List实现。
CopyOnWriteArrayList在进行数据修改时，都不会对数据进行锁定，每次修改时，先`拷贝整个数组`，然后修改其中的一些元素，完成上述操作后，
替换整个数组的指针。
对CopyOnWriteArrayList进行读取时，也不进行数据锁定，直接返回需要查询的数据，如果需要返回整个数组，那么会将整个数组`拷贝`一份，再返回，
保证内部array在任何情况下都是只读的。
#### 应用场景
正因为上述读写特性，如果需要频繁对CopyOnWriteArrayList进行修改，而很少读取的话，那么会严重降低系统性能。
因为没有锁的干预，所以CopyOnWriteArrayLIst`在少量修改，频繁读取的场景下，有很好的并发性能`。

CopyOnWriteArrayList:CopyOnWriteArrayList这是一个ArrayList的线程安全的变体，其原理大概可以通俗的理解为:初始化的时候只有一个容器，
很常一段时间，这个容器数据、数量等没有发生变化的时候，大家(多个线程)，都是读取(假设这段时间里只发生读取的操作)同一个容器中的数据，
所以这样大家读到的数据都是唯一、一致、安全的，但是后来有人往里面增加了一个数据，这个时候CopyOnWriteArrayList 底层实现添加的原理是
先copy出一个容器(可以简称`副本`)，再往新的容器里添加这个新的数据，最后把新的容器的引用地址赋值给了之前那个旧的的容器地址，但是在添加
`这个数据的期间，其他线程如果要去读取数据，仍然是读取到旧的容器里的数据`。


#### 优点:
1. 解决的开发工作中的多线程的并发问题。
#### 缺点:
1. 内存占有问题:很明显，两个数组同时驻扎在内存中，如果实际应用中，数据比较多，而且比较大的情况下，`占用内存会比较大`，针对这个其实可以
用ConcurrentHashMap来代替。
2. 数据一致性:CopyOnWrite容器只能保证数据的最终一致性，`不能保证数据的实时一致性`。所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器











[学习网站]:http://wiki.jikexueyuan.com/project/java-collection/hashmap.html
[美团点评技术团队]:https://tech.meituan.com/java-hashmap.html
[ConcurrentHashMap博客]:http://www.jasongj.com/java/concurrenthashmap/
[ConcurrentHashMap阿里云栖社区]:https://yq.aliyun.com/articles/36781#
[哈希表博客]:http://blog.csdn.net/u011080472/article/details/51177412
[TreeMap和红黑树]:http://blog.csdn.net/chenssy/article/details/26668941
[深入分析 ThreadLocal 内存泄漏问题]:http://blog.xiaohansong.com/2016/08/06/ThreadLocal-memory-leak/