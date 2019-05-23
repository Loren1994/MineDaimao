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
class BinderClient : Service() {

    private lateinit var iMyAidlInterface: IMyAidlInterface

    private val stub = object : IMyAidlInterface.Stub() {
        override fun bindSuccess() {
            log("BinderClient绑定成功")
        }

        override fun unbind() {
            log("BinderClient解绑")
            applicationContext.unbindService(connection)
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            log(">>>onServiceDisconnected")
            bindServerService()
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            log(">>>onServiceConnected")
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service)
            iMyAidlInterface.bindSuccess()
            bindServerService()
        }

    }

    override fun onUnbind(intent: Intent?): Boolean {
        iMyAidlInterface.unbind()
        return super.onUnbind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        log("创建BinderClient")
        bindServerService()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return stub
    }

    private fun bindServerService() {
        val intent = Intent()
        intent.component = ComponentName("com.example.loren.minesample.debug", "binder.BinderServer")
        if (!applicationContext.bindService(intent, connection, Context.BIND_AUTO_CREATE)) {
            log("bindServerService: 绑定 ServerService 失败")
            stopSelf()
        }
    }

}