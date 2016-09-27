package com.example.loren.minesample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_copy_wx_pull.*

class CopyWxPullActivity : AppCompatActivity() {

    private var downY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_copy_wx_pull)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        param.gravity = Gravity.CENTER
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> downY = ev.rawY.toInt()
            MotionEvent.ACTION_MOVE -> {
                val offset: Int = ev.rawY.toInt() - downY
                param.setMargins(0, offset, 0, 0)
                eyes.layoutParams = param
                eyes.setRadius(offset)
                Log.d("loren", "offset=" + offset)
                if (offset == 120) {
                    startActivity(Intent(this, CopyWxActivity::class.java))
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}
