package com.example.loren.minesample.fragment

import android.content.Intent
import android.view.View
import com.example.loren.minesample.BezierActivity
import com.example.loren.minesample.R
import com.example.loren.minesample.base.ui.BaseFragment
import kotlinx.android.synthetic.main.util_fragment.*

/**
 * Copyright © 2017/3/7 by loren
 */

class UtilsFragment : BaseFragment() {
    override fun initWidgets() {
        bezier_tv.setOnClickListener { startActivity(Intent(activity, BezierActivity::class.java)) }
    }

    override fun useTitleBar() = false

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.util_fragment

}
