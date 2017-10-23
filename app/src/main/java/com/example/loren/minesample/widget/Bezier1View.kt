package com.example.loren.minesample.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*


/**
 * Copyright © 2017/10/13 by loren
 */
class Bezier1View(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private val BEZIER_COEFICIENT = 0.551915024494f
    private val DURING = 1000
    private val mCount = 100f// 将时长总共划分多少份
    private var mCurrent = 0f // 当前已进行时长
    private val mPiece = DURING / mCount// 每一份的时长
    private var RADIUS = 0
    private var centerPoint = PointF() //坐标系中心点
    private var controlPoint = ArrayList<PointF>() //12个控制点
    private var controlArr = Array(12) { FloatArray(2) } //12个控制点二维数组
    private var mPaint: Paint? = null
    private var linePaint: Paint? = null
    private var mPath: Path? = null

    init {
        init()
    }

    private fun init() {
        mPath = Path()
        mPaint = Paint()
        linePaint = Paint()
        linePaint!!.color = Color.BLUE
        linePaint!!.strokeWidth = 5f
        linePaint!!.isAntiAlias = true
        mPaint!!.color = Color.WHITE
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 10f
        mPaint!!.isAntiAlias = true
        for (i in 0..11) {
            controlPoint.add(PointF())
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        centerPoint.x = (View.MeasureSpec.getSize(widthMeasureSpec) / 2).toFloat()
        centerPoint.y = (View.MeasureSpec.getSize(heightMeasureSpec) / 2).toFloat()
        RADIUS = (View.MeasureSpec.getSize(heightMeasureSpec) / 2)
        //p11
        controlArr[11][0] = centerPoint.x - RADIUS * BEZIER_COEFICIENT
        controlArr[11][1] = centerPoint.y + RADIUS
        //p0
        controlArr[0][0] = centerPoint.x
        controlArr[0][1] = centerPoint.y + RADIUS
        //p1
        controlArr[1][0] = centerPoint.x + RADIUS * BEZIER_COEFICIENT
        controlArr[1][1] = centerPoint.y + RADIUS
        //p2
        controlArr[2][0] = centerPoint.x + RADIUS
        controlArr[2][1] = centerPoint.y + RADIUS * BEZIER_COEFICIENT
        //p3
        controlArr[3][0] = centerPoint.x + RADIUS
        controlArr[3][1] = centerPoint.y
        //p4
        controlArr[4][0] = centerPoint.x + RADIUS
        controlArr[4][1] = centerPoint.y - RADIUS * BEZIER_COEFICIENT
        //p5
        controlArr[5][0] = centerPoint.x + RADIUS * BEZIER_COEFICIENT
        controlArr[5][1] = centerPoint.y - RADIUS
        //p6
        controlArr[6][0] = centerPoint.x
        controlArr[6][1] = centerPoint.y - RADIUS
        //p7
        controlArr[7][0] = centerPoint.x - RADIUS * BEZIER_COEFICIENT
        controlArr[7][1] = centerPoint.y - RADIUS
        //p8
        controlArr[8][0] = centerPoint.x - RADIUS
        controlArr[8][1] = centerPoint.y - RADIUS * BEZIER_COEFICIENT
        //p9
        controlArr[9][0] = centerPoint.x - RADIUS
        controlArr[9][1] = centerPoint.y
        //p10
        controlArr[10][0] = centerPoint.x - RADIUS
        controlArr[10][1] = centerPoint.y + RADIUS * BEZIER_COEFICIENT
        for (i in controlPoint.indices) {
            controlPoint[i].x = controlArr[i][0]
            controlPoint[i].y = controlArr[i][1]
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas!!.drawPoint(centerPoint.x, centerPoint.y, mPaint)
        canvas.drawColor(Color.RED)
        controlPoint.forEach {
            canvas.drawPoint(it.x, it.y, mPaint)
        }
        //二阶
        //mPath!!.quadTo(controlPoint[0].x, controlPoint[0].y, controlPoint[1].x, controlPoint[1].y)
        //三阶
        mPath!!.moveTo(controlPoint[6].x, controlPoint[6].y)
        mPath!!.cubicTo(controlPoint[5].x, controlPoint[5].y, controlPoint[4].x, controlPoint[4].y, controlPoint[3].x, controlPoint[3].y)
        mPath!!.cubicTo(controlPoint[2].x, controlPoint[2].y, controlPoint[1].x, controlPoint[1].y, controlPoint[0].x, controlPoint[0].y)
        mPath!!.cubicTo(controlPoint[11].x, controlPoint[11].y, controlPoint[10].x, controlPoint[10].y, controlPoint[9].x, controlPoint[9].y)
        mPath!!.cubicTo(controlPoint[8].x, controlPoint[8].y, controlPoint[7].x, controlPoint[7].y, controlPoint[6].x, controlPoint[6].y)
        canvas.drawPath(mPath, mPaint)

        canvas.drawLine(controlPoint[11].x, controlPoint[11].y, controlPoint[0].x, controlPoint[0].y, linePaint)
        canvas.drawLine(controlPoint[0].x, controlPoint[0].y, controlPoint[1].x, controlPoint[1].y, linePaint)
        canvas.drawLine(controlPoint[2].x, controlPoint[2].y, controlPoint[3].x, controlPoint[3].y, linePaint)
        canvas.drawLine(controlPoint[3].x, controlPoint[3].y, controlPoint[4].x, controlPoint[4].y, linePaint)
        canvas.drawLine(controlPoint[5].x, controlPoint[5].y, controlPoint[6].x, controlPoint[6].y, linePaint)
        canvas.drawLine(controlPoint[6].x, controlPoint[6].y, controlPoint[7].x, controlPoint[7].y, linePaint)
        canvas.drawLine(controlPoint[8].x, controlPoint[8].y, controlPoint[9].x, controlPoint[9].y, linePaint)
        canvas.drawLine(controlPoint[9].x, controlPoint[9].y, controlPoint[10].x, controlPoint[10].y, linePaint)


        mCurrent += mPiece
        if (mCurrent < DURING) {
            controlPoint[6].y += (RADIUS / 2) / mCount
            controlPoint[11].y -= (RADIUS / 3) / mCount
            controlPoint[1].y -= (RADIUS / 3) / mCount
            controlPoint[2].x -= (RADIUS / 21) / mCount
            controlPoint[10].x += (RADIUS / 21) / mCount
            postInvalidateDelayed(mPiece.toLong())
        }
        super.onDraw(canvas)
    }

}