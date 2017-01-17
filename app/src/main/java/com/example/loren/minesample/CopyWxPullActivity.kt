package com.example.loren.minesample

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_copy_wx_pull.*

class CopyWxPullActivity : AppCompatActivity() {

    private var downY = 0
    private var isOpen = false
    private var radius = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copy_wx_pull)
    }

    override fun onResume() {
        super.onResume()
        isOpen = false
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.CENTER
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> downY = ev.rawY.toInt()
            MotionEvent.ACTION_MOVE -> {
                val offset: Int = (ev.rawY.toInt() - downY) / 8
                param.setMargins(0, offset * 3, 0, 0)
                eyes.layoutParams = param
                eyes.setRadius(offset)
                Log.d("loren", "offset=" + offset)
                if (offset >= 130) {
//                    startActivity(Intent(this, CopyWxActivity::class.java))
                    isOpen = true
                    text.text = "松开拍摄"
                } else {
                    isOpen = false
                    text.text = "下拉睁眼"
                }
                radius = offset
            }
            MotionEvent.ACTION_UP -> {
                reset(radius)
                if (isOpen) {
                    startActivity(Intent(this, CopyWxActivity::class.java))
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun reset(radius: Int) {
        val animator = ObjectAnimator.ofInt(eyes, "Y", eyes.y.toInt(), 0).setDuration(800)
        val radiuAnim = ObjectAnimator.ofInt(eyes, "Y", radius, 0).setDuration(800)
        radiuAnim.addUpdateListener { radiuAnim -> eyes.setRadius(radiuAnim.animatedValue as Int) }
        animator.addUpdateListener { animator ->
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.CENTER
            params.setMargins(0, animator.animatedValue as Int, 0, 0)
            eyes.layoutParams = params
        }
        radiuAnim.start()
        animator.start()
        text.text = "下拉睁眼"
    }
}