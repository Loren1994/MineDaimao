package com.example.loren.minesample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

/**
 *                            _ooOoo_
 *                           o8888888o
 *                           88" . "88
 *                           (| -_- |)
 *                            O\ = /O
 *                        ____/`---'\____
 *                      .   ' \\| |// `.
 *                       / \\||| : |||// \
 *                     / _||||| -:- |||||- \
 *                       | | \\\ - /// | |
 *                     | \_| ''\---/'' | |
 *                      \ .-\__ `-` ___/-. /
 *                   ___`. .' /--.--\ `. . __
 *                ."" '< `.___\_<|>_/___.' >'"".
 *               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *                 \ \ `-. \_ __\ /__ _/ .-` / /
 *         ======`-.____`-.___\_____/___.-`____.-'======
 *                            `=---='
 *
 *         .............................................
 *                  佛祖保佑             永无BUG
 *
 *                Copyright (c) 16-9-22 by loren
 */
class MoveView(context: Context, attributeSet: AttributeSet) : TextView(context, attributeSet) {

    var downX: Float = 0f
    var downY: Float = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                x += (event.x - downX)
                y += (event.y - downY)
            }
        }
        return true
    }

    fun MoveViewWithFinger(view: View, X: Float, Y: Float) {
        val param = view.layoutParams as LinearLayout.LayoutParams
        param.leftMargin = X.toInt() - view.width / 2
        param.topMargin = Y.toInt() - view.height / 2
        view.layoutParams = param
    }

}