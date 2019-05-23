package com.example.loren.minesample

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.eventbus.EventMsg
import com.example.loren.minesample.eventbus.FEventbus
import kotlinx.android.synthetic.main.eventbus_send_activity.*
import pers.victor.ext.dp2px

/**
 * Copyright © 2019/2/20 by loren
 */
class EventBusSendActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun initWidgets() {
        event_bus_btn.setOnClickListener {
            FEventbus.post(EventMsg.LorenEventMsg(type_edt.text.toString(), msg_edt.text.toString()))
            val tv = TextView(this)
            tv.text = "已发送消息:${type_edt.text} - ${msg_edt.text}"
            tv.setPadding(dp2px(8),dp2px(8),dp2px(8),dp2px(8))
            msg_send_container_ll.addView(tv)
        }
        event_bus1_btn.setOnClickListener {
            FEventbus.post(EventMsg.LorenEventMsg1(type_edt.text.toString(), msg_edt.text.toString()))
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.eventbus_send_activity
}