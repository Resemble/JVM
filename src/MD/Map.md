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

### ConcurrentHashMap 的实现原理
ConcurrentHashMap 的结构是比较复杂的，都深究去本质，其实也就是数组和链表而已。
ConcurrentHashMap 数据结构为一个 Segment 数组，Segment 的数据结构为 HashEntry 的数组，而 HashEntry 存的是我们的键值对，可以构成链表。
ConcurrentHashMap 的成员变量中，包含了一个 Segment 的数组（final Segment<K,V>[] segments;），而 Segment 是 ConcurrentHashMap 
的内部类，然后在 Segment 这个类中，包含了一个 HashEntry 的数组（`transient volatile HashEntry<K,V>[] table;`）。而 HashEntry 
也是 ConcurrentHashMap 的`内部类`。HashEntry 中，包含了 key 和 value 以及 next 指针（类似于 HashMap 中 Entry），所以 HashEntry 
可以构成一个链表。





















[学习网站]:http://wiki.jikexueyuan.com/project/java-collection/hashmap.html