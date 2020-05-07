#MVVM

#MVVM 架构

https://blog.csdn.net/Codyer/article/details/104992393


根据公司现状进行的一些MVVM的变体实现，可能大家会有不同的看法，欢迎交流。

以下贴出16年的MVVM实现方式大概框架图，因为之前是做的PPT形式，现在截图贴出来。

后续更新19年的基于LiveData的MVVM实现。

1、各个模块的理解：

![](https://tva1.sinaimg.cn/large/007S8ZIlgy1gejnc2cm6sj30lj0gnjso.jpg)

2、各个模块的关系：

![](https://tva1.sinaimg.cn/large/007S8ZIlgy1gejnbl6jkvj30n10cat9b.jpg)

3、变体MVVM的理解：

![](https://tva1.sinaimg.cn/large/007S8ZIlgy1gejnck8160j30pd0j7n2x.jpg)

4、开发模式和优势：

![](https://tva1.sinaimg.cn/large/007S8ZIlgy1gejncud1i1j30sn0hjq61.jpg)

## 说明
+ 之前官方并没有 ViewModel 的组件，所以我基于自己的理解和实际项目情况设定了 ViewModel 的定义。
+ 可能看了其他解释 MVVM 的文章的朋友比较难理解这种设定， 但是实际使用时，会发现这样的设定更利于项目的一致性和维护
