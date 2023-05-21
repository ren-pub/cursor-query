# 游标查询测试

## 背景

基础大数据集导出时出现OOM的性能改造

### 导入与导出

- 现阶段导入 并没有发现问题 暂不处理

## 环境

本项目的运行环境模拟生产环境
本项目的JDK 以及项目引入的`pom`版本依赖 主要的依赖于mom保存一致

## 方案

### 普通查询 多次查询一次导出

#### 优点

#### 缺点

### 普通查询 多次查询 分批导出

#### 优点

#### 缺点

### 游标查询

#### 优点

#### 缺点

### 流式查询

#### 优点

#### 缺点

## 测试
 ！为了测试的准确性最好指定JVM参数
```
比如
-Xms512m -Xmx1028m -XX:MaxNewSize=512m  -XX:+PrintGCTimeStamps   -XX:+PrintGCDetails  -verbose:gc  -Xloggc:gc.log  
```

## 性能对比

### 生产架构设计

## JMH benchmarking

## 参考文献

- 我再b站看了一些视频 将这块的比较少
- https://blog.csdn.net/weixin_43944305/article/details/128897778 这个写的还行
- https://blog.csdn.net/m0_61811711/article/details/125604711

## 结论

还是自己写一下实践比较好 可能未来在生产会碰到一些mysql以及spring boot客户端使用上的问题 但是总比现在这种情况好多了

```azure

```