package com.example.loren.minesample.adapter

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.text.TextUtils
import com.example.loren.minesample.R
import com.example.loren.minesample.base.ext.CommonAdapter
import com.example.loren.minesample.base.ext.Holder
import kotlinx.android.synthetic.main.item_ble_list.*
import pers.victor.ext.findDrawable

/**
 * Copyright © 2018/3/26 by loren
 */
class BleAdapter(val data: ArrayList<BluetoothDevice>, val onItemClickListener: ((device: BluetoothDevice) -> Unit)) : CommonAdapter() {

    override fun bindLayout() = R.layout.item_ble_list

    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val device = data[position]
        holder.ble_state_view.background = findDrawable(if (device.bondState == 12) R.drawable.bg_green_coner else R.drawable.bg_main_coner)
        holder.item_name_tv.text = if (TextUtils.isEmpty(device.name)) device.address else device.name
        holder.itemView.setOnClickListener { onItemClickListener.invoke(device) }
    }
}