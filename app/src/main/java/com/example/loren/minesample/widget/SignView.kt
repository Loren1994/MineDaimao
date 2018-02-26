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
class SignView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var signPaint = Paint()
    private var signPath = Path()
    private val SIGN_WIDTH = 20f
//    private var preX = 0f
//    private var preY = 0f
//    private val MOVE_OFFSET = 20f

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
        canvas.drawPath(signPath, signPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> signPath.moveTo(event.x, event.y)
            MotionEvent.ACTION_MOVE -> signMove(event)
        }
        invalidate()
        return true
    }

    private fun signMove(event: MotionEvent) {
        signPath.lineTo(event.x, event.y)
//        val x = event.x
//        val y = event.y
//        if (Math.abs(x - preX) > MOVE_OFFSET || Math.abs(y - preY) > MOVE_OFFSET) {
//            val tX = (preX + x) / 2.0f
//            val tY = (preY + y) / 2.0f
//            signPath.quadTo(tX, tY, preX, preY)
//        } else {
//            signPath.lineTo(event.x, event.y)
//        }
//        preX = event.x
//        preY = event.y
    }

    fun resetSign() {
        signPath.reset()
        invalidate()
    }

    fun getSignBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        canvas.save()
        return bitmap
    }
}