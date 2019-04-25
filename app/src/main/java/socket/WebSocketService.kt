package socket

import android.app.Service
import android.content.Intent
import android.os.Build
import com.example.loren.minesample.base.ext.log
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import java.util.*

/**
 * Copyright © 2018/10/9 by loren
 */
class WebSocketService : Service() {
    private var webSocket: WebSocket? = null
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private val url = "ws://106.15.185.23:8080/smartlab/websocket/${Build.SERIAL}"
    private val interval = 6000L

    override fun onBind(intent: Intent?) = null

    override fun onCreate() {
        super.onCreate()
        connect()
    }

    private fun connect() {
        log("connect")
        val request = Request.Builder()
                .url(url)
                .build()
        val client = OkHttpClient()
        client.newWebSocket(request, WSListener())
    }

    private fun initTimer() {
        log("initTimer")
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                log("heart beat send:h")
                this@WebSocketService.webSocket?.send("h")
            }
        }
    }

    private fun startTimer() {
        log("startTimer")
        timer?.schedule(timerTask, interval, interval)
    }

    private fun stopTimer() {
        log("stopTimer")
        timerTask?.cancel()
        timer?.purge()
        timer?.cancel()
    }

    private fun reconnect() {
        log("reconnect")
        GlobalScope.launch {
            delay(interval)
            connect()
        }
    }

    inner class WSListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            this@WebSocketService.webSocket = webSocket
            log("onOpen")
            initTimer()
            startTimer()
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            log("onStringMessage: $text")
            // TODO
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            log("onByteStringMessage: $bytes")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            log("onClosing code = [$code], reason = [$reason]")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            log("onClosed code = [$code], reason = [$reason]")
            this@WebSocketService.webSocket = null
            stopTimer()
            reconnect()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            this@WebSocketService.webSocket = null
            log("onFailure t = [$t], response = [$response]")
            stopTimer()
            reconnect()
        }
    }

}
