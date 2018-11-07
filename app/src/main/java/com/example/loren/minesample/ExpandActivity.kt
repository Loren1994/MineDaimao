package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.expand_activity.*
import kotlinx.coroutines.*
import pers.victor.ext.toast

/**
 * Copyright © 2018/3/15 by loren
 */
class ExpandActivity : BaseActivity() {

    override fun initWidgets() {
        test_btn.setOnClickListener {
            doAny()
            toast(">>>>>>>")
        }
    }

    private fun doAny() = runBlocking {
        launch {
            val a = async(CommonPool) {
                delay(1000)
                runOnUiThread { toast("launch") }
            }
            a.await()
            //a.cancel()
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.expand_activity
}