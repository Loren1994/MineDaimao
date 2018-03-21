package com.example.loren.minesample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Copyright © 09/01/2018 by loren
 */
class SignWithRevertView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var signPaint = Paint()
    //    private var signPath = Path()
    private val SIGN_WIDTH = 20f
//    private var preX = 0f
//    private var preY = 0f
//    private val MOVE_OFFSET = 20f

    private var pathList = arrayListOf<Path>()

    init {
        signPaint.color = Color.WHITE
        signPaint.isAntiAlias = true
        signPaint.style = Paint.Style.STROKE
        signPaint.strokeWidth = SIGN_WIDTH
        signPaint.strokeCap = Paint.Cap.ROUND
        signPaint.strokeJoin = Paint.Join.ROUND
        signPaint.pathEffect = CornerPathEffect(50f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        pathList.forEach {
            canvas.drawPath(it, signPaint)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val path = Path()
                path.moveTo(event.x, event.y)
                pathList.add(path)
            }
            MotionEvent.ACTION_MOVE -> signMove(event)
        }
        invalidate()
        return true
    }

    private fun signMove(event: MotionEvent) {
        pathList[pathList.lastIndex].lineTo(event.x, event.y)
    }

    fun resetSign() {
        if (pathList.isNotEmpty()) {
            pathList.removeAt(pathList.lastIndex)
            invalidate()
        }
    }

    fun getSignBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        canvas.save()
        return bitmap
    }
}