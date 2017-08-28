```Python
# !/usr/bin/env python3
# -*- coding: utf-8 -*-
```

### 简单的输入输出
* end = "\n" 来控制每个print最后的输出内容，默认是换行
```Python
print('hello, world')
print('The quick brown fox', 'jumps over', 'the lazy dog')
print('100 + 200 =', 100 + 200)
name = input('请输入名字\n')  # 读取一行，括号内为输出的提示语
print('hello,', name)
```

### 数据类型
* Python可以处理任意大小的整数
* 浮点数是双精度
* 字符串是'或"之间的文本，不可变
* 布尔型True False
* None 空
关系运算符 and or not
除法：/浮点除 //整除
```Python
print(r'\\\t\\')  # r''单引号之间的文本不转译
print('''line1
line2''')  # 三引号中的字符串可以换行
```

### 字符串
* ord()函数获取字符的整数表示
* chr()函数把编码转换为对应的字符
* len()函数计算的是str的字符数，或bytes的字节数
* 格式化输出和c中的printf类似，用一个%分割

```Python
print(ord('中'))
print(chr(66))
print(len('中文'))
print(len('中文'.encode('utf-8')))
print('Hello, %s' % 'world')
print('Hi, %s, you have $%d.' % ('Michael', 1000000))
```

### list
* 有序集，用中括号表示
* 用len()函数可以获得list元素的个数
* 支持负下标，表示导数第几个
* append 追加元素
* insert(i, x) 插入元素，把x查到下标为i的位置上，以前的向后移动
* 要删除list末尾的元素，用pop()方法
* 删除list指定位置的元素，用pop(i)方法
* list中可以是任何东西，比如另一个list
```Python
classmates = ['Michael', 'Bob', 'Tracy']
classmates.insert(1, 'Jack')
classmates.append('Adam')
classmates.pop()
print(classmates)
```

### tuple
* 元组，用小括号表示
* 不可变的list
* 不可变指的是引用，类似于java的final array
```Python
t = ('a', 'b', ['A', 'B'])
print(t)
t[2][0] = 'X'
t[2][1] = 'Y'
print(t)
```

### 条件判断
```Python 
if <条件判断1>:
    <执行1>
elif <条件判断2>:
    <执行2>
elif <条件判断3>:
    <执行3>
else:
    <执行4>
```
* 注意冒号
```Python
s = input('birth: ')  # input返回str
birth = int(s)
if birth < 2000:
    print('00前')
else:
    print('00后')
```

### 循环：
* for...in循环，依次把list或tuple中的每个元素迭代出来，类似于java的for(:)
* range(x) 生成[0, x)的range
* list()可以将range转换为list
* while循环，和if一样去掉括号加冒号
* break, continue 和java一样
```Python
names = ['Michael', 'Bob', 'Tracy']
for name in names:
    print(name)
print(range(3, 5))
print(list(range(2, 5)))
sumX = 0
for x in range(101):
    sumX = sumX + x
print(sumX)
sumN = 0
n = 100
while n > 0:
    sumN = sumN + n
    n = n - 1  # 没有n--
print(sumN)
```

### dict
* 字典(HashMap)
* 花括号表示
* 和C++一样可以用[]索引
* 用in判断key是否存在
* 用get(key)取值不会抛异常
* 用pop(key)删除
```Python
d = {'Michael': 95, 'Bob': 75, 'Tracy': 85}
print(d['Michael'])
print('Thomas' in d)
print(d.get('Thomas'))  # 找不到返回None
print(d.get('Thomas', -1))  # 找不到返回指定值
```

### set
* 就是set
* add(key) 添加
* remove(key) 删除
* 可以进行 & | 交集，并集运算
```Python
s1 = {1, 2, 3}
s2 = {2, 3, 4}
print(s1 & s2)
print(s1 | s2)
a = (1, 2, 3)
b = (1, [2, 3])
d[a] = "tuple"
d[b] = "tuple with list"  # 还是不行的
```

### 函数
* def定义
* 返回值任意，没有的时候返回None
* pass占位符，没有会语法错误
* 可以有多个返回值，其实就是返回一个tuple
* 和C++一样支持默认参数,默认参数必须指向不变对象!
* 参数之前加星号变成可变参数
* 在list或tuple前面加一个*号，把list或tuple的元素变成可变参数传进去
* 关键字参数，用=传入dict, 用**定义
* 参数列表中用*, 分割表示命名关键字参数
```Python
def my_abs(x):
    if not isinstance(x, (int, float)):
        raise TypeError('bad operand type')  # 手动抛出异常
    if x >= 0:
        return x
    else:
        return -x

def two_return():
    return 1, 2

x, y = two_return()
print(my_abs(-12))
print(my_abs('asdf'))

def nop():
    pass

age = 10
if age >= 18:
    pass

def power(base, n=2):
    s = 1
    while n > 0:
        n = n - 1
        s = s * base
    return s

print(power(2, 3))
print(power(2, 3))

def calc(*numbers):
    sum_number = 0
    for n in numbers:
        sum_number = sum_number + n * n
    return sum_number

print(calc(1, 2))
nums = [1, 2, 3]
print(calc(*nums))
```
