package com.example.loren.minesample

import android.animation.ObjectAnimator
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_copy_wx_pull.*

class CopyWxPullActivity : BaseActivity() {
    override fun initWidgets() {

    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.activity_copy_wx_pull

    private var downY = 0
    private var isOpen = false
    private var radius = 0

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
                Log.d("loren", "offset=$offset")
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

    private fun reset(radius: Int) {
        val animator = ObjectAnimator.ofInt(eyes, "Y", eyes.y.toInt(), 0).setDuration(800)
        val radiusAnim = ObjectAnimator.ofInt(eyes, "Y", radius, 0).setDuration(800)
        radiusAnim.addUpdateListener { eyes.setRadius(radiusAnim.animatedValue as Int) }
        animator.addUpdateListener {
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.CENTER
            params.setMargins(0, it.animatedValue as Int, 0, 0)
            eyes.layoutParams = params
        }
        radiusAnim.start()
        animator.start()
        text.text = "下拉睁眼"
    }
}
