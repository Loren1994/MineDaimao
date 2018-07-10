package com.example.loren.minesample.observe

import com.example.loren.minesample.base.ext.log
import java.util.*

/**
 * Copyright © 2018/7/9 by loren
 * 订阅者
 */
class DataObserve(val onChangeListener: (data: String) -> Unit) : Observer {
    override fun update(p0: Observable?, p1: Any?) {
        val data = (p0 as DataObservable).data
        onChangeListener.invoke(data)
        log("订阅者: $data")
    }
}