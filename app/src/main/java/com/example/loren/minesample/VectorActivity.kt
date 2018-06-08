package com.example.loren.minesample

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_vector.*
import pers.victor.ext.dp2px
import pers.victor.ext.toast
import java.util.*


class VectorActivity : BaseActivity() {
    @SuppressLint("SetTextI18n")
    override fun initWidgets() {
        requestPermission(Manifest.permission.RECORD_AUDIO, granted = { }, denied = { toast("没有权限，请授权后重试") })
        for (i in 0..14) {
            val item = LayoutInflater.from(this).inflate(R.layout.volume_item, null)
            volume_container_ll!!.addView(item)
        }
        mAudioRecord = AudioRecordDemo(AudioRecordDemo.OnVolumeListener { volume ->
            Log.d("loren", "分贝值：$volume")
            runOnUiThread { cur_volume_tv!!.text = "分贝值：" + String.format(Locale.CHINA, "%.2f", volume) }
            for (i in 0 until volume_container_ll!!.childCount) {
                val view = (volume_container_ll!!.getChildAt(i) as RelativeLayout).getChildAt(0) as TextView
                val heightNum = (volume * if (i > volume_container_ll!!.childCount / 2) volume_container_ll!!.childCount - i else i).toFloat()
                addListener(view)
                view.postDelayed({
                    val params = view.layoutParams
                    val heightTemp = params.height
                    val animator = ValueAnimator.ofInt(heightTemp, dp2px(heightNum))
                    animator.duration = 100
                    animator.addUpdateListener { animation ->
                        params.height = animation.animatedValue as Int
                        view.layoutParams = params
                    }
                    animator.start()
                }, (50 * i).toLong())
            }
        })
        mAudioRecord!!.getNoiseLevel()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.activity_vector

    private var mAudioRecord: AudioRecordDemo? = null
    private val CHANGE_SIZE = 50

    private fun addListener(view: View) {
        view.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_UP -> changeParams(v, MotionEvent.ACTION_UP)
                MotionEvent.ACTION_DOWN -> changeParams(v, MotionEvent.ACTION_DOWN)
                MotionEvent.ACTION_MOVE -> {
                }
            }
            true
        }
    }

    private fun changeParams(view: View, event: Int) {
        val params = view.layoutParams
        if (event == MotionEvent.ACTION_DOWN) {
            params.width += CHANGE_SIZE
            params.height += CHANGE_SIZE
        } else {
            params.width -= CHANGE_SIZE
            params.height -= CHANGE_SIZE
        }
        view.layoutParams = params
    }

    override fun onDestroy() {
        mAudioRecord!!.stopAudioThread()
        super.onDestroy()
    }
}
