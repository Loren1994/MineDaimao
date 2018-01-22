package com.example.loren.minesample

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.view.View
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.server.VideoHandler
import com.yanzhenjie.andserver.AndServer
import com.yanzhenjie.andserver.Server
import com.yanzhenjie.andserver.filter.HttpCacheFilter
import com.yanzhenjie.andserver.website.AssetsWebsite
import kotlinx.android.synthetic.main.html_file_activity.*
import java.net.InetAddress


/**
 * Copyright © 19/01/2018 by loren
 */
class HtmlFileActivity : BaseActivity() {
    private val HOST_PORT = 9485
    private var mServer: Server? = null

    override fun initWidgets() {
        initServer()
    }

    @SuppressLint("SetTextI18n")
    private fun initServer() {
        ip_tv.text = "电脑访问>>> http://${getIP()}:$HOST_PORT"
        val byteArr = ByteArray(4)
        getIP().split(".").forEachIndexed { index, item ->
            byteArr[index] = item.toInt().toByte()
        }
        if (mServer == null) {
            mServer = AndServer.serverBuilder()
                    .inetAddress(InetAddress.getByAddress(byteArr))
                    .port(HOST_PORT)
                    .website(AssetsWebsite(assets, "web"))
                    .listener(mListener)
                    .filter(HttpCacheFilter())
                    .registerHandler("/video", VideoHandler())
                    .build()
            mServer!!.startup()
        }
    }

    private val mListener = object : Server.ServerListener {
        override fun onStarted() {
            mServer?.let {
                log("手机Server地址: http://${it.inetAddress.hostAddress}:$HOST_PORT")
            }
        }

        override fun onStopped() {
            log("onStopped")

        }

        override fun onError(e: Exception) {
            log("onError>> ${e.printStackTrace()}")

        }
    }

    override fun onDestroy() {
        mServer?.let {
            if (it.isRunning)
                it.shutdown()
        }
        super.onDestroy()
    }

    private fun getIP(): String {
        val wn = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val i = wn.connectionInfo.ipAddress
        return (i and 0xFF).toString() + "." +
                (i shr 8 and 0xFF) + "." +
                (i shr 16 and 0xFF) + "." +
                (i shr 24 and 0xFF)
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }


    override fun bindLayout() = R.layout.html_file_activity
}