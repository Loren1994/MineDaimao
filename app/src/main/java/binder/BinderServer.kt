package binder

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.example.loren.minesample.base.ext.log


/**
 * Copyright © 2019-05-22 by loren
 * 互相绑定 - 互相守护
 */
class BinderServer : Service() {

    private val stub = object : IMyAidlInterface.Stub() {
        override fun bindSuccess() {
            log("BinderServer绑定成功")
        }

        override fun unbind() {
            log("BinderServer解绑")
            applicationContext.unbindService(connection)
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            log(">>>onServiceDisconnected")
            bindRemoteService()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            log(">>>onServiceConnected")
            bindRemoteService()
        }

    }

    override fun onCreate() {
        super.onCreate()
        bindRemoteService()
        Thread {
            while (true) {
                log("server service execute...")
                Thread.sleep(500)
            }
        }.start()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return stub
    }

    private fun bindRemoteService() {
        val intent = Intent()
        intent.component = ComponentName("com.example.loren.minesample.client.debug", "binder.BinderClient")
        if (!applicationContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
            log("bindRemoteService: 绑定 RemoteService 失败")
            stopSelf()
        }
    }

}