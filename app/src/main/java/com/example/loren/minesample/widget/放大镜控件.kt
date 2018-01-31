package com.example.loren.minesample.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_UP
import android.view.View
import com.example.loren.minesample.R
import pers.victor.ext.getVirNavBarHeight
import pers.victor.ext.screenHeight
import pers.victor.ext.screenWidth

/**
 * Copyright © 26/01/2018 by loren
 */
class 放大镜控件(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var isTouch = false
    private var animX = 0f
    private var animY = 0f
    private val RADIUS = 200
    private val SCALE = 2
    private lateinit var mPaint: Paint
    private lateinit var shapeDrawable: ShapeDrawable
    private lateinit var mx: Matrix
    private lateinit var originBitmap: Bitmap
    private lateinit var scaleBitmap: Bitmap

    init {
        初始化()
    }

    private fun 初始化() {
        mx = Matrix()
        mPaint = Paint()
        mPaint.isAntiAlias = true
        shapeDrawable = ShapeDrawable(OvalShape())
        originBitmap = BitmapFactory.decodeResource(resources, R.drawable.wangye)
        scaleBitmap = Bitmap.createScaledBitmap(originBitmap, originBitmap.width * SCALE, originBitmap.height * SCALE, true)
        val bitmapShader = BitmapShader(scaleBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        shapeDrawable.paint.shader = bitmapShader
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(screenWidth, screenHeight + getVirNavBarHeight())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(originBitmap, 0f, 0f, null)
        画放大镜(canvas)
    }

    private fun 画放大镜(canvas: Canvas) {
        if (isTouch) {
            mx.setTranslate(RADIUS - animX * SCALE, RADIUS - animY * SCALE)
            shapeDrawable.paint.shader.setLocalMatrix(mx)
            shapeDrawable.setBounds(animX.toInt() - RADIUS, animY.toInt() - 2 * RADIUS, RADIUS + animX.toInt(), animY.toInt())
            shapeDrawable.draw(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        animX = event.x
        animY = event.y
        when (event.action) {
            ACTION_UP -> isTouch = false
            ACTION_DOWN -> isTouch = true
        }
        postInvalidate()
        return true
    }
}