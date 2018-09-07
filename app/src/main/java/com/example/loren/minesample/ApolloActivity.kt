package com.example.loren.minesample

import android.app.ActivityManager
import android.view.View
import androidx.core.content.systemService
import androidx.core.widget.toast
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.opengl.FirstGlRender
import kotlinx.android.synthetic.main.apollo_activity.*

/**
 * Copyright © 2018/7/17 by loren
 */
class ApolloActivity : BaseActivity() {

    private var renderSet = false

    override fun initWidgets() {
        val activityManager = systemService<ActivityManager>()
        val support2 = activityManager.deviceConfigurationInfo.reqGlEsVersion >= 0x20000
        if (support2) {
            initGL()
        } else {
            toast("设备不支持GLES-2.0")
        }
    }

    private fun initGL() {
        gl_surface.setEGLContextClientVersion(2)
        gl_surface.setRenderer(FirstGlRender(this))
        renderSet = true
    }

    override fun onPause() {
        super.onPause()
        if (renderSet) gl_surface.onPause()
    }

    override fun onResume() {
        super.onResume()
        if (renderSet) gl_surface.onResume()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.apollo_activity

}