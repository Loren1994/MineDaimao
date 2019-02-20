package com.example.loren.minesample.eventbus

/**
 * Copyright © 2019/2/20 by loren
 */
interface EventMsg {
    class LorenEventMsg(val type: String, val msg: String)
    class LorenEventMsg1(val type: String, val msg: String)
}