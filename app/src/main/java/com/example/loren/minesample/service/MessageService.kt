package com.example.loren.minesample.service

import aidl.IMessage
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.loren.minesample.base.ext.log

/**
 * Copyright © 2018/11/8 by loren
 */
class MessageService : Service() {

    private var msg: IMessage.Stub? = null
    private var message = ""

    override fun onCreate() {
        super.onCreate()
        msg = object : IMessage.Stub() {
            override fun sendMessage(msg: String) {
                log("服务端收到消息:$msg")
            }

            override fun recMessage(): String {
                message = "this is server message !!!"
                log("服务端返回消息:$message")
                return message
            }

        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        log("${javaClass.simpleName} - ${String.format("on bind,intent = %s", intent.toString())}")
        return msg
    }

    //客户端(将服务端的aidl文件放入客户端中并编译,报名不变)
//    private var msg: IMessage? = null
//
//    private var mServiceConnection = object : ServiceConnection {
//        override fun onServiceDisconnected(name: ComponentName?) {
//            log("客户端断开连接")
//        }
//
//        override fun onServiceConnected(name: ComponentName?, service: IBinder) {
//            msg = IMessage.Stub.asInterface(service)
//            msg!!.sendMessage("客户端修改了消息")
//            val temp = msg!!.recMessage()
//            log(">>>>客户端:$temp")
//        }
//
//    }
//
//    private fun attemptToBindService() {
//        val intent = Intent()
//        intent.action = "pers.loren.aidl"
//        intent.setPackage("com.example.loren.minesample.debug")
//        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
//    }
}