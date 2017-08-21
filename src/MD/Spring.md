## spring

### Spring很受欢迎的原因有几点：
- Spring的依赖注入方式鼓励编写可测试代码。
- 具备简单但功能强大的数据库事务管理功能
- Spring简化了与其他Java框架的集成工作，比如JPA/Hibernate ORM和Struts/JSF等web框架。
- 构建web应用最先进的Web MVC框架。


### spring工作机制及为什么要用?【spring是一个轻量的控制反转和面向切面的容器框架】
1. springmvc把所有的请求都提交给DispatcherServlet,它会委托应用系统的其他模块负责对请求进行真正的处理工作。
2. DispatcherServlet查询一个或多个HandlerMapping,找到处理请求的Controller.
3. DispatcherServlet把请求提交到目标Controller
4. Controller进行业务逻辑处理后，会返回一个ModelAndView
5. Dispathcher查询一个或多个ViewResolver视图解析器,找到ModelAndView对象指定的视图对象
6. 视图对象负责渲染返回给客户端。
IoC就是由容器来控制业务对象之间的依赖关系。控制反转的本质，是`控制权由应用代码转到了外部容器`，控制器的转移既是所谓的反转。
`控制权的转移带来的好处就是降低了业务对象之间的依赖程度，即实现了解耦。`
DI/IOC,对持久层和表示层的控制与分配，增加系统的灵活性和稳定性. AOP,面向切面,利用代理对程序的有效管理.
spring是一个轻量级的IOC和AOP框架，通过spring的`IOC实现松耦合`，而作为`一个AOP框架他又能分离系统服务，实现内聚开发` Spring 最好的
地方是它有助于您替换对象。有了 Spring，只要用 `JavaBean 属性和配置文件加入依赖性（协作对象）`。然后可以很容易地在需要时替换具有类似
接口的协作对象。}
Spring对多种ORM框架提供了很好的支持

#### 为什么有AOP？
此时我们就不能通过抽象父类的方式消除以上的重复性代码，因为这些逻辑依附在业务类方法的流程中，它们不能够转移到其他地方去。


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

### springMVC的工作原理图：
1、客户端发出一个http请求给web服务器，web服务器对http请求进行解析，如果匹配DispatcherServlet的请求映射路径（在web.xml中指定），
web容器将请求转交给`DispatcherServlet.`
2、DipatcherServlet接收到这个请求之后将根据请求的信息（包括URL、Http方法、请求报文头和请求参数Cookie等）以及HandlerMapping的配置
找到处理请求的处理器（`Handler`）。
3-4、DispatcherServlet根据HandlerMapping找到对应的Handler,将处理权交给Handler（Handler将具体的处理进行封装），再由具体的
HandlerAdapter对Handler进行具体的调用。
5、Handler对数据处理完成以后将返回一个`ModelAndView()`对象给DispatcherServlet。
6、Handler返回的ModelAndView()只是一个逻辑视图并不是一个正式的视图，DispatcherSevlet通过`ViewResolver将逻辑视图转化为真正的视图View`。
7、Dispatcher通过model解析出ModelAndView()中的参数进行解析最终展现出完整的view并返回给客户端。


### Hibernate是如何延迟加载？
当Hibernate在查询数据的时候，`数据并没有存在与内存中`，当程序真正对数据的操作时，对象才存在与内存中，就实现了延迟加载，他节省了服务器的内存开销，从而提高了服务器的性能。

### 说下Hibernate的缓存机制
1. 内部缓存存在Hibernate中又叫一级缓存，属于`应用事物级缓存`
2. 二级缓存：
  a) 应用及缓存
  b) 分布式缓存
  c) 第三方缓存的实现
 
### Struts和springmvc的区别，两者分别是多线程还是单线程的

### Spring Boot解决的问题
(1) Spring Boot使编码变简单
(2) Spring Boot使配置变简单
(3) Spring Boot使部署变简单
(4) Spring Boot使监控变简单


### Spring Boot继承了Spring的优点，并新增了一些新功能和特性：
（1）SpringBoot是伴随着Spring4.0诞生的，一经推出，引起了巨大的反向； 
（2）从字面理解，Boot是引导的意思，因此SpringBoot帮助开发者快速搭建Spring框架； 
（3）SpringBoot帮助开发者快速启动一个Web容器； 
（4）SpringBoot继承了原有Spring框架的优秀基因； 
（5）SpringBoot简化了使用Spring的过程； 
（6）Spring Boot为我们带来了脚本语言开发的效率，但是Spring Boot并没有让我们意外的新技术，都是java ee开发者常见的额技术。

### Spring Boot主要特性
（1）遵循“`习惯优于配置`”的原则，使用Spring Boot只需要很少的配置，大部分的时候我们直接使用默认的配置即可； 
（2）项目快速搭建，可以无需配置的自动整合第三方的框架； 
（3）可以完全不使用XML配置文件，只需要自动配置和Java Config； 
（4）`内嵌Servlet容器`，降低了对环境的要求，可以使用命令直接执行项目，应用可用jar包执行：java -jar； 
（5）提供了starter POM, 能够非常方便的进行包管理, 很大程度上减少了jar hell或者dependency hell； 
（6）运行中应用状态的`监控`； 
（7）对主流开发框架的无配置集成； 
（8）与云计算的天然继承；