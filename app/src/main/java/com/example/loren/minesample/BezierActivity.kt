package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.bezier_activity.*

/**
 * Copyright © 2017/10/13 by loren
 */
class BezierActivity : BaseActivity() {
    override fun initWidgets() {
        bezier_view.showSubline = false
        bezier_view.setOnClickListener { bezier_view.transformation() }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.bezier_activity

}