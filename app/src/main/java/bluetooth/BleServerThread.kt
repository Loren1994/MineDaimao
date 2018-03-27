package bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import java.io.IOException
import java.util.*


/**
 * Copyright © 2018/3/27 by loren
 */
class BleServerThread(bleName: String) : Thread() {
    private var bleAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var mmServerSocket: BluetoothServerSocket? = null
    private val mUUID: UUID
        get() = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    init {
        var tmp: BluetoothServerSocket? = null
        try {
            tmp = bleAdapter.listenUsingRfcommWithServiceRecord(bleName, mUUID)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        mmServerSocket = tmp
    }

    override fun run() {
        var socket: BluetoothSocket?
        // 在后台一直监听客户端的请求
        while (true) {
            try {
                socket = mmServerSocket?.accept()
            } catch (e: IOException) {
                break
            }
            if (socket != null) {
                mmServerSocket?.close()
                break
            }
        }
    }

    fun cancel() {
        try {
            mmServerSocket?.close()
        } catch (e: IOException) {
        }
    }

}