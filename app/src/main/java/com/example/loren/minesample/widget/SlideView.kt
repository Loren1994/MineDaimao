package com.example.loren.minesample.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.TextView
import com.example.loren.minesample.R
import pers.victor.ext.dp2px
import pers.victor.ext.findColor

/**
 * Copyright © 2018/2/5 by loren
 */
class SlideView(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {

    private var mPaint: Paint = Paint()
    private var textList = arrayListOf<String>()
    private var clickIndex = 0
    var onSlideItemListener: ((index: Int, text: String) -> Unit)? = null
    private var fromScroll = false

    init {
        //不设背景默认不走onDraw
        setBackgroundColor(findColor(R.color.transparent))
        mPaint.isAntiAlias = true
        mPaint.color = findColor(R.color.trans_black)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (childCount > 0) {
            measureChildren(widthMeasureSpec, heightMeasureSpec)
            setMeasuredDimension(paddingStart + paddingEnd + getChildAt(0).measuredWidth, paddingTop + paddingBottom + getChildAt(0).measuredHeight * childCount)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), measuredWidth / 2.0f, measuredWidth / 2.0f, mPaint)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        repeat(childCount) {
            val child = getChildAt(it)
            child.layout(paddingStart, paddingTop + it * child.measuredHeight, paddingStart + measuredWidth, paddingTop + (it + 1) * child.measuredHeight)
        }
    }


    fun setData(data: MutableList<String>) {
        removeAllViews()
        textList.clear()
        textList.addAll(data)
        textList.filter { !it.isEmpty() }.forEachIndexed { index, item ->
            val tv = TextView(context)
            tv.text = item
            tv.setTextColor(Color.WHITE)
            tv.gravity = Gravity.CENTER
            tv.setPadding(dp2px(4), dp2px(4), dp2px(4), dp2px(4))
            tv.setOnClickListener {
                setTvCheck(index)
                if (!fromScroll) {
                    onSlideItemListener?.invoke(index, item)
                }
                fromScroll = false
            }
            addView(tv)
        }
        setTvCheck(0)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_MOVE) {
            return true
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_MOVE) {
            scrollTvList(event.y)
        }
        return true
    }

    private fun scrollTvList(y: Float) {
        val itemHeight = getChildAt(0).measuredHeight
        var index = Math.floor((y - paddingTop) / itemHeight.toDouble()).toInt()
        index = Math.min(index, childCount - 1)
        if (index < 0) {
            index = 0
        }
        if (clickIndex != index) {
            setTvCheck(index)
            getChildAt(index).performClick()
        }
    }

    private fun setTvCheck(index: Int) {
        repeat(childCount) {
            if (index == it) {
                clickIndex = index
                (getChildAt(it) as TextView).setTextColor(findColor(R.color.green))
            } else {
                (getChildAt(it) as TextView).setTextColor(Color.WHITE)
            }
        }
    }

    //外部列表滚动时调用此方法
    fun setCurrentCheck(index: Int) {
        if (clickIndex != index) {
            fromScroll = true
            setTvCheck(index)
            getChildAt(index).performClick()
        }
    }
}