---
guarded suspension模式使用场景
---
以im软件为例，用户登录成功之后拉取各种信息。在未登录成功的时候不去拉取，而且拉取数据之间也需要通过gs来保证时序。

Java的阻塞队列也使用gs的思想。

---
threadpool模式
---
通过Java concurrent包提供的ThreadPoolExecutor来自定义线程池。

实现目标： 
> * 线程池的大小可以动态配置
> * 线程池的日志是全面的
> * 线程池中线程的运行状态是随时可知的
> * 任务执行前后打印日志
> * 线程池的关闭时灵活的
> * 实现任务的优先级
> * 线程池&队列饱和时的策略
> * 线程池支持多种任务类型的执行
> * 如果可以 希望实现空闲线程的回收。 但是需要权衡线程创建的代价和空闲线程对资源的消耗。

Looper线程相关问题：有些代码的线程池是通过本地开启一些Looper线程，通过Looper线程来post任务，但Looper线程的维护会存在问题。
> * 有两种方案
 - 使用Java默认的线程池来解决 即使用ThreadFactory创建Looper线程。但是这需要一个新的线程池。
 - 如果想使用通用的线程池，可以对任务进行封装。然后在任务执行时创建一个HandlerThread线程，任务都由这个线程来执行。但有个问题，这个HandlerThread需要自己来销毁。

考虑问题：
> * 线程池死锁：即线程池中的线程都在等待某个队列中的任务被执行完成。
> * 系统资源不足： 可以设计线程池的动态调整方案。
> * 线程泄露：线程在执行任务时发生异常，或者线程一直阻塞(可以考虑超时)。
> * 任务过载：当任务过多的时候设计策略。
