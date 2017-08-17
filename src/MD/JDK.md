## JDK
### Java8 新增了非常多的特性，我们主要讨论以下几个：
- Lambda 表达式 − Lambda允许把函数作为一个方法的参数（函数作为参数传递进方法中。->
- 方法引用 − 方法引用提供了非常有用的语法，可以直接引用已有Java类或对象（实例）的方法或构造器。与lambda联合使用，方法引用可以使语言
的构造更紧凑简洁，减少冗余代码。 `方法引用使用一对冒号(::)。`
```java
class test {
    public static void main(String[] args){
        final Car car = Car.create( Car::new );
          final List< Car > cars = Arrays.asList( car );
          cars.forEach( Car::repair );  // repair 为 Car 的方法
    }
  
}
```
- 函数式接口 - 函数式接口可以被隐式转换为lambda表达式。函数式接口可以现有的函数友好地支持 lambda。

- `默认方法` − 默认方法就是一个在`接口里面有了一个实现的方法。`  默认方法和抽象方法之间的区别在于抽象方法需要实现，而默认方法不需要。
```java
private interface Defaulable {
    // Interfaces now allow default methods, the implementer may or 
    // may not implement (override) them.
    default String notRequired() { 
        return "Default implementation"; 
    }        
}

private static class DefaultableImpl implements Defaulable {
}

private static class OverridableImpl implements Defaulable {
    @Override
    public String notRequired() {
        return "Overridden implementation";
    }
}

```
- 新工具 − 新的编译工具，如：Nashorn引擎 jjs、 类依赖分析器jdeps。
- `Stream API` −新添加的Stream API（java.util.stream） 把真正的`函数式编程风格引入到Java中。`
```java
class test {
    List<Integer> transactionsIds = 
    widgets.stream()
                 .filter(b -> b.getColor() == RED)
                 .sorted((x,y) -> x.getWeight() - y.getWeight())
                 .mapToInt(Widget::getWeight)
                 .sum();
}
```
             
- Date Time API − `加强对日期与时间的处理。`
// 获取当前的日期时间
LocalDateTime currentTime = LocalDateTime.now();

-  重复注解
  自从Java 5中引入注解以来，这个特性开始变得非常流行，并在各个框架和项目中被广泛使用。不过，注解有一个很大的限制是：在同一个地方
  不能多次使用同一个注解。Java 8打破了这个限制，引入了重复注解的概念，允许在同一个地方多次使用同一个注解。
```java
    @Filter( "filter1" )
    @Filter( "filter2" )
    public interface Filterable {        
    }

```
- Optional 类 − Optional 类已经成为 Java 8 类库的一部分，用来解决空指针异常。`允许为 null`
- Nashorn, JavaScript 引擎 − Java 8提供了一个新的Nashorn javascript引擎，它允许我们在JVM上运行特定的javascript应用。
- concurrent 包修改 ConcurrentHashMap 大数组、HashEntry 里面值超过8个自动转为红黑树,Java 8在链表长度超过一定`阈值（8）`时
将链表（寻址时间复杂度为O(N)）转换为`红黑树`（寻址时间复杂度为O(log(N))）。

- Base64 在Java 8中，Base64编码已经成为Java类库的标准。Java 8 内置了 Base64 编码的编码器和解码器。
 // 使用基本编码
String base64encodedString = Base64.getEncoder().encodeToString("runoob?java8".getBytes("utf-8"));
- 并发性
  基于新增的lambda表达式和steam特性，为Java 8中为java.util.concurrent.ConcurrentHashMap类添加了新的方法来支持聚焦操作；


### Jdk9 新特性
1. Java 平台级模块系统
Java 9 的定义功能是一套全新的模块系统。
模块化的JAR文件包含了一个附加的模块描述符。在这个模块描述符中，通过requires语句来表示对其他模块的依赖，此外，exports语句可以控制
哪些package可被其他模块使用。默认情况下，所有未导出的package都封装在模块中不被其他模块可见。下述是定义在module-info.java中的模块描述符的一个例子：
module blog {
  exports com.pluralsight.blog;
  requires cms;
}
当启动模块化应用程序时，JVM会根据“require”语句（比起类路径解析，粒度上上升了一个层面）来验证是否可以解析所有模块。模块化可以让
开发人员通过强大的封装和显式依赖性来更好地构建大型应用。
2. 装配（Linking）
通过创建针对应用程序进行优化的最小运行时镜像，开发人员将不再需要使用完全加载的JDK安装环境来运行应用程序。
3. JShell：Java交互式REPL
4. 改进的Javadoc
Javadoc现在提供了API文档中的搜索功能。Javadoc的输出现在也符合HTML5标准。此外，你会注意到，每个Javadoc页面都包含有关JDK模块类或接口来源的信息。
5. 集合类的工厂方法
以前，在代码中创建并初始化一个集合（例如，List或Set），需要实例化一个集合类的具体实现，然后通过调用“add”方法进行元素填充。这会导致重复的代码。Java 9添加了几种集合工厂方法来简化上述需求：
Set<Integer> ints = Set.of(1, 2, 3);
List<String> strings = List.of("first", "second");
6. Stream API改进
Streams API可以说是Java长期演进过程中对标准库的最佳改进之一。
7. 私有接口方法
`Java 8支持在接口添加默认方法，因此，接口现在也可以包含行为，而不仅仅是方法签名。`可是，如果在接口中有几个默认方法，代码几乎相同，
会发生什么？通常，我们需要重构这些方法来调用包含共享功能的私有方法。现在的问题是：默认方法不能是私有的。使用共享代码创建另一个默认方法
不是一个可行的解决方案，因为该帮助方法会成为公共API的一部分。在Java 9中，可以向接口添加私有的帮助方法来解决此问题：
```java
public interface MyInterface {
    void normalInterfaceMethod();
    default void interfaceMethodWithDefault() {  init(); }
    default void anotherDefaultMethod() { init(); }
    // This method is not part of the public API exposed by MyInterface
    private void init() { System.out.println("Initializing"); }
}
```
8. HTTP/2
Java 9提供了一种HTTP调用的新方式。对于旧的“HttpURLConnetion”API来说，这个迟来的替换也添加了对WebSockets和HTTP/2的支持。注意：新的HttpClient API在Java 9中作为所谓的孵化器模块提供，这意味着API不能100％的保证是最终版本。不过，随着Java 9的到来，开发人员总是可以开始使用这个API了：
```java
class test {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest req =
       HttpRequest.newBuilder(URI.create("http://www.google.com"))
                  .header("User-Agent","Java")
                  .GET()
                  .build();
}
```
9. 多版本JAR
需要强调的最后一个功能对于类库维护者来说特别好。当新版本的Java出来时，某个类库的所有用户通常需要几年时间才能切换到这个新版本。


- jdk9将对Unsafe方法调整， sun.misc.Unsafe 将全部实现委托给 jdk.internal.misc.Unsafe
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
