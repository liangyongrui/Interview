# 面试知识点总结
* 笔记向，不定期更新。
* 没有太多需要理解的东西，需要理解的部分自行学习。
* 自己总结，如果有错的地方欢迎指正。

# 数据库
1. 索引相关
    1. 引入  
    在数据库中对大量数据进行筛选的话，  
    考虑在 where 及 order by 涉及的列上建立索引。
    2. 什么是索引  
    索引是对数据库表中一列或多列的值进行排序的一种结构，使用索引可快速访问数据库表中的特定信息。
    3. 什么时候使用索引  
    总的来说就是能够提升效率的时候使用索引。  
        1. 在作为主键的列上，唯一性约束的列（不过大部分数据库会自动加索引）；
        2. 在经常用在连接的列上，这些列主要是一些外键，可以加快连接的速度；
        3. 在经常需要排序的列上创建索引，这样查询可以利用索引的排序，加快排序查询时间；
        4. 在经常使用在WHERE子句中的列上面创建索引，加快条件的判断速度。
    4. 什么时候不能使用索引  
    总的来说就是不能够提升效率的时候不使用索引，例如：该列很少进行查找或排序操作
    5. 什么时候索引会失效
        1. 查询的结果太大，性能消耗大于使用索引
        2. where里面存在!=
        3. 模糊查询的时候，通配符在第一个
        4. where查询null值
        
2. in 和 exists
    1. 结论  
    两个大小有差距的表嵌套查询，则子查询表大的用exists，子查询表小的用in
    2. 原因  
    【待补充】
3. 数据库连接方法
    1. DriverManager
    ```java
    Connection conn = DriverManager.getConnection("连接URL","用户名","密码");
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setInt(index, value);
    ps.executeUpdate()
    //或者
    Statement stmt=conn.createStatement();
    stmt.executeUpdate(sql)...
    ```
    2. 用JDBC连接池，如C3P0

# 项目
1. 如何保证代码质量
    1. 在写之前想好各种边界条件，想清楚细节，设计好程序的总体思路，方便后期维护
    2. 写的时候满足一定的代码规范、命名规范等，在适当的地方进行输出，方便测试的时候不仅仅是看结果也看一些主要过程和自己的预期是否一致。
    3. 写完后做好完善的单元测试
2. 如何找bug
    1. 先输出调试
    2. 再对指定地方进行单步调试
 7. 单点登录  
    【待补充】

# 框架
【具体待展开】
1. Spring
2. Spring MVC
3. Mybatis
4. Hibernate

# 计算机网络
1. http
    1. http和https区别  
    HTTPS协议是由SSL+HTTP协议构建的可进行加密传输、身份认证的网络协议，要比http协议安全。  
    HTTPS和HTTP的区别主要如下：
        1. https协议需要到ca申请证书，一般免费证书较少，因而需要一定费用。
        2. http是超文本传输协议，信息是明文传输，https则是具有安全性的ssl加密传输协议。
        3. http和https使用的是完全不同的连接方式，用的端口也不一样，前者是80，后者是443。
        4. http的连接很简单，是无状态的；HTTPS协议是由SSL+HTTP协议构建的可进行加密传输、身份认证的网络协议，比http协议安全。

    1. 说一下什么是Http协议？  
    对器客户端和服务器端之间数据传输的格式规范，格式简称为“超文本传输协议”。

    2. 什么是Http协议无状态协议？怎么解决Http协议无状态协议？（曾经去某创业公司问到）
        1. 无状态协议对于事务处理没有记忆能力。缺少状态意味着如果后续处理需要前面的信息
        2. 无状态协议解决办法： 通过1、Cookie 2、通过Session会话保存。

    3. 说一下Http协议中302状态  
    http协议中，返回状态码302表示重定向。这种情况下，服务器返回的头部信息中会包含一个 Location 字段，内容是重定向到的url

    4. Http协议有什么组成？
        1. 请求报文 包含三部分：
            1. 请求行：包含请求方法、URI、HTTP版本信息
            1. 请求首部字段
            1. 请求内容实体
        2. 响应报文 包含三部分：
            1. 状态行：包含HTTP版本、状态码、状态码的原因短语
            2. 响应首部字段
            3. 响应内容实体

    5. Http协议中有那些请求方式？
        1. GET： 用于请求访问已经被URI（统一资源标识符）识别的资源，可以通过URL传参给服务器
        2. POST：用于传输信息给服务器，主要功能与GET方法类似，但一般推荐使用POST方式。
        3. PUT： 传输文件，报文主体中包含文件内容，保存到对应URI位置。
        4. HEAD： 获得报文首部，与GET方法类似，只是不返回报文主体，一般用于验证URI是否有效。
        5. DELETE：删除文件，与PUT方法相反，删除对应URI位置的文件。
        6. OPTIONS：查询相应URI支持的HTTP方法。

    6. Http协议中Http1.0与1.1区别？
        1. 在http1.0中，当建立连接后，客户端发送一个请求，服务器端返回一个信息后就关闭连接，当浏览器下次请求的时候又要建立连接，显然这种不断建立连接的方式，会造成很多问题。
        2. 在http1.1中，引入了持续连接的概念，通过这种连接，浏览器可以建立一个连接之后，发送请求并得到返回信息，然后继续发送请求再次等到返回信息，也就是说客户端可以连续发送多个请求，而不用等待每一个响应的到来。

    7. Http协议实现原理机制？  
    【待补充】

    8. get与post请求区别？
        1. get重点在从服务器上获取资源，post重点在向服务器发送数据；
        2. get传输数据是通过URL请求，post传输数据通过Http的post机制，将字段与对应值封存在请求实体中发送给服务器，这个过程对用户是不可见的；
        3. Get传输的数据量小，因为受URL长度限制，但效率较高；Post可以传输大量数据，所以上传文件时只能用Post方式；
        4. get是不安全的，因为URL是可见的，可能会泄露信息；post较get安全性较高；

    10. 常见Http协议状态？
        * 1**	信息，服务器收到请求，需要请求者继续执行操作
        * 200 - 请求成功
        * 301 - 资源（网页等）被永久转移到其它URL
        * 404 - 请求的资源（网页等）不存在
        * 500 - 内部服务器错误

    13. Http优化
        * 利用负载均衡优化和加速HTTP应用
        * 利用HTTP Cache来优化网站

2. osi七层  
    ![osi](https://raw.githubusercontent.com/llysrv/Interview/master/img/OSI.gif)
    1. 物理层
    1. 数据链路层
    1. 网络层
    1. 传输层
    1. 会话层
    1. 表示层
    1. 应用层

3. TCP  
三次握手  
![三次握手](https://raw.githubusercontent.com/llysrv/Interview/master/img/%E4%B8%89%E6%AC%A1%E6%8F%A1%E6%89%8B.png)  
四次断开  
![四次断开](https://raw.githubusercontent.com/llysrv/Interview/master/img/%E5%9B%9B%E6%AC%A1%E6%8C%A5%E6%89%8B.jpg)

4. udp  
    【待补充】

# Java

### JVM
1. Java GC如何判断对象是否为垃圾  
根搜索算法，根：
    1. 虚拟机栈中引用的对象（本地变量表）
    2. 方法区中静态属性引用的对象
    3. 方法区中常量引用的对象
    4. 本地方法栈中引用的对象（Native对象）

16. 内存模型 为什么要设置工作内存和主内存  
    【待补充】
17. GC的过程 当发现虚拟机频繁GC时应该怎么办？  
    【待补充】  
JVM调优
### 并发
1. 乐观锁，悲观锁
    1. 乐观锁  
    乐观锁假设认为数据一般情况下不会造成冲突，所以在数据进行提交更新的时候，才会正式对数据的冲突与否进行检测，如果发现冲突了，则让返回用户错误的信息，让用户决定如何去做。
    2. 悲观锁  
    指的是对数据被外界（包括本系统当前的其他事务，以及来自外部系统的事务处理）修改持保守态度，因此，在整个数据处理过程中，将数据处于锁定状态。悲观锁的实现，往往依靠数据库提供的锁机制

15. volatile关键字的作用  
    保证变量的可见性

### 其他
1. 优化反射性能
    1. 利用缓存
    2. 对Field、Method、Constructor进行setAccessible(true);

11. 什么时候用接口、什么时候用抽象类 
    1. 表达is-a关系的时候用抽象类
    2. 表达like-a关系的时候用接口
    
12. List的实现类
    1. LinkedList 
    2. ArrayList 默认自动扩容1.5倍
    
13. Map的实现类
    1. HashMap  
    先hash，hash冲突的用链表连起来（jdk1.8 当链表长度超过8时，链表变成红黑树）
    2. TreeMap  
    红黑树
    3. LinkedHashMap  
    默认按照插入排序，  
    accessOrder为true时，记录访问顺序，最近访问的放在最后
    4. hashTable 与 concurrentHashMap 区别。怎么实现线程安全  
    【待解决】

1. 异常处理机制  
    【待补充】

1. Servlet的生命周期，是否是线程安全的？  
    【待补充】

1. 谈谈对事务的理解。  
    【待补充】
1. Spring中的事务管理有哪两种？  
    【待补充】

# 操作系统
    【待补充】
# 设计模式
    【待补充】


