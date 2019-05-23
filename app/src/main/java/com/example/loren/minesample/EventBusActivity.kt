package com.example.loren.minesample

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.eventbus.EventMsg
import com.example.loren.minesample.eventbus.FEventbus
import com.example.loren.minesample.eventbus.OnEventListener
import kotlinx.android.synthetic.main.eventbus_activity.*
import pers.victor.ext.dp2px

/**
 * Copyright © 2019/2/18 by loren
 */
class EventBusActivity : BaseActivity(), OnEventListener<EventMsg.LorenEventMsg> {

    @SuppressLint("SetTextI18n")
    override fun onEvent(event: EventMsg.LorenEventMsg) {
        val tv = TextView(this)
        tv.text = "收到消息:${event.type} - ${event.msg}"
        tv.setPadding(dp2px(8),dp2px(8),dp2px(8),dp2px(8))
        msg_container_ll.addView(tv)
//        val mAdapter = TestAdapter()
//        test_vp2.adapter = mAdapter as RecyclerView.Adapter<*>
    }

//    class TestAdapter : CommonAdapter() {
//
//        override fun bindLayout() = R.layout.item_test
//
//        override fun getItemCount() = 5
//
//        override fun onBindViewHolder(holder: Holder, p1: Int) {
//            holder.test_tv.text = "ITEM - $p1"
//        }
//
//    }

    override fun initWidgets() {
        FEventbus.register(this)
        event_bus_btn.setOnClickListener { startActivity<EventBusSendActivity>() }
        unregister_btn.setOnClickListener { FEventbus.unRegister(this) }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.eventbus_activity
}