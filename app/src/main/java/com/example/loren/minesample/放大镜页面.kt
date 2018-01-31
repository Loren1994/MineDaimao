package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity

/**
 * Copyright © 26/01/2018 by loren
 */
class 放大镜页面 : BaseActivity() {

    override fun allowFullScreen() = true

    override fun initWidgets() {

    }

    override fun useTitleBar() = false

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.glass_activity
}