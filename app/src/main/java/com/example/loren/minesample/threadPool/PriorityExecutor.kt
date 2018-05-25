package com.example.loren.minesample.threadPool

import java.util.*
import java.util.concurrent.PriorityBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

/**
 * Copyright © 2018/5/24 by loren
 * 前CORE_POOL_SIZE个不走Comparator,所以执行顺序不根据优先级
 */
class PriorityExecutor(corePoolSize: Int,
                       maximumPoolSize: Int,
                       keepAliveTime: Long,
                       unit: TimeUnit,
                       workQueue: PriorityBlockingQueue<Runnable>,
                       threadFactory: ThreadFactory) : ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory) {

    companion object {
        private val CORE_POOL_SIZE = 3//核心线程池大小
        private val MAXIMUM_POOL_SIZE = 256//最大线程池队列大小
        private val KEEP_ALIVE = 1L//保持存活时间，当线程数大于corePoolSize的空闲线程能保持的最大时间。
        //提供原子操作的Integer类，通过线程安全的方式操作加减
        private val SEQ_SEED = AtomicLong(0)//主要获取添加任务
        private val sThreadFactory = object : ThreadFactory {
            private val mCount = AtomicInteger(1)

            override fun newThread(runnable: Runnable): Thread {
                return Thread(runnable, "newThread - " + mCount.getAndIncrement())
            }
        }
    }

    constructor() : this(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
            PriorityBlockingQueue(CORE_POOL_SIZE, Comparator { o1, o2 ->
                if (o1 is PriorityRunnable && o2 is PriorityRunnable) {
                    val result = o1.priority.ordinal - o2.priority.ordinal
                    if (result == 0) (o1.SEQ - o2.SEQ).toInt() else result
                } else {
                    0
                }
            }), sThreadFactory)

    override fun execute(command: Runnable) {
        if (command is PriorityRunnable) {
            command.SEQ = SEQ_SEED.getAndIncrement()
        }
        super.execute(command)
    }

}