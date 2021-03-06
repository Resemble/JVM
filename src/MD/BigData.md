## 大数据
Hadoop，Spark和Storm是目前最重要的三大分布式计算系统，Hadoop常用于`离线的复杂的大数据处理`，Spark常用于`离线的快速的大数据处理`，
而Storm常用于`在线的实时的大数据处理`。

- 使用 sbt 打包，SBT 之于 Scala 就像 Maven 之于 Java，用于管理项目依赖，构建项目
- 写了个 sql ，在这个类运行之前我们 before 先把数据选出来，这个类运行完我们有一个 after 方法将数据持久化存到数据库里面
- 中间就是数据处理，通过隐式转换，程序员可以在编写Scala程序时故意漏掉一些信息，让编译器去尝试在编译期间自动推导出这些信息来，这种特性可以极大的减少代码量，忽略那些冗长，过于细节的代码。
然后就是多次  map  group by key  map  数 获得与去年相比 与上月相比 的数据，他们定制了一个规则，就是一棵树，树上有一些节点，然后我们把数据挂到节点上，然后把数据传给前端
最开始使用 xpath 进行数据节点的寻找，后面我们给了节点一个标记，一层一层往下找。

#### spark 基本流程
以SparkContext为程序运行的总入口，在SparkContext的初始化过程中，Spark会分别创建DAGScheduler作业和TaskScheduler任务调度两级调度模块。
其中作业调度模块是基于任务阶段的高层调度模块，它为每个Spark作业计算具有依赖关系的多个调度阶段（通常根据shuffle来划分），然后为每个
阶段构建出一组具体的任务（通常会考虑数据的本地性等），然后以TaskSets（任务组）的形式提交给任务调度模块来具体执行。而任务调度模块则
负责具体启动任务、监控和汇报任务运行情况。

#### YARN cluster模式和YARN client模式的区别在于：
YARN client模式的AM是`运行在提交任务的节点`，而YARN cluster模式的AM是由YARN在集群中`选取一个节点运行`，不一定是在提交任务的节点运行。

### Hive中内部表与外部表的区别： 
Hive 创建内部表时，会将`数据移动到数据仓库指向的路径`；若创建外部表，仅记录数据所在的`路径`， 
不对数据的位置做任何改变。`在删除表的时候，内部表的元数据和数据会被一起删除`， 
而`外部表只删除元数据，不删除数据`。这样外部表相对来说更加安全些，数据组织也更加灵活，方便共享源数据。 
外部表创建时有一个 location ，location后面跟的是目录，不是文件，hive会把整个目录下的文件都加载到表中：

```scala
object SimpleApp{
  def main(args:Array[String]){
    val logFile="/root/spark-1.1.0-bin-hadoop2.4/README.md"
    val conf=new SparkConf().setAppName("SimpleApp")
    val sc=new SparkContext(conf)
    val logData=sc.textFile(logFile,2).cache()
    val counts=logData.flatMap(line=>line.split(" ")).map(word=>(word,1)).reduceByKey((a,b)=>a+b).count()
    println("There are %s words in the file".format(counts))
  }
}
```

