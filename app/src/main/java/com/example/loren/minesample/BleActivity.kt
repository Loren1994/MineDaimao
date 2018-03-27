package com.example.loren.minesample

import android.bluetooth.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import bluetooth.BleServerThread
import com.example.loren.minesample.adapter.BleAdapter
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.ble_activity.*
import java.util.*

/**
 * Copyright © 2018/3/26 by loren
 */
class BleActivity : BaseActivity() {
    private lateinit var bleManager: BluetoothManager
    private lateinit var bleAdapter: BluetoothAdapter
    private var data = arrayListOf<BluetoothDevice>()
    private lateinit var mAdapter: BleAdapter
    private val mUUID: UUID
        get() = UUID.fromString("78321152-5f20-400b-9da9-1683d68e8f54")
    private lateinit var bleServerSocket: BluetoothServerSocket
    private lateinit var socketThread: Thread
    private lateinit var bleSocket: BluetoothSocket

    //三方库:fastBle

    override fun initWidgets() {
        setTitleBarRight("扫描")
        setTitleBarRightClick { bleAdapter.startDiscovery() }
        bleManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bleAdapter = bleManager.adapter
        // bleAdapter=BluetoothAdapter.getDefaultAdapter()
        checkBleDevice()
        mAdapter = BleAdapter(data) {
            if (it.bondState == 12) {
                startConnection(it)
            } else {
                it.createBond()
            }
        }
        ble_rv.adapter = mAdapter
        val filter = IntentFilter()
        filter.addAction(BluetoothDevice.ACTION_FOUND)
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED)
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bleReceiver, filter)
    }

    val bleReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (BluetoothDevice.ACTION_FOUND.contentEquals(intent.action)) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                log("${device.name} - ${device.address}")
                data.add(device)
                val temp = data.distinctBy { it.address }
                data.clear()
                data.addAll(temp)
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun startConnection(device: BluetoothDevice) {
//        val server = BleServerThread(device.name)
//        server.start()
    }


    override fun onDestroy() {
        unregisterReceiver(bleReceiver)
        super.onDestroy()
    }

    private fun checkBleDevice() {
        if (!bleAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            enableBtIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(enableBtIntent)
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.ble_activity
}