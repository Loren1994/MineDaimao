package com.example.loren.minesample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.view.View
import android.widget.SeekBar
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.custom_view_activity.*

/**
 * Copyright (c) 16-9-18 by loren
 */

class CustomViewActivity : BaseActivity() {
    override fun initWidgets() {
        setAnim()
        r.max = 255
        g.max = 255
        b.max = 255
        r.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mR = progress
                five_star.setColor(Color.rgb(mR, mG, mB))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        g.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mG = progress
                five_star.setColor(Color.rgb(mR, mG, mB))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        b.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mB = progress
                five_star.setColor(Color.rgb(mR, mG, mB))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout()=R.layout.custom_view_activity

    private var mR: Int = 0
    private var mG: Int = 0
    private var mB: Int = 0

    private fun setAnim() {
        window.enterTransition = Fade().setDuration(1000)
        window.enterTransition = Fade().setDuration(1000)
    }
}
