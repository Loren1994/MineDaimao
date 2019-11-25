package socket

import android.view.View
import com.example.loren.minesample.R
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.socket_client_activity.*
import java.io.IOException
import java.io.OutputStream
import java.net.Socket


/**
 * Copyright © 28/12/2017 by loren
 */
class SocketClientActivity : BaseActivity() {

    private var outputStream: OutputStream? = null
    private var socket: Socket? = null
    private var ip: String? = null
    private var data: String = ""
    private var socketStatus = false
    private val PORT = 8886//8000

    private fun connect() {
        ip = input_edt.text.toString()
        Thread {
            if (!socketStatus) {
                try {
                    socket = Socket(ip, PORT)
                    if (socket == null) {
                    } else {
                        socketStatus = true
                    }
                    outputStream = socket!!.getOutputStream()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.start()
    }


    private fun send() {
        data = msg_edt.text.toString()
        Thread {
            if (socketStatus) {
                try {
                    outputStream!!.write(data.toByteArray())
                    val input=data.toByteArray().inputStream()
                    log(String(input.readBytes()))
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

    override fun onBackPressed() {
        outputStream?.close()
        socket?.close()
        super.onBackPressed()
    }

    override fun initWidgets() {
        connect_btn.setOnClickListener { connect() }
        send_btn.setOnClickListener { send() }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.socket_client_activity
}