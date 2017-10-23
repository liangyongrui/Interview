#redis

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
* set key_name redis  设置key 删除key del key_name
* dump key 序列化给定的key,并返回被序列化的值
* exists key 检查给定的key是否存在
* expire key seconds 为给定的key设置过期时间
* expireat key timestamp 都是为key设置过期时间 接受的时间参数unix 时间戳
* pexpire key milliseconds设置key的过期时间以毫秒计
* pexpireat key milliseconds-timestamp 设置key过期时间戳毫秒计
* keys pattern 查找所有符合给定模式的key
* move key db移除key的过期时间,key将持久保持
* persist key 移除key的过期时间,key将持久保持
* pttl key 以毫秒为单位返回key的剩余过期时间
* ttl key 以秒为单位，返回给定key的剩余生存时间
* randomkey 从当前数据库汇中随即返回一个key
* rename key newkey 修改key的名称
* renamex key new key 仅当newkey不存在时，将key改为 newkey
* type key 返回key所储存value的type

6. redis hash命令
* [hash命令](http://www.runoob.com/redis/redis-lists.html)

7. redis set命令 
* [set命令](http://www.runoob.com/redis/redis-sets.html)

8. redis zset命令
* [zset命令](http://www.runoob.com/redis/redis-sorted-sets.html)
