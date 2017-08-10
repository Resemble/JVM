## 设计模式
所谓设计模式，就是一套被反复使用的代码设计经验的总结（情境中一个问题经过证实的一个解决方案）。使用设计模式是为了可重用代码、让代码
更容易被他人理解、保证代码可靠性。设计模式使人们可以更加简单方便的复用成功的设计和体系结构。将已证实的技术表述成设计模式也会使新系统
开发者更加容易理解其设计思路。

### 开放-封闭原则
修改原有的代码就说明设计不够好。
### 依赖倒转原则
我们应该让程序都`依赖抽象`，而不是相互依赖。


### 工厂模式
工厂类可以根据条件生成不同的子类实例，这些子类有一个`公共的抽象父类并且实现了相同的方法`，但是这些方法针对不同的数据进行了
不同的操作（多态方法）。当得到子类的实例后，开发人员可以调用基类中的方法而不必考虑到底返回的是哪一个子类的实例。
#### 简单工厂模式
- 例如一个简单工厂类有一个运算类的方法，if 调用运算类的子类（加法类、减法类、乘法类、除法类）
- 简单工厂模式的最大优点在于工厂类中包含了必要的逻辑判断，根据客户端的选择条件动态实例化相关的类，对于客户端来说，去除了与具体产品的依赖。
#### 工厂方法
- 例如运算类有子类，而抽象工厂也有子类（加法工厂、减法工厂、乘法工厂、除法工厂）
- 工厂方法克服了简单工厂违背开放-封闭原则的缺点，又保持了封装对象创建过程的优点。但是缺点时由于没加一个产品，就要加一个产品工厂的类，
增加了额外的开发量。

### 策略模式
- 策略模式是一种定义一系列算法的方法，从概念上来看，`所有这些算法完成的都是相同的工作`，只是实现不同，它可以以相同的方式调用所有的算法，
`减少各种算法类与使用算法类之间的耦合`。
- 策略模式的 Strategy 类层次为 `Context 定义了一系列对的可供重用的算法或行为`。继承有助于析取出这些算法中的公共功能。
- 策略模式封装了算法。只要在分析过程中听到需要在不同的时间应用不同的业务规则，就可以考虑用策略模式处理这种变化的可能性。
- 基本的策略模式中，选择所用具体实现的职责由客户端对象承担，并转给策略模式的 Context 对象。
#### 策略模式和简单工厂模式结合
- 简单工厂模式，客户端要接触两个类，例如 CashSuper 和 CashFactory
- 策略模式和简单工厂模式结合，客户端接触一个类例如 CashContext 就可以了。耦合度更加降低。

### 装饰器模式（Decorator）
- 动态地给一个对象添加一些额外的职责，就增加功能来说，装饰器模式比生成子类更为灵活。
- 当系统需要新的功能的时候，向旧的类中添加新的代码。这些新加的代码通常修饰了原有类的核心职责或主要行为。直接加入类中，类的复杂度会增加
主类的复杂度，装饰器提供一个解决方案，它把每个要修饰的功能放在单独的类中，并让这个类包装它所要修饰的对象。
- 把类中的装饰功能从类中搬移去除，简化类。
- 有效地把核心职责和装饰功能区分开，`去除相关类的核心职责和装饰功能区分开`，而且可以`去除相关类中的重复的修饰逻辑`。
- 例如一个人穿衣服，服装继承人，具体裤子衣服继承服装，服装里面有一个人

### 代理模式：为他人作嫁衣
- 需要代理对象和代理对象都是继承同一个类，代理对象中新建了一个需要代理对象。
- 给一个对象提供一个`代理对象`，并由`代理对象控制原对象的引用`。实际开发中，按照使用目的的不同，代理可以分为：远程代理、
虚拟代理、保护代理、Cache 代理、防火墙代理、同步化代理、智能引用代理。
- 远程代理：为一个对象在不同的地址空间提供局部代表。这样可以隐藏一个对象存在不同地址空间的事实。
- 虚拟代理：是根据需要创建开销很大的对象。通过它来存放实例化需要很长时间的真实对象。比如打开一个很大的 HTML 网页，但是可以很快打开，
此时看到的是所有文字，但是图片却是一张一张地下载后才能看到。那些未打开的图片框，就是通过虚拟代理来替代了真实的图片，此时代理存储了真实
图片的路径和尺寸。
- 安全代理：用来控制真实对象访问时的权限。一般用于对象应该有不同的访问权限的时候。
- 智能指引：当调用真实对象时，`代理处理另外一些事`。例如计算真实对象的引用次数，这样当该对象没有引用时，可以自动释放它。或访问一个实际
对象前，检查是否已经锁定它，以确保其他对象不能改变它。
- 例如找个人帮你送花给喜欢的女生。

### 原型模式：简历复制
- 用原型实例指定创建对象的种类，并且通过`拷贝这些原型创建新的对象`。
- 原型模式其实就是从一个对象再创建另外一个可定制对象，而且不需要知道任何创建细节。
- 拷贝时值用浅克隆，对象用深克隆。
- 例如把简历里面的工作经验和年龄姓名基本信息当作一个原型，然后拷贝原型根据每个公式作适应性修改。

### 模板方法
- 定义一个操作中的算法的骨架，而将一些步骤延迟到子类中。模板方法使得子类可以不改变一个算法的结构即可重定义该算法的某些特定步骤。
- 模板方法模式是通过把不变行为搬移到超类，去除子类中的重复代码来体现它的优势。
- 例如实习是报表服务定义一个 Base 的报表类，然后各种子类报表实现类别和数据的操作。或者 考试试卷将题目和答案放在超类，子类实现不同的答案。
- 提供一个抽象类，将部分逻辑以具体方法或构造器的形式实现，然后声明一些抽象方法来迫使子类实现剩余的逻辑。不同的子类可以以不同的方式
实现这些抽象方法（多态实现），从而实现不同的业务逻辑。

### 观察者模式:老板回来，我不知道
- 观察者模式又叫做发布-订阅(Publish/Subscribe)模式，定义了一种一对多的依赖关系，让`多个观察者对象同时监听某一个主题对象`。这个主题对象
在状态发生变化时，会通知所有观察者对象，使它们能够自动更新自己。
- 例如一个观察者前台观察老板是否回来，老板继承主题，前台设置状态为老板回来，然后 NotifyAll ，看股票和同事和看 NBA 的同事就得到了通知。
在具体主题的内部状态改变时，给所有登记过的观察者发出通知。
- 将一个系统分割成一系列相互协作的类有一个很不好的副作用，那就是需要维护相关对象间的一致性。我们不希望为了维持一致性而使各类紧密耦合，
这样会给维护、扩展和重用都带来不便。而观察者模式的关键对象是主题 Subject 和观察者 Observer，一个 Subject 可以有任意数目的依赖它的 
Observer，一旦 Subject 的状态发生了改变，所有的 Observer 都可以得到通知。

### 委托 
委托就是一种引用方法的类型。一旦为委托分配了方法，委托将与该方法具有完全相同的行为。委托方法的使用可以像其他方法一样，具有参数和返回值。
委托可以看作是对函数的抽象，是函数的‘类’，委托的实例将代表一个具体的函数。

### 适配器模式：
把一个类的接口变换成客户端所期待的另一种接口，从而使原本因接口不匹配而无法在一起使用的类能够一起工作。