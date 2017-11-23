package com.example.loren.minesample.fragment

import android.view.View
import com.example.loren.minesample.*
import com.example.loren.minesample.base.ui.BaseFragment
import kotlinx.android.synthetic.main.util_fragment.*

/**
 * Copyright © 2017/3/7 by loren
 */

class UtilsFragment : BaseFragment() {
    override fun initWidgets() {
    }


    override fun setListeners() {
        click(bezier_tv, screen_shot_tv, test_tv, inject_tv)
    }

    override fun onWidgetsClick(v: View) {
        when (v.id) {
            R.id.bezier_tv -> startActivity<BezierActivity>()
            R.id.screen_shot_tv -> startActivity<ScreenShotActivity>()
            R.id.test_tv -> startActivity<ObjectBoxActivity>()
            R.id.inject_tv -> startActivity<InjectAnimActivity>()
        }
    }

    override fun useTitleBar() = false
    override fun bindLayout() = R.layout.util_fragment

}
