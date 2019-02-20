package com.example.loren.minesample.eventbus

/**
 * Copyright © 2019/2/18 by loren
 */
interface OnEventListener<in T> {
    fun onEvent(event: T)
}