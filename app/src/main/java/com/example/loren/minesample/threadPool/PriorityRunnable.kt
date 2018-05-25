package com.example.loren.minesample.threadPool

/**
 * Copyright © 2018/5/24 by loren
 */
class PriorityRunnable(var priority: Priority, var runnable: Runnable) : Runnable {

    var SEQ = 0L //任务唯一标示

    override fun run() {
        runnable.run()
    }
}