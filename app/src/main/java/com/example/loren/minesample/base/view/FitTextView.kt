package com.example.loren.minesample.base.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.TextView

/**
 * Created by Victor on 16/5/10.
 */
class FitTextView(context: Context, attributeSet: AttributeSet) : TextView(context, attributeSet, 0) {
    companion object {
        fun adjustTextViewsWidth(textViews: List<FitTextView>) {
            var maxWidth = 0
            textViews.forEach { maxWidth = if (it.getTextWidth() > maxWidth) it.getTextWidth() else maxWidth }
            textViews.forEach { it.adjustWidth(maxWidth) }
        }
    }

    private var textPaint: Paint
    private var eachWidth = 0f
    private var spacingWidth = 0f
    private var currX = 0f
    private var textStr: String

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        measureText()
    }

    override fun onDraw(canvas: Canvas) {
        currX = 0f
        for (i in 0 until textStr.length) {
            canvas.drawText(textStr.elementAt(i).toString(), currX, baseline.toFloat(), textPaint)
            currX += eachWidth + spacingWidth
        }
    }

    fun getRealText() = textStr

    fun setText(text: String) {
        textStr = text
        measureText()
        postInvalidate()
    }

    private fun measureText() {
        if (textPaint.measureText(textStr) > minWidth) {
            minWidth = textPaint.measureText(textStr).toInt()
        }
        eachWidth = textPaint.measureText(textStr) / textStr.length
        spacingWidth = (measuredWidth - textPaint.measureText(textStr)) / (textStr.length - 1)
    }

    private fun adjustWidth(width: Int) {
        minWidth = width
        measureText()
        postInvalidate()
    }

    fun getTextWidth() = if (textPaint.measureText(textStr) > minWidth) textPaint.measureText(textStr).toInt() else minWidth

    init {
        textStr = text.toString()
        textPaint = Paint()
        textPaint.textSize = textSize
        textPaint.isAntiAlias = true
        textPaint.color = currentTextColor
        textPaint.textAlign = Paint.Align.LEFT
        minWidth = textPaint.measureText(textStr).toInt()
    }
}