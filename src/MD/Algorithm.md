## 算法

### Dijkstra算法
1.定义概览
Dijkstra(迪杰斯特拉)算法是典型的`单源最短路径算法`，用于计算`一个节点到其他所有节点的最短路径`。主要特点是以起始点为中心向外层层
扩展，直到扩展到终点为止。Dijkstra算法是很有代表性的最短路径算法，在很多专业课程中都作为基本内容有详细的介绍，如数据结构，图论，
运筹学等等。注意该算法要求图中不存在负权边。
问题描述：在无向图 G=(V,E) 中，假设每条边 E[i] 的长度为 w[i]，找到由顶点 V0 到其余各点的最短路径。（单源最短路径）
2.算法描述
1)算法思想：设G=(V,E)是一个带权有向图，把图中顶点集合V分成两组，第一组为已求出最短路径的顶点集合（用S表示，初始时S中只有一个源点，
以后每求得一条最短路径 , 就将加入到集合S中，直到全部顶点都加入到S中，算法就结束了），第二组为其余未确定最短路径的顶点集合（用U表示），
按最短路径长度的递增次序依次把第二组的顶点加入S中。在加入的过程中，总保持从源点v到S中各顶点的最短路径长度不大于从源点v到U中任何顶点
的最短路径长度。此外，每个顶点对应一个距离，S中的顶点的距离就是从v到此顶点的最短路径长度，U中的顶点的距离，是从v到此顶点只包括S中的
顶点为中间顶点的当前最短路径长度。
2)算法步骤：
a.初始时，S只包含源点，即S＝{v}，v的距离为0。U包含除v外的其他顶点，即:U={其余顶点}，若v与U中顶点u有边，则<u,v>正常有权值，若u不是v的出边邻接点，则<u,v>权值为∞。
b.从U中选取一个距离v最小的顶点k，把k，加入S中（该选定的距离就是v到k的最短路径长度）。
c.以k为新考虑的中间点，修改U中各顶点的距离；若从源点v到顶点u的距离（经过顶点k）比原来距离（不经过顶点k）短，则修改顶点u的距离值，修改后的距离值的顶点k的距离加上边上的权。
d.重复步骤b和c直到所有顶点都包含在S中。

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

### 稳定和不稳定
假定在待排序的记录序列中，存在多个具有相同的关键字的记录，若经过排序，这些记录的相对次序保持不变，即在原序列中，ri=rj，且ri在rj之前，
而在排序后的序列中，ri仍在rj之前，则称这种排序算法是稳定的；否则称为不稳定的。

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

### 快排优化
  4种优化：
（1）当待排序序列的长度分割到一定大小后，使用插入排序； 小段的插入快
（2）在一次分割结束后，可以把与Key相等的元素聚在一起，继续下次分割时，不用再对与key相等元素分割； 
（3）优化递归操作； 
```javascript 1.8
// 递归非常耗费内存，因为需要同时保存成千上百个调用记录，很容易发生"栈溢出"错误（stack overflow）。但对于尾递归来说，
// 由于只存在一个调用记录，所以永远不会发生"栈溢出"错误。

function factorial(n) {
  if (n === 1) return 1;
  return n * factorial(n - 1);
}

factorial(5) // 120
//上面代码是一个阶乘函数，计算n的阶乘，最多需要保存n个调用记录，复杂度 O(n) 。
// 如果改写成尾递归，只保留一个调用记录，复杂度 O(1) 。

function factorial(n, total) {
  if (n === 1) return total;
  return factorial(n - 1, n * total);
}

factorial(5, 1) // 120
```
（4）使用并行或多线程处理子序列； 

##### 快排中轴选择
- 选第一个
- 随机选
- 三数取中
- 最好一种
上述算法的时间O(n)是期望的，最坏情况下为O(n*n)。原因在于分割的好坏。下面来说说在最坏情况下仍为O(n)的快速选择算法：
(改写确定性的快速排序的partiton部分)
（1）将数据5个一组分割，每个组内用插入排序找到中位数
（2）再对找到的所有中位数用（1）递归地找它们的中位数
（3）把最终的中位数作为partition的分割元素
这样得到的快速选择算法可以在最坏情况下O(n)的时间效率找到第i小的元素
这样得到的快速选择算法可以在最坏情况下O(n)的时间效率找到第i小的元素
这样的算法真的是O(n)的吗？关键一点是证明可以在O(n)时间内找到最终的中位数


### 时间复杂度强化训练
假设某算法的时间复杂度符合递推关系式T(n)=2T(n/2)+n，那么该算法的时间复杂度相当于（） O(nlgn)
T(n)=2T(n/2),所以T(n)/n=T(n/2)/(n/2)+1,
T(n/2)/(n/2)=T(n/4)/(n/4)+1;
T(n/4)/(n/4)=T(n/8)/(n/8)+1;
.
.
T(2)/2=T(1)/1+1;
这些等式左右相加，T(n)/n=T(1)+1*log2(n);
即T(n)=nT(1)+n*long2(n);
复杂度为O(nlog2(n))

斐波拉契数列 f(n) = f(n-1) + f(n-2) 时间复杂度为 o(2^n)
（1） 先解方程 x^2 – x – 1 = 0；得到x1=(1+5^(1/2))/2，x2=(1-5^(1/2))/2。
（2）利用公式 u(j) = c1 * x1^(j) + c2 * x2^(j)，其中c1，c2为待定常数，利用u(0)=1,u(1)=1, 得出 
c1 = 1/(5^(1/2))， c2 = -c1。这样Fibonacci的通项公式就出来了。
因此T(n)= O( ((1+5^1/2)/2)^n – ((1-5^1/2)/2)^n  ) = O(2^n)。




### 图形逻辑推断
参考[图形逻辑推断]
1. 图形差，比如圆形和方形差
2. 既考察了叠加，还考察了求异。这个九宫格图形中第一行和第三行都是前两幅图叠加在一起得到第三幅图。颜色叠加规律就是：白+黑=白，黑+白=黑，白+白=黑，黑+黑=白。
3. 图形数量类。题干中每幅图形都只含有一种元素。因此以第三个图形为对称点，则第五个图形内部应该有一条线。
4. 本题属于位置类。题干中的五幅图形包含元素完全相同，那肯定是发生了位置上的变化。该题位置变化最明显的就是短线。短线移动的方向和距离相同。
5. 题干的这个九宫格图形每幅图都是轴对称图形，而且外围八个图形的对称轴可以组合成一个“米”字形状。
6. 本题属于`图形旋转和叠加题。` 叠加有抵消的考察。 先看第一组图，图中第三个图是由第一个图逆时针旋转90度，第二个图翻转，然后逆时针旋转90度，再叠加而成的。
7. 本题属于属性类。第一组图形都是竖轴对称，第二组都是横轴对称
8. 第一个图边数 第二图边数 递减 递增 和为第三个数边数
9. 左右上下移动




#### 查找N个元素中的第K个小的元素（来自编程珠玑） OR 求无序数组的中位数
编程珠玑给出了一个时间复杂度O（N），的解决方案。该方案改编自快速排序。
经过快排的一次划分，
   1）如果左半部份的长度>K-1，那么这个元素就肯定在左半部份了
   2）如果左半部份的长度==K-1，那么当前划分元素就是结果了。
   3）如果。。。。。。。<K-1,那么这个元素就肯定在右半部分了。
  并且，该方法可以用尾递归实现。效率更高。

时间复杂度分析， 由于差不多每次都是把序列划分为一半。。。假设划分的元素做了随机优化，时间复杂度近似于
N+N/2+N/4.... = 2N*(1-2^-(logN)) 当N较大时 约等于 2N 也就是 O（N）。
看来，快速排需的用处可大着咧。。。
也用来查找可以N个元素中的前K个小的元素，前K个大的元素。。。。等等。

#### 完全二叉树和满二叉树的定义。
满二叉树，所有的分支结点都存在左子树和右子树，并且所有叶子都在同一层上。深度为n的满二叉树的节点数为2^n - 1 ;
完全二叉树是除了叶子层，其他层都符合满二叉树定义的二叉树，所以完全二叉树最少的结点为2^（n-1） -1 +１　；


#### 初始堆建立
一组记录排序码为(5 11 7 2 3 17),则利用堆排序方法建立的初始堆为 (17 11 7 2 3 5)


#### 与运算  1&1才为1
int m = 1234;
while(m) {
 m = m & (m-1);  // 就是判断m二进制有多少个1
 // m 10011010010
}

##### 百度100盏灯问题
Q:
有100盏灯泡，第一轮点亮所有电灯，第二轮每两盏灯熄灭一盏，即熄灭第2盏，第4盏，以此类推，
第三轮改变编号为3的倍数的电灯，第3盏，第6盏，如果原来那盏灯是亮的，就熄灭它，如果原来是灭的，
就点亮它，以此类推，直到第100轮。问第100结束后，还有多少盏灯泡是亮的？
A：
1．对于每盏灯，拉动的次数是奇数时，灯就是亮着的，拉动的次数是偶数时，灯就是关着的。
2．每盏灯拉动的次数与它的编号所含约数的个数有关，它的编号有几个约数，这盏灯就被拉动几次。
3．1——100这100个数中有哪几个数，`约数的个数是奇数`。我们知道一个数的约数都是成对出现的，
只有完全平方数约数的个数才是奇数个。
所以这100盏灯中有10盏灯是亮着的。
它们的编号分别是： 1、4、9、16、25、36、49、64、81、100。


#### 前缀树(Tire树)和后缀树
#### 几种数据结构
AVL树:  最早的平衡二叉树之一。应用相对其他数据结构比较少。windows对进程地址空间的管理用到了AVL树。
红黑树: 平衡二叉树，广泛用在C++的STL中。如map和set都是用红黑树实现的。
B/B+树: 用在磁盘文件组织 数据索引和数据库索引。
Trie树(字典树): 用在统计和排序大量字符串，如自动机。

#### 如何判断一棵二叉树是平衡二叉树？
一颗平衡的二叉树是指其任意结点的左右子树深度之差不大于1。判断一棵二叉树是否是平衡的，可以使用递归算法来实现。

#### 完全二叉树 VS 满二叉树
满二叉树最后一层满的
完全二叉树最后一层可以不满

#### 具有n个节点的完全二叉树的深度为（） （向下取整）
   n
log   + 1
   2
#### 二叉树 
设一颗完全二叉树有500个节点，则该二叉树的深度为 9 层
若用二叉链表作为该完全二叉树的存储结构，共有 501 个空指针  511 - 500  = 11   256 * 2 - 11 = 501 
二叉树的叶子节点有250个

### 先手问题
100根火柴，拿到最后一根火柴就赢的必胜解法及思路
问题：100根火柴，两个人轮流取，每个人每次只能取1~7根，谁拿到最后一根火柴谁赢；问有必胜策略吗，有的话是先手还是后手必胜？
最近读了刘未鹏的博文“跟波利亚学解题”有感。我也尝试使用博文中提到的一下思维方法解决这一问题。 问题并不难，只为了记录思维过程。正如欧拉认为如果不能把解决数学问题背后的思维过程教给学生的话，数学教学就是没有意义的。
解题思路（归约法）：
反过来推导是一种极其重要的启发法，Pappus在他的宏篇巨著中将这种手法总结为解题的最重要手法。实际上，反向解题隐含了解题中至为深刻的思想：归约。如果原问题不容易解决，那么归约后的问题也许就容易解决了，通过一层层的归约，让逻辑的枝蔓从结论上一节节的生长，我们往往会发现，离已知量越来越近。
首先不考虑谁先抽取，如果假设Winner就是最后的赢家而Loser是输家。
第一步：从结论出发，谁拿到最后一根火柴谁赢。同时考虑约束条件每个人每次只能取1~7根。 因此Loser最后一次抽取火柴后必须剩下1-7根火柴，Winner才能成为最后的赢家。也就意味：Winner在其倒数第二次抽取完火柴后 ，剩余火柴的数量必须是8根，才能确保无论Loser在其最后一次怎么抽取火柴都要1-7根火柴留给Winner。
所以在第一步，我们将问题向上归约为：Winner必须在倒数第二次抽取完后将剩余火柴的总数量控制在8根。是不是感觉离已知条件（100根火柴）近了点
第二步，继续归约：怎样才能确保Winner在倒数第二次抽取完后将剩余火柴的总数量控制在8根？ 问题转换一下就是说，在Winner在进行倒数第二次抽取时，当时的火柴是多少根时，Winner能确保完成抽取后能剩下8根火柴？答案很显然是（9-15）根。Winner抽取（9-15）根中的1-7根就一定可以保证留8根给Loser。
分析到这里，问题归约为：如何确保Loser在Winer上一轮抽取后，一定剩下9-15根火柴？离已知条件（100根火柴）又近了点。
第三步，继续归约。从第二步归约出的问题思考：Loser倒数第二次抽取火柴后必须剩下9-15根火柴。是不是似曾相识啊？和第一步思考的问题一致。答案显然是：Winner在其倒数第三次抽取火柴后剩下16根火柴给Loser。
到这里，我们将问题向上归约为：Winner必须在倒数第三次抽取完后将剩余火柴的总数量控制在16根。离已知条件（100根火柴）又近了点。
第四步，从前三步的分析，似乎找到了某种规律。 可以通过表格尝试画出来.
 
 	   Begin	End
Winner	1-7	    0
Loser	8	    1-7
Winner	9-15	8
Loser	16	    9-15
Winner	17-23	16
Loser	24	    17-23
Winner	25-31	24
Loser	32	    25-31
Winner	33-39	32
Loser	...	...
...	...	...
...	...	...
...	...	...
Winner	100	96
 第五步，归纳一下得出结论：谁能确保抽完火柴后，剩下的火柴数量只要是8的倍数就一定是完胜。因为共100根，所以只要先下手为强抽调4根火柴就一定是最后的赢家。

#### 反概率问题
假设在一段高速公路上，30分钟之内见到汽车经过的概率是0.95。那么，在10分钟内见到汽车经过的概率是多少?(假设缺省概率固定)
答案：这题的关键在于0.95是见到一辆或多辆汽车的概率，而不是仅见到一辆汽车的概率。在30分钟内，见不到任何车辆的概率为0.05。
因此在10分钟内见不到任何车辆的概率是这个值的立方根，而在10分钟内见到一辆车的概率则为1减去此立方根，也就是大约63%。
 
#### 赛马问题
有25匹马，速度都不同，但每匹马的速度都是定值。现在只有5条赛道，无法计时，即每赛一场最多只能知道5匹马的相对快慢。问最少赛几场可以找出25匹马中速度最快的前3名？（百度2008年面试题）

每匹马都至少要有一次参赛的机会，所以25匹马分成5组，一开始的这5场比赛是免不了的。接下来要找冠军也很容易，每一组的冠军在一起赛一场就行了（第6场）。最后就是要找第2和第3名。我们按照第6场比赛中得到的名次依次把它们在前5场比赛中所在的组命名为A、B、C、D、E。即：A组的冠军是第6场的第1名，B组的冠军是第6场的第2名……每一组的5匹马按照他们已经赛出的成绩从快到慢编号：

A组：1，2，3，4，5
B组：1，2，3，4，5
C组：1，2，3，4，5
D组：1，2，3，4，5
E组：1，2，3，4，5

从现在所得到的信息，我们可以知道哪些马已经被排除在3名以外。只要已经能确定有3匹或3匹以上的马比这匹马快，那么它就已经被淘汰了。可以看到，只有上表中粗体蓝色的那5匹马才有可能为2、3名的。即：A组的2、3名；B组的1、2名，C组的第1名。取这5匹马进行第7场比赛，第7场比赛的前两名就是25匹马中的2、3名。故一共最少要赛7场。

这道题有一些变体，比如64匹马找前4名。方法是一样的，在得出第1名以后寻找后3名的候选竞争者就可以了。
64匹马找前4名：
1、如果这次排名，B2或C1能进前三名，则加上B1后，B1一定能进前三名，因为B1 排名比B2和C1都要靠前；
到此比赛可以结束了；这种情况8+1+1＝10次出结果；
2、如果这次排名，B2或C1不能进入前三名，则需要再进行一次比赛，B1、A2、A3、A4进行，取前三名：
 这种情况8+1+1+1=11次出结果。
 
#### 先放硬币赢
考虑一个双人游戏。游戏在一个圆桌上进行。每个游戏者都有足够多的硬币。他们需要在桌子上轮流放置硬币，每次必需且只能放置一枚硬币，要求硬币完全置于桌面内（不能有一部分悬在桌子外面），并且不能与原来放过的硬币重叠。谁没有地方放置新的硬币，谁就输了。游戏的先行者还是后行者有必胜策略？这种策略是什么？
答案：先行者在桌子中心放置一枚硬币，以后的硬币总是放在与后行者刚才放的地方相对称的位置。这样，只要后行者能放，先行者一定也有地方放。先行者必胜。
 
考虑一个双人游戏。游戏在一个圆桌上进行。每个游戏者都有足够多的硬币。他们需要在桌子上轮流放置硬币，每次必需且只能放置一枚硬币，要求硬币完全置于桌面内（不能有一部分悬在桌子外面），并且不能与原来放过的硬币重叠。谁没有地方放置新的硬币，谁就输了。游戏的先行者还是后行者有必胜策略？这种策略是什么？
答案：先行者在桌子中心放置一枚硬币，以后的硬币总是放在与后行者刚才放的地方相对称的位置。这样，只要后行者能放，先行者一定也有地方放。先行者必胜。
 
 
### 二进制1个数
如何快速找出一个32位整数的二进制表达里有多少个"1"？用关于"1"的个数的线性时间？
答案1（关于数字位数线性）：for(n=0; b; b >>= 1) if (b & 1) n++;    b & 1 可以判读奇偶性
答案2（关于"1"的个数线性）：for(n=0; b; n++) b &= b-1; 
 
 
### 判断给定的整数是否是一个2的幂。
答案：(b & (b-1)) == 0
 



### 二进制使用
1000个瓶子有999瓶白水和1瓶毒药，最少用多少只小白鼠可以找到毒药瓶子，限制是一周之后才会死，一周之后就要得到毒瓶子的是那一瓶，我告诉
他我猜的是10只（标准答案真的是10），他问我怎么考虑的，我说我不知道，我就是凭直觉（哈哈），然后讨论了很久，我还是没有想到解决办法，他有点失望了。
0 0 0 0 0 0 0 0 0 0 
小白鼠那一道题是对包含毒药在内的1000瓶溶剂进行编号1-1000，然后每一只小白鼠对应10位中的某一位，喝那一位为1的编号的溶剂，例如第一只
小白鼠喝第一位是1的所有试剂。这样通过观察小白鼠有那几只死了，就可以得到毒药的二进制编号，转换成十进制就可以找到毒药了。

根据2^10=1024，所以10个老鼠可以确定1000个瓶子具体哪个瓶子有毒。具体实现跟3个老鼠确定8个瓶子原理一样。
000=0001=1010=2011=3100=4101=5110=6111=7一位表示一个老鼠，0-7表示8个瓶子。也就是分别将1、3、5、7号瓶子的药混起来给老鼠1吃，
2、3、6、7号瓶子的药混起来给老鼠2吃，4、5、6、7号瓶子的药混起来给老鼠3吃，哪个老鼠死了，相应的位标为1。如老鼠1死了、老鼠2没死、
老鼠3死了，那么就是101=5号瓶子有毒。同样道理10个老鼠可以确定1000个瓶子


### 2个变量记录
一个无序数组，其中一个数字出现的次数大于其他数字之和，求这个数字
一个变量记录数字，一个变量记录次数

### 最大子数组
2个变量，i和j这2个指针，一个变量记录当前加的最大值=max（当前和+array[i]，array[i]）,一个变量记录最大值max=max(当前最大，所有最大)，


### 空瓶子
有这样一道智力题：“某商店规定：三个空汽水瓶可以换一瓶汽水。小张手上有十个空汽水瓶，她最多可以换多少瓶汽水喝？”答案是5瓶，方法如下：
先用9个空瓶子换3瓶汽水，喝掉3瓶满的，喝完以后4个空瓶子，用3个再换一瓶，喝掉这瓶满的，这时候剩2个空瓶子。然后你让老板先借给你一瓶汽水，
喝掉这瓶满的，喝完以后用3个空瓶子换一瓶满的还给老板。如果小张手上有n个空汽水瓶，最多可以换多少瓶汽水喝？ 
n/2


### 运煤
你是山西的一个煤老板，你在矿区开采了有3000吨煤需要运送到市场上去卖，从你的矿区到市场有1000公里，你手里有一列烧煤的火车，这个火车最多
只能装1000吨煤，且其能耗比较大——每一公里需要耗一吨煤。请问，作为一个懂编程的煤老板的你，你会怎么运送才能运最多的煤到集市？

火车回程要尽可能短。使火车回头的原因是火车容量为1000，理想状况为火车每次出发都装满1000的煤，所以火车第三次到达x点时共有煤3000-5x=2000，
求得x=200；火车第二次到达y点时站点有存煤1000-2y，火车有煤1000-y，共计2000-3y，理想状况为2000-3y=1000，y=333，火车到达终点剩下煤
1000-（1000-x-y）=533。



### SimHash是什么  去重 降维
SimHash是Google在2007年发表的论文《Detecting Near-Duplicates for Web Crawling 》中提到的一种指纹生成算法或者叫指纹提取算法，
被Google广泛应用在亿级的网页去重的Job中，作为locality sensitive hash（局部敏感哈希）的一种，其主要思想是降维，什么是降维？ 举个
通俗点的例子，一篇若干数量的文本内容，经过simhash降维后，可能仅仅得到一个长度为32或64位的二进制由01组成的字符串，这一点非常相似我们
的身份证，试想一下，如果你要在中国13亿+的茫茫人海中寻找一个人，如果你不知道这个人的身份证，你可能要提供姓名 ，住址， 身高，体重，性别，
等等维度的因素来确定是否为某个人，从这个例子已经能看出来，如果有一个一维的核心条件身份证，那么查询则是非常快速的，如果没有一维的身份证
条件，可能综合其他几个非核心的维度，也能确定一个人，但是这种查询则就比较慢了，而通过我们的SimHash算法，则就像是给每个人生成了一个身份证，
使复杂的事物，能够通过降维来简化。 




### 字典树 或 前缀树 或 TireTree
Trie树，即字典树，又称单词查找树或键树，是一种树形结构。典型应用是用于统计和排序大量的字符串（但不仅限于字符串），所以经常被搜索引擎系统用于文本词频统计。它的优点是最大限度地减少无谓的字符串比较，查询效率比较高。
Trie的核心思想是空间换时间，利用字符串的公共前缀来降低查询时间的开销以达到提高效率的目的。
1、一个文本文件，大约有一万行，每行一个词，要求统计出其中最频繁出现的前10个词，请给出思想，给出时间复杂度分析
提示：用trie树统计每个词出现的次数，时间复杂度是O(n*le)（le表示单词的平均长度），然后是找出出现最频繁的前10个词。当然，也可以用堆来实现，时间复杂度是O(n*lg10)。所以总的时间复杂度，是O(n*le)与O(n*lg10)中较大的哪一个。
2、寻找热门查询
原题：搜索引擎会通过日志文件把用户每次检索使用的所有检索串都记录下来，每个查询串的长度为1-255字节。假设目前有一千万个记录，这些查询串的重复读比较高，虽然总数是1千万，但是如果去除重复和，不超过3百万个。一个查询串的重复度越高，说明查询它的用户越多，也就越热门。请你统计最热门的10个查询串，要求使用的内存不能超过1G。
提示：利用trie树，关键字域存该查询串出现的次数，没有出现为0。最后用10个元素的最小推来对出现频率进行排序。



### java必记函数
Character.isDigit()  // 判断是否为数字
Arrays.sort()  // 排序


#### 蓄水池问题
2.在序列流中取一个数，如何确保随机性，即取出某个数据的概率为:1/(已读取数据个数)
假设已经读取n个数，现在保留的数是Ax，取到Ax的概率为(1/n)。
对于第n+1个数An+1，以1/(n+1)的概率取An+1，否则仍然取Ax。依次类推，可以保证取到数据的随机性。
数学归纳法证明如下：
当n=1时，显然，取A1。取A1的概率为1/1。
假设当n=k时，取到的数据Ax。取Ax的概率为1/k。
当n=k+1时，以1/(k+1)的概率取An+1，否则仍然取Ax。
(1)如果取Ak+1，则概率为1/(k+1)；
(2)如果仍然取Ax，则概率为(1/k)*(k/(k+1))=1/(k+1)
所以，对于之后的第n+1个数An+1，以1/(n+1)的概率取An+1，否则仍然取Ax。依次类推，可以保证取到数据的随机性。

3.在序列流中取k个数，如何确保随机性，即取出某个数据的概率为:k/(已读取数据个数)
建立一个数组，将序列流里的前k个数，保存在数组中。(也就是所谓的"蓄水池")
对于第n个数An，以k/n的概率取An并以1/k的概率随机替换“蓄水池”中的某个元素；否则“蓄水池”数组不变。依次类推，可以保证取到数据的随机性。
数学归纳法证明如下：
当n=k是，显然“蓄水池”中任何一个数都满足，保留这个数的概率为k/k。
假设当n=m(m>k)时，“蓄水池”中任何一个数都满足，保留这个数的概率为k/m。
当n=m+1时，以k/(m+1)的概率取An，并以1/k的概率，随机替换“蓄水池”中的某个元素，否则“蓄水池”数组不变。则数组中保留下来的数的概率为：
k     k    k-1   m+1-k      k
—— * (—— * ——  + ——————) = ————         剩余水的存活率  
m     m+1  k      m+1       m+1
     An没被选中剩余 被选中
     
 k
————   An的存活率
 m+1



### 邻接表和邻接矩阵
[邻接表和邻接矩阵]
都是图的表示方式
邻接表是链表 
邻接矩阵是矩阵形式



#### 约瑟夫环问题
圆圈上顺时针排列着 1,2,3 ， ......n 这 n 个数，从 1 开始，顺时针隔一个拿走一个，问最后剩下是哪一个数字。
for (i=2; i<=n; i++)
{
s=(s+2)%i;   // 这个2代表隔2个
}


#### 对一万条数据排序，你认为最好的方式是什么 
申请长度为一千万位的位向量bit[10000000]，所有位设置为0，顺序读取待排序文件，每读入一个数i，便将bit[i]置为1。当所有数据读入完成，
便对bit做从头到尾的遍历，如果bit[i]=1，则输出i到文件，当遍历完成，文件则已排好序。本机运行耗时9秒49毫秒。 




#### 按照规定概率行事 哈哈哈
假设张三的mp3里有1000首歌，现在希望设计一种随机算法来随机播放。与普通随机模式不同的是，张三希望每首歌被随机抽到的概率是与一首歌的
豆瓣评分（0~10分）成正比的，如朴树的《平凡之路》评分为8.9分，逃跑计划的《夜空中最亮的星》评分为9.5分，则希望听《平凡之路》的概率与
《夜空中最亮的星》的概率比为89:95,。现在我们已知这1000首歌的豆瓣评分：
（1）请设计一种随机算法来满足张三的需求。
（2）请写代码实现自己的算法。
解：
将一千首歌存在数组当中，数组下标对应0-999，数组内容存歌曲的评分，随机取0-999中的一个数字，取到之后查看对应的评分，如果评分为9.5，
则随机取1-100中的数字，小于等于95则播放这首歌，否则重复上述过程。
这个方法好，还可以改动一下，当某一首歌曲被选中后，与最后一首歌曲调换位置，然后随机数减一，等到下一首被选中后，与倒数第二的
位置交换，随机数减一，依次类推。    


#### 比较硬币
9个硬币有一枚较轻需要比较两次
3 3 3
从有问题的三枚中，让其中2枚比较


1/3对应的问题研究对象应当是家庭。
问题可以还原为：<有两个孩子的家庭>，在事件<一个孩子是男孩>({BB,BG,GB}) 发生的条件下，求这个家庭是<有两个男孩子的家庭>（{BB}）的概率？
1/2对应的问题研究对象应当是孩子。
问题可以还原为：数学题：我一个老朋友有且只有两个孩子A,B。孩子A是男孩。求孩子B是<男孩>({B})的概率？
i.e. 一个<小孩>是<男孩>的概率而关键的区别归结为一点：研究对象的不同。





[图形逻辑推断]:http://www.zzxingce.com/xingce/tiku/zhenti/20140403/1160.html
[邻接表和邻接矩阵]:http://www.cnblogs.com/XMU-hcq/p/6065057.html