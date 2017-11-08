package com.example.loren.minesample

import android.Manifest
import android.os.Environment
import android.os.FileObserver
import android.view.View
import android.view.WindowManager
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.hulab.debugkit.DebugFunction
import com.hulab.debugkit.DevTool
import kotlinx.android.synthetic.main.screen_shot_activity.*
import pers.victor.ext.toast
import java.io.File


/**
 * Copyright © 30/10/2017 by loren
 */
class ScreenShotActivity : BaseActivity() {
    private lateinit var PATH: String
    private var mFileObserver: CustomFileObserver? = null

    override fun initWidgets() {
        PATH = "${Environment.getExternalStorageDirectory()}${File.separator}${Environment.DIRECTORY_PICTURES}${File.separator}Screenshots${File.separator}"
        log(">>>> $PATH")
        val tp1 = screen_tv.paint
        tp1.isFakeBoldText = true
        screen_tv.postInvalidate()
        requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE, granted = {
            mFileObserver = CustomFileObserver(PATH)
            mFileObserver!!.startWatching()
        }, denied = {
            toast("没有权限，请授权后重试")
        })

        DevTool.Builder(this)
                .addFunction(object : DebugFunction() {
                    override fun call(): String {
                        screen_iv.visibility = View.VISIBLE
                        return "显示狗"
                    }
                }).addFunction(object : DebugFunction() {
                    override fun call(): String {
                        screen_iv.visibility = View.GONE
                        return "隐藏狗"
                    }
                }).build()
    }

    override fun setListeners() {
        click(screen_tv, cannot_screen_tv)
    }

    override fun onWidgetsClick(v: View) {
        when (v.id) {
            R.id.screen_tv -> {
                setTextStyle(true)
                window.clearFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
            R.id.cannot_screen_tv -> {
                setTextStyle(false)
                window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
            }
        }
    }

    private inner class CustomFileObserver(path: String) : FileObserver(path) {

        override fun onEvent(event: Int, path: String?) {
            log("path : $path - $event")
        }
    }

    override fun onDestroy() {
        if (null != mFileObserver)
            mFileObserver!!.stopWatching()
        super.onDestroy()
    }

    private fun setTextStyle(type: Boolean) {
        val tp1 = screen_tv.paint
        tp1.isFakeBoldText = type
        screen_tv.postInvalidate()
        val tp2 = cannot_screen_tv.paint
        tp2.isFakeBoldText = !type
        cannot_screen_tv.postInvalidate()
    }

    override fun bindLayout() = R.layout.screen_shot_activity
}