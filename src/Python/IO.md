### IO
* 用open()打开文件, 标识符有r,rb,w,wb
* file.read()输出文件的全部内容
* encoding指定编码格式，如读取gbk编码的文件f = open('/Users/michael/gbk.txt', 'r', encoding='gbk')
* open()函数还接收一个errors参数，表示如果遇到编码错误后如何处理。最简单的方式是直接忽略,errors='ignore'
* file.read(size) 读取size个字节
* file.readline() 读取一行，会保留换行符
* file.close()关闭 with可以带资源释放
```Python
mm = open('IOtest/kkk/密码.txt', 'r')
print(mm.read(10))
k = mm.readline()
print(len(k))  # 字符串长度里面有换行符
print(mm.readline())
for line in mm.readlines():
    print(line)
mm.close()

old_pic = open('IOtest/斧头.jpg', 'rb')
new_pic = open('IOtest/aa.jpg', 'wb')
new_pic.write(old_pic.read())
old_pic.close()
new_pic.close()
with open('IOtest/斧头.jpg', 'rb') as old_pic:
    with open('IOtest/aa.jpg', 'wb') as new_pic:
        new_pic.write(old_pic.read())
```

### StringIO 
* 类似于java的StringBuffer
* 可以向文件一样读取StringIO
```Python
from io import StringIO
f = StringIO()
f.write('hello')
f.write(' ')
f.write('world!')
print(f.getvalue())
```

### ByteIO
* 类似与StringIO，只不过里面存的是Byte
```Python
from io import BytesIO
f = BytesIO()
f.write('中文'.encode('utf-8'))
print(f.getvalue())
```

### file操作
* os.path.join(A, B) 会用当前系统的分隔符连接AB
* os.path.split(dir) 会把dir分割成两部分，用最后一个系统的路径分隔符分开
* os.path.splitext() 以文件的扩展名分割
* os.mkdir(dir) 创建某个目录
* os.rmdir(dir) 删除某个目录
* os.rename('test.txt', 'test.py') 重命名
* os.remove('test.py') 删除
```Python
import os
print(os.name)
print(os.environ)
print(os.path.abspath('.'))
print(os.mkdir(''))
```
查找dir下，名字里包含key的全部文件
```Python
def search(key, path):
    for son in os.listdir(path):
        son = os.path.join(path, son)
        if os.path.isfile(son):
            file = os.path.split(son)[1]
            if file.find(key) != -1:
                print(os.path.abspath(son))
        else:
            search(key, son)
    return "ok"

search('a', 'D:\\PycharmProjects')
```
