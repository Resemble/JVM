## 数据库
数据批量插入、数据库连接池、查询 select 不要所有字段、经常查询建索引、
能用 join 不要建临时表、静止自动提交、Explain 分析执行计划、插入数据先删索引，插入后再建索引
### psql 性能调优
#### 查询优化
查询是数据库中最频繁的操作，优化查询语句可以有效提高数据库的性能。
- 使用`explain语句分析语句的执行计划`，查看索引使用情况
explain analyze select * from test_table1 where name='bruce.wu'
查看第一步的分析结果，在查询条件使用的列上创建索引
- 优化子查询
子查询很灵活可以极大的节省查询的步骤，但是子查询的执行效率不高。执行子查询时数据库需要为内部嵌套的语句查询的结果建立一个临时表，
然后再使用临时表中的数据进行查询。查询完成后再删除这个临时表，所以子查询的速度会慢一点。
我们可以`使用join语句来替换掉子查询`，来提高效率。join语句不需要建立临时表，所以其查询速度会优于子查询。大部分的不是很复杂的子查询都可以替换成join语句。
如：
select * from a1 where a1.id in (select b1.a1_id from b1)
select a1.* from a1 inner join b1 on a1.id=b1.a1_id
上面两句是等效的，但是inner join 语句效率更高
- 优化数据库结构
`将字段很多的表分解成多个表`
对于字段很多的表，如果有些字段在查询结果中的不常用到，可以将这些字段分离出来形成新的表。因为一个表的数据量很大时，查询会优于使用频率低的字段存在而变慢。
- 增加中间表
对于经常联合查询的表，可以建立一个中间表，把需要经常联合查询的数据插入到中间表，然后将联合查询改为对中间表的查询，来提高查询速度。
- 增加冗余字段
在设计数据库时应尽量遵循数据库设计范式，减少冗余字段，但是合理的冗余字段可以减少不必要的表关联查询，所以，适当的增加冗余字段也是可以提高查询效率的。
- 优化插入记录的速度
插入记录时，影响速度的主要是`索引、唯一性校验、一次性插入条数`等。根据这些情况分别优化
`插入大量数据时先删除索引，完成后再重新创建索引` `删除外键约束`
使用`批量插入`一条insert into 语句可以插入多条数据
- 禁止自动提交
如果允许每个插入都独立的提交，那么数据库就要为每行记录做大量的处理。所以在插入大量数据前禁止自动事务的自动提交，`完成后再恢复自动提交。`
- 使用copy语句批量导入
`copy语句导入数据的速度要比insert语句快。`在大量装载数据的情况下，导致的负载也少很多。
COPY命令是为装载数量巨大的数据行优化过的，它不像INSERT命令那样灵活，但是在装载大量数据时，系统开销也要少很多。因为COPY是单条命令，因此在填充表的时候就没有必要关闭自动提交了。 
- 增大maintenance_work_mem：
在装载大量数据时，临时增大maintenance_work_mem系统变量的值可以改进性能。这个系统参数可以提高CREATE INDEX命令和ALTER TABLE ADD FOREIGN KEY命令的执行效率，但是它不会对COPY操作本身产生多大的影响。
- 增大checkpoint_segments：
临时增大checkpoint_segments系统变量的值也可以提高大量数据装载的效率。这是因为在向PostgreSQL装载大量数据时，将会导致检查点操作
(由系统变量checkpoint_timeout声明)比平时更加频繁的发生。在每次检查点发生时，所有的脏数据都必须flush到磁盘上。通过提高checkpoint_segments变量的值，可以有效的减少检查点的数目。
- 事后运行ANALYZE：
`在增加或者更新了大量数据之后，应该立即运行ANALYZE命令`，这样可以保证规划器得到基于该表的最新数据统计。换句话说，如果没有统计数据或者统计数据太过陈旧，那么规划器很可能会选择一个较差的查询规划，从而导致查询效率过于低下。

### Mysql 性能优化
参考 [MySQL性能优化的21个最佳实践]
1. 为查询缓存优化你的查询
2. EXPLAIN 你的 SELECT 查询
3. 当只要一行数据时使用 LIMIT 1
4. 为搜索字段建索引 　　
`索引并不一定就是给主键或是唯一的字段`。如果在你的表中，有某个字段你总要会经常用来做搜索，那么，请为其建立索引吧。
5. 在Join表的时候使用`相当类型的列`，并将其索引 　　
如果你的应用程序有很多 JOIN 查询，你应该确认两个表中Join的字段是被建过索引的。这样，MySQL内部会启动为你优化Join的SQL语句的机制。
6. 千万不要 ORDER BY RAND()
7. `避免 SELECT *`
需要什么就取什么
8. 永远为每张表设置一个ID 　　
我们应该为数据库里的每张表都设置一个ID做为其主键，而且最好的是一个INT型的(推荐使用UNSIGNED)，并设置上自动增加的AUTO_INCREMENT标志。
9. 使用 ENUM 而不是 VARCHAR 　　
ENUM 类型是非常快和紧凑的。在实际上，其保存的是 TINYINT，但其外表上显示为字符串。这样一来，用这个字段来做一些选项列表变得相当的完美。 　　如果你有一个字段，比如“性别”，“国家”，“民族”，“状态”或“部门”，你知道这些字段的取值是有限而且固定的，那么，你应该使用 ENUM 而不是 VARCHAR。
10. 从 PROCEDURE ANALYSE() 取得建议 　　
PROCEDURE ANALYSE() 会让 MySQL 帮你去分析你的字段和其实际的数据，并会给你一些有用的建议。只有表中有实际的数据，这些建议才会变得有用，因为要做一些大的决定是需要有数据作为基础的。 　　例如，如果你创建了一个 INT 字段作为你的主键，然而并没有太多的数据，那么，PROCEDURE ANALYSE()会建议你把这个字段的类型改成 MEDIUMINT 。或是你使用了一个 VARCHAR 字段，因为数据不多，你可能会得到一个让你把它改成 ENUM 的建议。这些建议，都是可能因为数据不够多，所以决策做得就不够准。
11. 尽可能的使用 NOT NULL
12. Prepared Statements 　　
Prepared Statements很像存储过程，是一种运行在后台的SQL语句集合，我们可以从使用 prepared statements 获得很多好处，无论是性能问题还是安全问题。 　　Prepared Statements 可以检查一些你绑定好的变量，这样可以保护你的程序不会受到“SQL注入式”攻击。当然，你也可以手动地检查你的这些变量，然而，手动的检查容易出问题，而且很经常会被程序员忘了。当我们使用一些framework或是ORM的时候，这样的问题会好一些。 　　在性能方面，当一个相同的查询被使用多次的时候，这会为你带来可观的性能优势。你可以给这些Prepared Statements定义一些参数，而MySQL只会解析一次。
14. 把IP地址存成 UNSIGNED INT 　　
很多程序员都会创建一个 VARCHAR(15) 字段来存放字符串形式的IP而不是整形的IP。如果你用整形来存放，只需要4个字节，并且你可以有定长的字段。而且，这会为你带来查询上的优势，尤其是当你需要使用这样的WHERE条件：IP between ip1 and ip2。 　　我们必需要使用UNSIGNED INT，因为 IP地址会使用整个32位的无符号整形。
15. 固定长度的表会更快 　　
如果表中的所有字段都是“固定长度”的，整个表会被认为是 “static” 或 “fixed-length”。 例如，表中没有如下类型的字段： VARCHAR，TEXT，BLOB。只要你包括了其中一个这些字段，那么这个表就不是“固定长度静态表”了，这样，MySQL 引擎会用另一种方法来处理。
16. 垂直分割 　　
“垂直分割”是一种把数据库中的表按列变成几张表的方法，这样可以降低表的复杂度和字段的数目，从而达到优化的目的。(以前，在银行做过项目，见过一张表有100多个字段，很恐怖)
17. 拆分大的 DELETE 或 INSERT 语句 　　
如果你需要在一个在线的网站上去执行一个大的 DELETE 或 INSERT 查询，你需要非常小心，要避免你的操作让你的整个网站停止相应。因为这两个
操作是会锁表的，表一锁住了，别的操作都进不来了。 　　Apache 会有很多的子进程或线程。所以，其工作起来相当有效率，而我们的服务器也不希
望有太多的子进程，线程和数据库链接，这是极大的占服务器资源的事情，尤其是内存。 　　如果你把你的表锁上一段时间，比如30秒钟，那么对于
一个有很高访问量的站点来说，这30秒所积累的访问进程/线程，数据库链接，打开的文件数，可能不仅仅会让你泊WEB服务Crash，还可能会让你的
整台服务器马上掛了。 　　所以，如果你有一个大的处理，你定你一定把其拆分，使用 `LIMIT 条件`是一个好的方法。 while 循环删除。
18. 越小的列会越快 　　
对于大多数的数据库引擎来说，硬盘操作可能是最重大的瓶颈。所以，把你的数据变得紧凑会对这种情况非常有帮助，因为这减少了对硬盘的访问。 　　
如果一个表只会有几列罢了(比如说字典表，配置表)，那么，我们就没有理由使用 INT 来做主键，使用 `MEDIUMINT, SMALLINT 或是更小的 TINYINT `
会更经济一些。如果你不需要记录时间，使用` DATE 要比 DATETIME 好得多。`
19. 选择正确的存储引擎 　　
在 MySQL 中有两个存储引擎 MyISAM 和 InnoDB，每个引擎都有利有弊。
- MyISAM 适合于一些需要`大量查询`的应用，但其对于有大量写操作并不是很好。`甚至你只是需要update一个字段，整个表都会被锁起来`，而别的进程，就算是读进程都无法操作直到读操作完成。另外，`MyISAM 对于 SELECT COUNT(*) 这类的计算是超快无比的`。 　　
- InnoDB 的趋势会是一个非常复杂的存储引擎，对于一些小的应用，它会比 MyISAM 还慢。他是它支持`“行锁” `，于是在写操作比较多的时候，会更优秀。并且，他还支持更多的高级应用，比如：`事务`
20. 使用一个对象关系映射器(Object Relational Mapper) 　　
使用 ORM (Object Relational Mapper)，你能够获得可靠的性能增涨。一个ORM可以做的所有事情，也能被手动的编写出来。但是，这需要一个高级专家。 　　
`ORM 的最重要的是“Lazy Loading”，`也就是说，只有在需要的去取值的时候才会去真正的去做。但你也需要小心这种机制的副作用，因为这很有可能会因为要去创建很多很多小的查询反而会降低性能。 　　ORM 还可以把你的SQL语句打包成一个事务，这会比单独执行他们快得多得多。
21. 小心“永久链接” 　　
“永久链接”的目的是用来减少重新创建MySQL链接的次数。当一个链接被创建了，它会永远处在连接的状态，就算是数据库操作已经结束了。
而且，自从我们的Apache开始重用它的子进程后——也就是说，下一次的HTTP请求会重用Apache的子进程，并重用相同的 MySQL 链接。
感觉像连接池。


















[MySQL性能优化的21个最佳实践]:http://www.searchdatabase.com.cn/showcontent_38045.htm