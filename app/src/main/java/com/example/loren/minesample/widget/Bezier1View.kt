package com.example.loren.minesample.widget

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.loren.minesample.R
import java.util.*


/**
 * Copyright © 2017/10/13 by loren
 */
class Bezier1View(context: Context, attributes: AttributeSet) : View(context, attributes) {

    private val BEZIER_COEFICIENT = 0.551915024494f //bezier倍数(圆形)
    private val DURING = 1000L
    private var RADIUS = 0 //半径
    private var centerPoint = PointF() //坐标系中心点
    private var controlPoint = ArrayList<PointF>() //12个控制点
    private var controlArr = Array(12) { FloatArray(2) } //12个控制点二维数组
    private lateinit var mPaint: Paint //心形Paint
    private lateinit var linePaint: Paint//辅助线Paint
    private var mPath: Path? = null
    private var color = 0 //颜色
    private lateinit var startAnim: ValueAnimator
    private lateinit var endAnim: ValueAnimator
    var showSubline = true //是否显示辅助线
    var isCircle = true //是否是圆形

    init {
        init(context, attributes)
    }

    private fun init(context: Context, attributes: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.HeartView)
        color = typedArray.getColor(R.styleable.HeartView_heart_color, Color.RED)
        typedArray.recycle()
        mPath = Path()
        mPaint = Paint()
        linePaint = Paint()
        linePaint.color = Color.BLUE
        linePaint.strokeWidth = 3f
        linePaint.isAntiAlias = true
        mPaint.color = color
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 3f
        mPaint.isAntiAlias = true
        for (i in 0..11) {
            controlPoint.add(PointF())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        centerPoint.x = (View.MeasureSpec.getSize(widthMeasureSpec) / 2).toFloat()
        centerPoint.y = (View.MeasureSpec.getSize(heightMeasureSpec) / 2).toFloat()
        RADIUS = (View.MeasureSpec.getSize(heightMeasureSpec) / 2)
        startAnim = ValueAnimator.ofFloat(1f, (RADIUS / 2f)).setDuration(DURING)
        endAnim = ValueAnimator.ofFloat((RADIUS / 2f), 1f).setDuration(DURING)
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
        mPath!!.reset()
        //二阶
        //mPath!!.quadTo(controlPoint[0].x, controlPoint[0].y, controlPoint[1].x, controlPoint[1].y)
        //三阶
        mPath!!.moveTo(controlPoint[6].x, controlPoint[6].y)
        mPath!!.cubicTo(controlPoint[5].x, controlPoint[5].y, controlPoint[4].x, controlPoint[4].y, controlPoint[3].x, controlPoint[3].y)
        mPath!!.cubicTo(controlPoint[2].x, controlPoint[2].y, controlPoint[1].x, controlPoint[1].y, controlPoint[0].x, controlPoint[0].y)
        mPath!!.cubicTo(controlPoint[11].x, controlPoint[11].y, controlPoint[10].x, controlPoint[10].y, controlPoint[9].x, controlPoint[9].y)
        mPath!!.cubicTo(controlPoint[8].x, controlPoint[8].y, controlPoint[7].x, controlPoint[7].y, controlPoint[6].x, controlPoint[6].y)
        canvas!!.drawPath(mPath, mPaint)

        if (showSubline) {
            canvas.drawPoint(centerPoint.x, centerPoint.y, mPaint)
            controlPoint.forEach {
                canvas.drawPoint(it.x, it.y, mPaint)
            }
            canvas.drawLine(controlPoint[11].x, controlPoint[11].y, controlPoint[0].x, controlPoint[0].y, linePaint)
            canvas.drawLine(controlPoint[0].x, controlPoint[0].y, controlPoint[1].x, controlPoint[1].y, linePaint)
            canvas.drawLine(controlPoint[2].x, controlPoint[2].y, controlPoint[3].x, controlPoint[3].y, linePaint)
            canvas.drawLine(controlPoint[3].x, controlPoint[3].y, controlPoint[4].x, controlPoint[4].y, linePaint)
            canvas.drawLine(controlPoint[5].x, controlPoint[5].y, controlPoint[6].x, controlPoint[6].y, linePaint)
            canvas.drawLine(controlPoint[6].x, controlPoint[6].y, controlPoint[7].x, controlPoint[7].y, linePaint)
            canvas.drawLine(controlPoint[8].x, controlPoint[8].y, controlPoint[9].x, controlPoint[9].y, linePaint)
            canvas.drawLine(controlPoint[9].x, controlPoint[9].y, controlPoint[10].x, controlPoint[10].y, linePaint)
        }
    }

    private fun startTransformation() {
        if (startAnim.isRunning || endAnim.isRunning) {
            return
        }
        startAnim.removeAllUpdateListeners()
        startAnim.addUpdateListener {
            controlPoint[6].y = it.animatedValue as Float
            controlPoint[11].y = 2f * RADIUS - 2f / 3f * it.animatedValue as Float
            controlPoint[1].y = 2f * RADIUS - 2f / 3f * it.animatedValue as Float
            controlPoint[2].x = 2f * RADIUS - 2f / 21f * it.animatedValue as Float
            controlPoint[10].x = 2f / 21f * it.animatedValue as Float
            postInvalidate()
        }
        startAnim.start()
        isCircle = false
    }

    private fun resetShape() {
        if (startAnim.isRunning || endAnim.isRunning) {
            return
        }
        endAnim.removeAllUpdateListeners()
        endAnim.addUpdateListener {
            controlPoint[6].y = it.animatedValue as Float
            controlPoint[11].y = 2f * RADIUS - 2f / 3f * it.animatedValue as Float
            controlPoint[1].y = 2f * RADIUS - 2f / 3f * it.animatedValue as Float
            controlPoint[2].x = 2f * RADIUS - 2f / 21f * it.animatedValue as Float
            controlPoint[10].x = 2f / 21f * it.animatedValue as Float
            postInvalidate()
        }
        endAnim.start()
        isCircle = true
    }

    fun transformation() {
        if (isCircle) {
            startTransformation()
        } else {
            resetShape()
        }
    }

}