# Redis-MQ

#### 介绍

使用Redis来实现消息队列，个人实验性质的小项目。

#### 计划

1.  基于 Redis List 的 LPUSH、RPOP来实现
    ```
    核心的功能是实现了，ACK机制太过简陋，使用一个队列加定时任务扫描那种机制更靠谱一些。
    这种List的方式，不能做广播、分组、重复消费
    ```
2.  基于 Redis 的发布/订阅来实现 
    ```
    
    ```
3.  基于 Redis 的 Stream 来实现
    ```
    
    ```
4.  基于 Redis 的 Sorted-Set 来实现
    ```
    
    ```

#### 参考
1.  https://www.cnblogs.com/-wenli/p/12777703.html
2.  https://segmentfault.com/a/1190000012244418
3.  https://www.jianshu.com/p/57edefa80167