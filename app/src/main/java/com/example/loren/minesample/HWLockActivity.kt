package com.example.loren.minesample

import android.graphics.drawable.ColorDrawable
import android.transition.Fade
import android.view.KeyEvent
import android.view.View
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.widget.HWLockView
import kotlinx.android.synthetic.main.hw_lock_activity.*
import pers.victor.ext.findColor
import pers.victor.ext.toast


/**
 * Copyright © 25/01/2018 by loren
 */
class HWLockActivity : BaseActivity() {

    override fun initWidgets() {
        window.enterTransition = Fade().setDuration(500)
        window.enterTransition = Fade().setDuration(500)
        window.setBackgroundDrawable(ColorDrawable(findColor(R.color.transparent)))
        lock_view.scrollMode = HWLockView.ScrollMode.SLIDE_MODE
        lock_view.onScrollOver = {
            onBackPressed()
        }
        slide_btn.setOnClickListener { lock_view.scrollMode = HWLockView.ScrollMode.SLIDE_MODE }
        circle_btn.setOnClickListener { lock_view.scrollMode = HWLockView.ScrollMode.CIRCLE_MODE }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        log(keyCode)
        when (keyCode) {
            KeyEvent.KEYCODE_HOME, KeyEvent.KEYCODE_MENU, KeyEvent.KEYCODE_POWER -> {
                // doesn't work
                return true
            }
            KeyEvent.KEYCODE_VOLUME_UP, KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_BACK -> {
                toast("手机坏了,别按了")
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun isGoneBar() = true

    override fun useTitleBar() = false

    override fun bindLayout() = R.layout.hw_lock_activity
}