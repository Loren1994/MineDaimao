package com.example.loren.minesample.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import pers.victor.ext.sp2px

/**
 * Copyright © 2018/3/7 by loren
 */
class PathView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var totalPathLength = 0f
    private var pathMeasure = PathMeasure()
    private var textPaint = Paint()
    private var drawPaint = Paint()
    private var textPath = Path()
    private var textWidth = 0f
    private var valueAnim = ValueAnimator()
    private var proPaint = Paint()
    private var dstPath = Path()
    private var curProgress = 0f
    val DURING_TIME = 10000L
    val mText = "Loren NB !"


    init {
        init()
    }

    private fun init() {
        proPaint.isAntiAlias = true
        proPaint.color = Color.WHITE
        proPaint.strokeWidth = 6f
        proPaint.style = Paint.Style.STROKE
        proPaint.strokeCap = Paint.Cap.ROUND
        drawPaint.isAntiAlias = true
        drawPaint.color = Color.BLACK
        drawPaint.strokeWidth = 3f
        drawPaint.style = Paint.Style.STROKE
        textPaint.textSize = sp2px(50).toFloat()
        textPaint.getTextPath(mText, 0, mText.length, 0f, textPaint.textSize, textPath)
        textPath.close() // !!!不close,drawPath无效
        pathMeasure.setPath(textPath, false)
        while (pathMeasure.nextContour()) {
            totalPathLength += pathMeasure.length
        }
        initAnimation()
        setOnClickListener { valueAnim.start() }
    }

    private fun initAnimation() {
        valueAnim.setFloatValues(0f, 1f)
        valueAnim.duration = DURING_TIME
        valueAnim.addUpdateListener {
            curProgress = it.animatedValue as Float * totalPathLength
            pathMeasure.setPath(textPath, false)
            dstPath.reset() //逐个轮廓画
            while (curProgress > pathMeasure.length) {
                curProgress -= pathMeasure.length
                //不加此行会导致轮廓不封闭
                pathMeasure.getSegment(0f, pathMeasure.length, dstPath, true)
                if (!pathMeasure.nextContour()) {
                    break
                }
            }
            //逐个轮廓按进度画
            pathMeasure.getSegment(0f, curProgress, dstPath, true)
            invalidate()
        }
    }

    private fun computeTextWidth() {
        val widths = FloatArray(mText.length)
        textPaint.getTextWidths(mText, 0, mText.length, widths)
        var tempWidth = 0f
        for (j in 0 until mText.length) {
            tempWidth += Math.ceil(widths[j].toDouble()).toInt()
        }
        textWidth = tempWidth
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        computeTextWidth()
        setMeasuredDimension(textWidth.toInt(), textPaint.fontSpacing.toInt())
    }


    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(textPath, drawPaint)
        canvas.drawPath(dstPath, proPaint)
    }


}