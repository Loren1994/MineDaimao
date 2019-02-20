package com.example.loren.minesample

import android.app.ActivityManager
import android.content.Context
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.opengl.FirstGlRender
import kotlinx.android.synthetic.main.apollo_activity.*
import pers.victor.ext.toast

/**
 * Copyright © 2018/7/17 by loren
 */
class ApolloActivity : BaseActivity() {

    private var renderSet = false
    private var glSurfaceRender = FirstGlRender(this)

    override fun initWidgets() {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val support2 = activityManager.deviceConfigurationInfo.reqGlEsVersion >= 0x20000
        if (support2) {
            runOnUiThread { initGL() }
        } else {
            toast("设备不支持GLES-2.0")
        }
    }

    private fun initGL() {
        gl_surface.setEGLContextClientVersion(2)
        gl_surface.setRenderer(glSurfaceRender)
        gl_surface.setOnTouchListener { v, event ->
            val normalizedX = (event.x / v.width) * 2 - 1
            val normalizedY = -((event.y / v.height) * 2 - 1)
            when (event.action) {
                ACTION_DOWN -> gl_surface.queueEvent { glSurfaceRender.handleTouchPress(normalizedX, normalizedY) }
                ACTION_MOVE -> gl_surface.queueEvent { glSurfaceRender.handleTouchDrag(normalizedX, normalizedY) }
                else -> return@setOnTouchListener false
            }
            true
        }
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