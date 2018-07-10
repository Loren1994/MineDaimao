package com.example.loren.minesample.observe

import java.util.*

/**
 * Copyright © 2018/7/9 by loren
 * 被观察者
 */
class DataObservable : Observable() {

    var data = ""
        set(value) {
            field = value
            setChanged()
            notifyObservers()
        }
}