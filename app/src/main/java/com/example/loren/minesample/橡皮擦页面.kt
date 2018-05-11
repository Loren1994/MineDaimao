package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.clear_activity.*

/**
 * Copyright © 2018/5/11 by loren
 */
class 橡皮擦页面 : BaseActivity() {
    override fun initWidgets() {
        clear_btn.setOnClickListener { board_view.isClear = true }
        paint_btn.setOnClickListener { board_view.isClear = false }
        complete_btn.setOnClickListener { complete_iv.setImageBitmap(board_view.getSignBitmap()) }
        reset_btn.setOnClickListener { board_view.resetSign() }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.clear_activity
}