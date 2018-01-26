package com.example.loren.minesample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import pers.victor.ext.*


/**
 * Copyright © 25/01/2018 by loren
 */
class HWLockView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private lateinit var mPaint: Paint
    private lateinit var linearShader: Shader
    private lateinit var radiaShader: Shader
    private var OVER_FLAG = 0f
    private val RADIUS = 300f
    private var animX = 0f
    private var animY = 0f
    private var isTouch = false
    private val inXFerMode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private val outXFerMode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    private var downX = 0f
    var onScrollOver: (() -> Unit)? = null
    var scrollMode = ScrollMode.SLIDE_MODE
        set(value) =
            if (value == ScrollMode.SLIDE_MODE) {
                mPaint.xfermode = inXFerMode
                field = value
            } else {
                mPaint.xfermode = outXFerMode
                field = value
            }

    enum class ScrollMode {
        CIRCLE_MODE,
        SLIDE_MODE
    }

    init {
        init()
    }

    private fun init() {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.xfermode = inXFerMode
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(screenWidth, screenHeight + getVirNavBarHeight())
        OVER_FLAG = measuredWidth * 0.75f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawAlpha(canvas)
    }

    private fun drawAlpha(canvas: Canvas) {
        if (scrollMode == ScrollMode.SLIDE_MODE) {
            linearShader = LinearGradient(animX, animY, 0f, measuredHeight.toFloat(),
                    intArrayOf(Color.WHITE, isTouch.yes { Color.TRANSPARENT }.no { Color.WHITE }),
                    floatArrayOf(0.05f, 0.95f), Shader.TileMode.CLAMP)
            mPaint.shader = linearShader
            canvas.drawRect(RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat()), mPaint)
        } else {
            radiaShader = RadialGradient(animX, animY, RADIUS, (isTouch).yes { Color.BLACK }.no { Color.TRANSPARENT }, Color.TRANSPARENT, Shader.TileMode.CLAMP)
            mPaint.shader = radiaShader
            canvas.drawCircle(animX, animY, RADIUS, mPaint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        animX = event.x
        animY = event.y
        when (event.action) {
            MotionEvent.ACTION_UP -> {
                isTouch = false
            }
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                isTouch = true
            }
            MotionEvent.ACTION_MOVE -> {
                if (event.x - downX >= OVER_FLAG) {
                    onScrollOver?.invoke()
                }
            }
        }
        postInvalidate()
        return true
    }

}