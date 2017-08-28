### 切片（Slice）
* 在中括号中提供一个[a,b)区间，步长为c，用冒号分割
* a默认为0, b默认为len
* 负下标，为映射成对伊的正下标(idx+k*len)%len,k为整数
* tuple切片的结果仍是tuple
* str切片的结果仍是str
```Python
L = ['Michael', 'Sarah', 'Tracy', 'Bob', 'Jack']
print(L[0:3])
print(L[:3])
print(L[:-3])  # ['Michael', 'Sarah']
print(L[3:])  # ['Bob', 'Jack']
print(L[1:4:2])  # ['Sarah', 'Bob']
```
### 迭代
* dict迭代的是key。如果要迭代value，可以用for value in d.values()
* 如果要同时迭代key和value，可以用for k, v in d.items()
* 字符串也可以直接迭代
* from collections import Iterable
  isinstance(var, Iterable) # var是否可迭代
* Python内置的enumerate函数可以把一个list变成索引-元素对
```Python
print(isinstance('abc', Iterable))  # str是否可迭代
for i, value in enumerate(['A', 'B', 'C']):
    print(i, value)

L = [1, 2, 'a', 'b', 'c']
for i in range(0, len(L), 2):  # [java] for (int i = 0; i < len(L); i += 2)
    print(L[i])
```

### 列表生成式(List Comprehensions)
* 把要生成的元素内容放到前面，后面跟for循环，就可以把list创建出来
```Python
print([x * x for x in range(1, 11)])  # 完全平方数
print([x * x for x in range(1, 11) if x % 2 == 0])  # 偶的完全平方数
print([m + n for m in 'ABC' for n in 'XYZ'])  # 全排列
d = {'x': 'A', 'y': 'B', 'z': 'C'}
print([k + '=' + v for k, v in d.items()])
L = ['Hello', 'World', 18, 'Apple', None]
print([s.lower() for s in L if isinstance(s, str)])
```

### 生成器(generator)
* 一边循环一边计算的机制
* 列表生成式的中括号改成小括号
* 通过next()函数获得generator的下一个返回值
* 生成器也可以用for迭代
* 正在函数中使用yield，每次生成器到yield返回，下一次从yield的下一行开始
```Python
g = (x * x for x in range(10))
print(g)
print(next(g))
for i in g:
    print(i)
def fib(max_n):
    n, a, b = 0, 0, 1
    while n < max_n:
        yield b
        a, b = b, a + b
        n = n + 1
    return 'done'
f = fib(10)
for i in f:
    print(i, end=" ")
def pascal_triangle():
    lis = [1]
    while True:
        yield lis
        lis = [lis[i] + lis[i + 1] for i in range(len(lis) - 1)]
        lis.insert(0, 1)
        lis.append(1)

pas = pascal_triangle()
n = 0
for t in pas:
    print(t)
    n = n + 1
    if n == 10:
        break
```

### 迭代器
* 使用iter()函数可以把可迭代对象变为迭代器
```Python
L = [1, 2, 3, 123, 11, 123]
K = iter(L)
print(next(K))
print(next(K))
print(next(K))
```
