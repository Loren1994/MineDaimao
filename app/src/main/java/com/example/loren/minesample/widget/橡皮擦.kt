package com.example.loren.minesample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import pers.victor.ext.dp2px
import pers.victor.ext.screenWidth

/**
 * Copyright © 2018/5/11 by loren
 */
class 橡皮擦(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private val SIGN_WIDTH = 20f
    private val MIN_QUAD = 10
    private var signPaint = Paint()
    private var clearPaint = Paint()
    private var clearXMode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    private var signPath = Path()
    private var mBitmap: Bitmap
    private var mCanvas: Canvas
    private var mX = 0f
    private var mY = 0f
    var isClear = false

    init {
        signPaint.isAntiAlias = true
        signPaint.style = Paint.Style.STROKE
        signPaint.strokeWidth = SIGN_WIDTH
        signPaint.strokeCap = Paint.Cap.ROUND
        signPaint.strokeJoin = Paint.Join.ROUND
        signPaint.pathEffect = CornerPathEffect(50f)

        clearPaint.xfermode = clearXMode
        clearPaint.isAntiAlias = true
        clearPaint.style = Paint.Style.STROKE
        clearPaint.strokeWidth = SIGN_WIDTH
        clearPaint.strokeCap = Paint.Cap.ROUND
        clearPaint.strokeJoin = Paint.Join.ROUND
        clearPaint.pathEffect = CornerPathEffect(50f)
        clearPaint.alpha = 0

        mBitmap = Bitmap.createBitmap(screenWidth, dp2px(300), Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mBitmap, 0f, 0f, signPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                signPath.reset()
                signPath.moveTo(event.x, event.y)
                mX = event.x
                mY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                touchMove(event.x, event.y)
            }
            MotionEvent.ACTION_UP -> {
                signMove(event)
            }
        }
        mCanvas.drawPath(signPath, if (!isClear) signPaint else clearPaint)
        invalidate()
        return true
    }

    private fun touchMove(x: Float, y: Float) {
        val dx = Math.abs(x - mX)
        val dy = Math.abs(y - mY)
        if (dx >= MIN_QUAD || dy >= MIN_QUAD) {
            signPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2)
            mX = x
            mY = y
        }
    }

    private fun signMove(event: MotionEvent) {
        signPath.lineTo(event.x, event.y)
    }

    fun resetSign() {
        mBitmap = Bitmap.createBitmap(screenWidth, dp2px(300), Bitmap.Config.ARGB_8888)
        mCanvas = Canvas(mBitmap)
        invalidate()
    }

    fun getSignBitmap(): Bitmap {
        return mBitmap
    }
}