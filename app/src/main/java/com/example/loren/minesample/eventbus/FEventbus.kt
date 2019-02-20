package com.example.loren.minesample.eventbus

import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * Copyright © 2019/2/18 by loren
 */
object FEventbus {

    var allEventMap = ConcurrentHashMap<KClass<*>, LinkedList<out OnEventListener<*>>>()

    fun <T : Any> get(kClass: KClass<T>) = allEventMap[kClass] as LinkedList<OnEventListener<T>>?

    fun <T : Any> put(key: KClass<T>, value: LinkedList<OnEventListener<T>>) {
        allEventMap[key] = value
    }

    //T:具体事件 sub:实现接口的类
    inline fun <reified T : Any> register(sub: OnEventListener<T>) {
        var list = get(T::class)
        if (list == null) {
            list = LinkedList()
            put(T::class, list)
        }
        list.add(sub)
    }

    inline fun <reified T : Any> unRegister(sub: OnEventListener<T>) {
        get(T::class)?.remove(sub)
    }

    inline fun <reified T : Any> post(event: T) {
        get(T::class)?.forEach {
            it.onEvent(event)
        }
    }

}