package socket

import android.annotation.SuppressLint
import android.view.View
import com.example.loren.minesample.R
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.socket_server_activity.*
import java.io.IOException
import java.io.InputStream
import java.net.BindException
import java.net.ServerSocket
import java.net.Socket


/**
 * Copyright © 28/12/2017 by loren
 */
class SocketServerActivity : BaseActivity() {
    private var serverSocket: ServerSocket? = null
    private var inputStream: InputStream? = null
    private val PORT = 8000

    private fun receiveData() {
        Thread {
            while (true) {
                try {
                    if (!serverSocket!!.isClosed) {
                        val socket = serverSocket!!.accept()
                        inputStream = socket?.getInputStream()
                        readData(socket, inputStream!!)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    private fun readData(socket: Socket, inputStream: InputStream) {
        val stringBuffer = StringBuffer()
        var len: Int
        val bytes = ByteArray(20)
        fun read(): Int {
            len = inputStream.read(bytes)
            stringBuffer.append(String(bytes, Charsets.UTF_8))
            return len
        }
        try {
            while (read() > 0) {
                runOnUiThread {
                    receive_tv.text = (String(stringBuffer))
                }
            }
        } catch (e: IOException) {
            //当这个异常发生时，说明客户端那边的连接已经断开
            e.printStackTrace()
            inputStream.close()
            socket.close()
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initWidgets() {
        try {
            serverSocket = ServerSocket(PORT)
            GetIpAddress.getLocalIpAddress(serverSocket)
            ip_tv.text = "IP:${GetIpAddress.getIP()} PORT: ${GetIpAddress.getPort()}"
            receiveData()
        } catch (e: BindException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        try {
            inputStream?.close()
            serverSocket?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onBackPressed()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.socket_server_activity
}