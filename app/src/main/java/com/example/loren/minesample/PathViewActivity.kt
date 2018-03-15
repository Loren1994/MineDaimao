package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.path_view_activity.*
import pers.victor.ext.toast

/**
 * Copyright © 2018/3/7 by loren
 */
class PathViewActivity : BaseActivity() {
    override fun initWidgets() {
        msg_tv.setOnClickListener { toast("ping百度是否成功:${isNetWorkAvailable()}") }
    }

    private fun isNetWorkAvailable(): Boolean {
        val pingProcess = Runtime.getRuntime().exec("/system/bin/ping -c 1 www.baidu.com")
        val exitCode = pingProcess.waitFor() //0 代表连通，2代表不通
        return exitCode == 0
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.path_view_activity
}