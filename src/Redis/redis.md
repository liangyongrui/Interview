# redis

1. redis优势
* 性能高 读110000次/s 写81000次/s
* 支持Strings,Lists,Hashes,Sets,Ordered Sets数据类型操作
* redis所有的操作都是原子性的，同时redis支持怼几个操作全并后的原子性执行
* 支持publish/subscribe,通知,key过期等特性
 
2. redis mac安装&启动
* 安装brew install redis
* 启动redis  brew services start redis  // redis-server /usr/local/etc/redis.conf

3. redis 配置
* 感觉一堆要背的东西(待填坑) 

4. redis redis数据类型
* String是redis的基本类型 存的时候set key value  然后 get key 就可以得到value
* redis hash是一个键名对集合，存的时候HMSET key 对象 value 对象value .... 就是一个经常用于存对象的东西,然后可以用HGETALL KEY 得到所有对象和值 也可以用HGET KEY 对象 得到对象的value,每个hash可以存储2^32-1键值对
* redis list是简单的字符串列表，安装插入顺序排序，可以添加一个原宿到列表的头部或者尾部 lpush key value value..的方法塞入值 用lrange key 0 n 输出0到n下标的list的key,lpop key 删除list头的数据
* redis set是string类型的无序集合 集合是通过hash表实现的，sadd key value 添加元素，成功返回 已经存在返回0，其他不返回,通过sadd key value value....的方法塞入元素，通过smembers key 的方法输出所有元素
* redis zset和set不同的每个元素都会关联一个double类型的分数，redis通过分数来为集合中成员从小到达的排序zset成员是唯一的，但score可以重复 , 通过zadd key score value 的方法添加元枢 zrangebyscore key 0 n 按照分数输出0,n的zset
* 方法太多，需要去专门了解每个集合的方法

4. redis命令
* redis-cli redis客户端 连接本地redis服务
* 输入ping 检测是否启用服务
* 再远程服务上执行命令 redis-cli -h host -p port -a password


5. redis的键
* [key命令](http://www.runoob.com/redis/redis-hashes.html)

6. redis hash命令
* [hash命令](http://www.runoob.com/redis/redis-lists.html)

7. redis set命令 
* [set命令](http://www.runoob.com/redis/redis-sets.html)

8. redis zset命令
* [zset命令](http://www.runoob.com/redis/redis-sorted-sets.html)

9. redis hyperloglog
* HyperLogLog是用来做基数统计的算法，在输入元素的数量或者体积非常非常大时，计算基数所需的空间总是固定的，并且是很小的。每个HyperLogLog键只需要12kb内存，就可以计算2^64个不同元素的基数
* HyperLogLog只会根据输入的元素来计算基础，不会储存输入元素的本身,所以HyperLogLog不能像集合那要，返回输入的各个元素
* 使用 PFADD key_naem value value....的然后使用pfcount key_name就可以算出基数
* pfmerge destkey sourcekey sourcekey....将多个HyperLogLog合并为一个HyperLogLog

10. redis的发布订阅
* redis发布订阅(pub/sub)是一种消息通信模式
* redis客户端可以订阅任意数量的频道 大概是这样一个模式 有新的消息publish命令发给频道1,然后这个信息就会发送到订阅了该频道的所有客户端
* subscribe channel_name 创建频道
* 重新开个redis客户端 在同一个频道发布两次消息，订阅者就能接收到消息,publish channel_name "info" 
* [发布命令](http://www.runoob.com/redis/redis-pub-sub.html)

11. redis事务
* redis事务可以一次执行多个命令,并且带有以下两个重要保证:
    * 事务是一个单独的隔离操作：事务中的所有命令都会序列化、按顺序地执行。事务在执行的过程中，不会被其他客户端发送来的命令请求所打断。
    * 事务是一个原子操作：事务中的命令要么全部被执行，要么全部都不执行。
* 一个事务从开始到执行会经历三个阶段:
    * 开始事务
    * 命令入队
    * 执行事务
* 以multi开始一个事务，然后将多个命令入队到事务中，最后由exec命令触发事务，一并执行事务中所有的命令:
* discard取消事务，放弃执行事务块内的所有命令
* exec执行所由事务块内的命令
* multi标记一个事务块的开始
* unwatch取消watch命令对所有的key的监视
* watch key key...监视一个多个key，如果事务执行之前这个key被其他命令所改动，那么事务将被打断

12. redis脚本
* redis脚本使用lua解释器来执行脚本。redis 2.y版本通过内嵌支持lua环境。执行脚本的常用命令为eval
* eval命令的基本语法如下
    * eval script numkeys key .. arg arg ...
    *[eval命令](http://www.runoob.com/redis/redis-scripting.html)
    
13. redis连接
* auth "password"  客户端通过密码验证连接到redis服务 并检测服务是否运行 auth "password" 然后ping
*[redis连接命令](http://www.runoob.com/redis/redis-connection.html)

14. redis服务器
* info获取redis服务器的统计信息
*[redis服务器命令](http://www.runoob.com/redis/redis-server.html)

15. redis数据备份与恢复
* save命令创建当前数据库的备份 在redis安装目录中创建dump.rdb文件
* 恢复的时间只需要将备份文件移动到redis安装目录并启动服务即可,获取redis目录可以用config命令
* 创建redis备份也可以用 bgsave

16. redis安全
* 通过redis的配置文件设计密码参数，这样连接redis服务器就需要密码验证
* 通过config get key_name查看是否设置密码验证
* config set key_name "password" 设置密码 config get key_name 这样客户端连接redis服务就需要密码验证了
* auth password密码验证

17. redis性能测试
* redis-benchmark -n 10000 同时执行10000个请求来检测性能:
* [性能测试工具可选参数](http://www.runoob.com/redis/redis-benchmarks.html)

18. redis客户端连接
* redis通过监听一个tcp端口或者unix socket的方式来接受来自客户端的连接,当一饿连接建立后,redis内部会进行一些操作
    * 客户端socket会被设置为非阻塞模式,因为redis在网络事件处理上采用的是非阻塞多路服用模型.
    * 然后为这个socket设置tcp_nodelay属性，禁用nagle算法
    * 然后创建一个可读文件事件用于监听这个客户端socket的数据发送
    * 最大连接数在redis2.4中直接硬编码在代码里面的，2.6版本变成可配置的maxclients的默认值是10000，可以在redis.conf中对这个值修改
    *[连接命令](http://www.runoob.com/redis/redis-client-connection.html)
 
19. redis 管道技术
* redis管道技术可以在服务端未响应时，客户端可以继续向服务端发送请求,并最终一次读取所有服务端的响应
* 管道技术大概就是这样 一次性向redis 服务器提交，最终一次性读取所有服务器响应$(echo -en "PING\r\n SET runoobkey redis\r\nGET runoobkey\r\nINCR visitor\r\nINCR visitor\r\nINCR visitor\r\n"; sleep 10) | nc localhost 6379
* 显著提高了redis服务的性能

20. redis分区
*可能需要背 [分区](http://www.runoob.com/redis/redis-partitioning.html)

21. java使用redis
* [大概就这样](http://www.runoob.com/redis/redis-java.html)

22. 使用场景等..后续补充..



