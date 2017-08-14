## python


### Python和多线程（multi-threading）。这是个好主意码？列举一些让Python代码以并行方式运行的方法。

答案
Python并不支持真正意义上的多线程。Python中提供了多线程包，但是如果你想通过多线程提高代码的速度，使用多线程包并不是个好主意。
Python中有一个被称为`Global Interpreter Lock（GIL）的东西，它会确保任何时候你的多个线程中，只有一个被执行`。线程的执行速度非常之快，
会让你`误以为线程是并行执行的`，但是实际上都是轮流执行。经过GIL这一道关卡处理，会增加执行的开销。这意味着，如果你想提高代码的运行速度，
使用threading包并不是一个很好的方法。
不过还是有很多理由促使我们使用threading包的。如果你想同时执行一些任务，而且不考虑效率问题，那么使用这个包是完全没问题的，而且也很方便。
但是大部分情况下，并不是这么一回事，你会希望把多线程的部分外包给操作系统完成（通过开启多个进程），或者是某些调用你的Python代码的外部程序
（例如Spark或Hadoop），又或者是你的Python代码调用的其他代码（例如，你可以在Python中调用C函数，用于处理开销较大的多线程工作）。

### “猴子补丁”（monkey patching）指的是什么？这种做法好吗？

答案：
“猴子补丁”就是指，在函数或对象已经定义之后，再去改变它们的行为。
不好，你如果提到要避免“猴子补丁”，可以说明你不是那种喜欢花里胡哨代码的程序员（公司里就有这种人，跟他们共事真是糟糕透了），而是更注重可维护性。

### Python里面如何实现tuple和list的转换？
答：直接使用tuple和list函数就行了，`type()`可以判断对象的类型


### 介绍一下Python下range()函数的用法？
答：列出一组数据，经常用在for  in range()循环中
### 如何用Python来进行查询和替换一个文本字符串？
答：可以使用re模块中的`sub()函数`或者subn()函数来进行查询和替换，
格式：sub(replacement, string[,count=0])（replacement是被替换成的文本，string是需要被替换的文本，count是一个可选参数，指最大被替换的数量）
import re
p=re.compile(‘blue|white|red’)
print(p.sub(‘colour’,'blue socks and red shoes’))
colour socks and colourshoes
print(p.sub(‘colour’,'blue socks and red shoes’,count=1))
colour socks and redshoes
subn()方法执行的效果跟sub()一样，不过它会返回一个二维数组，包括替换后的新的字符串和总共替换的数量

### Python 字符串操作（string替换、删除、截取、复制、连接、比较、查找、包含、大小写转换、分割等）   
#### 去空格及特殊符号  
s.strip() .lstrip() .rstrip(',')   
 
#### 复制字符串  
sStr= 'strcpy'   
sStr = sStr  
sStr= 'strcpy'   
print sStr   
 
#### 连接字符串  
sStr= 'strcat'   
sStr = 'append'   
sStr+= sStr   
print sStr  
 
#### 查找字符  
sStr= 'strchr'   
sStr = 's'   
nPos = sStr1.index(sStr)   
print nPos   
 
#### 比较字符串  
sStr= 'strchr'   
sStr = 'strch'   
print cmp(sStr1,sStr)  
 
#### 字符串长度  
sStr= 'strlen'   
print len(sStr1)   
 
#### 将字符串中的大小写转换  
sStr= 'JCstrlwr'   
sStr= sStr1.upper()   
sStr= sStr1.lower()   
print sStr  
 
#### 追加指定长度的字符串  
sStr= '1345'   
sStr = 'abcdef'   
n = 3 
sStr+= sStr[0:n]   
print sStr  
 
#### 字符串指定长度比较  
sStr= '1345'   
sStr = '13bc'   
n = 3 
print cmp(sStr1[0:n],sStr[0:n])   
 
#### 复制指定长度的字符  
sStr= ''   
sStr = '1345'   
n = 3 
sStr= sStr[0:n]   
print sStr  
 
#### 将字符串前n个字符替换为指定的字符  
sStr= '1345'   
ch = 'r'   
n = 3 
sStr= n * ch + sStr1[3:]   
print sStr  
 
#### 扫描字符串  
sStr= 'cekjgdklab'   
sStr = 'gka'   
nPos = -1 
for c in sStr1:   
     if c in sStr:   
         nPos = sStr1.index(c)   
         break   
print nPos   
 
#### 翻转字符串  
sStr= 'abcdefg'   
sStr= sStr1[::-1]   
print sStr  
 
#### 查找字符串  
sStr= 'abcdefg'   
sStr = 'cde'   
print sStr1.find(sStr)   
 
#### 分割字符串  
sStr= 'ab,cde,fgh,ijk'   
sStr = ','   
sStr= sStr1[sStr1.find(sStr) + 1:]   
print sStr  
 或者   
s = 'ab,cde,fgh,ijk'   
print(s.split(','))   
 
#### 连接字符串  
delimiter = ','   
mylist = ['Brazil', 'Russia', 'India', 'China']   
print delimiter.join(mylist)   
PHP 中 addslashes 的实现  
def addslashes(s):   
     d = {'"':'\\"', "'":"\\'", "\0":"\\\0", "\\":"\\\\"}   
    return ''.join(d.get(c, c) for c in s)   
s = "John 'Johny' Doe (a.k.a. \"Super Joe\")\\\0"   
print s   
print addslashes(s)   
 
只显示字母与数字  
def OnlyCharNum(s,oth=''):   
     s = s.lower();   
    fomart = 'abcdefghijklmnopqrstuvwxyz013456789'   
    for c in s:   
        if not c in fomart:   
             s = s.replace(c,'');   
     return s;   
print(OnlyStr("a000 aa-b"))   