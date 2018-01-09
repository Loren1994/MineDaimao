package com.example.loren.minesample.fragment

import android.view.View
import com.example.loren.minesample.*
import com.example.loren.minesample.annotation.AnimationClick
import com.example.loren.minesample.annotation.AnimationClickType
import com.example.loren.minesample.annotation.LorenInject
import com.example.loren.minesample.base.ui.BaseFragment

/**
 * Copyright © 2017/3/7 by loren
 */

class UtilsFragment : BaseFragment() {
    override fun initWidgets() {
        LorenInject.into(this)
    }

    @AnimationClick([R.id.bezier_tv,R.id.download_tv, R.id.screen_shot_tv, R.id.test_tv, R.id.inject_tv, R.id.remote_tv], AnimationClickType.ALPHA)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.bezier_tv -> startActivity<BezierActivity>()
            R.id.screen_shot_tv -> startActivity<ScreenShotActivity>()
            R.id.test_tv -> startActivity<ObjectBoxActivity>()
            R.id.inject_tv -> startActivity<InjectAnimActivity>()
            R.id.remote_tv -> startActivity<RemoteViewActivity>()
            R.id.download_tv -> startActivity<DownloadingActivity>()
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {

    }

    override fun useTitleBar() = false

    override fun bindLayout() = R.layout.util_fragment

}
