package com.example.loren.minesample.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*

/**
 * Copyright (c) 17-1-19 by loren
 */

class OpenScreenKT(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var listener: OnDialogListener? = null
    var password = ""//设置的锁屏密码(0~8)
    var isTouch = true//是否可用
    private var widthArea = 0f
    private var heightArea = 0f
    private val positionArr = ArrayList<PointF>()//9个点的坐标
    private var CIRCLE_RADIUS = 0f//9个圆的半径
    private var mPaint: Paint? = null
    private var mLinePaint: Paint? = null//圆和触摸点的线
    private val a = Array(9) { FloatArray(2) }//9个点的二维数组
    private val curCircle = ArrayList<Int>()//已连接的所有点的下标
    private var moveX = 0f
    private var moveY = 0f
    private var mClickPaint: Paint? = null//点击的点

    init {
        init()
    }

    private fun init() {
        //点正常状态的属性
        mPaint = Paint()
        mPaint!!.color = Color.GRAY
        mPaint!!.style = Paint.Style.STROKE
        mPaint!!.strokeWidth = 10f
        mPaint!!.isAntiAlias = true
        for (i in 0..8) {
            positionArr.add(PointF())
        }
        //点击的点的属性
        mClickPaint = Paint()
        mClickPaint!!.color = Color.GRAY
        mClickPaint!!.style = Paint.Style.STROKE
        mClickPaint!!.strokeWidth = 30f
        mClickPaint!!.isAntiAlias = true
        //点击的点和触摸点之间的连线
        mLinePaint = Paint()
        mLinePaint!!.color = Color.YELLOW
        mLinePaint!!.isAntiAlias = true
        mLinePaint!!.strokeJoin = Paint.Join.ROUND
        mLinePaint!!.strokeCap = Paint.Cap.ROUND
    }

    /**
     * 9等分屏幕
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        //锁屏3x3点矩阵
        widthArea = (width / 3).toFloat()
        heightArea = (height / 3).toFloat()
        CIRCLE_RADIUS = widthArea / 4//设置半径
        mLinePaint!!.strokeWidth = CIRCLE_RADIUS / 2//设置划线的宽
        //根据屏幕设置9个坐标点
        a[0][0] = widthArea / 2
        a[1][0] = widthArea * 2f * 0.75f
        a[2][0] = widthArea * 3f * 5f / 6
        a[3][0] = widthArea / 2
        a[4][0] = widthArea * 2f * 0.75f
        a[5][0] = widthArea * 3f * 5f / 6
        a[6][0] = widthArea / 2
        a[7][0] = widthArea * 2f * 0.75f
        a[8][0] = widthArea * 3f * 5f / 6
        a[0][1] = heightArea / 2
        a[1][1] = heightArea / 2
        a[2][1] = heightArea / 2
        a[3][1] = heightArea * 2f * 0.75f
        a[4][1] = heightArea * 2f * 0.75f
        a[5][1] = heightArea * 2f * 0.75f
        a[6][1] = heightArea * 3f * 5f / 6
        a[7][1] = heightArea * 3f * 5f / 6
        a[8][1] = heightArea * 3f * 5f / 6
        for (i in positionArr.indices) {
            positionArr[i].x = a[i][0]
            positionArr[i].y = a[i][1]
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        //画出已连接的点
        positionArr.indices.forEach { i ->
            if (curCircle.contains(i)) {
                canvas.drawCircle(positionArr[i].x, positionArr[i].y, CIRCLE_RADIUS, mClickPaint!!)
            } else {
                canvas.drawCircle(positionArr[i].x, positionArr[i].y, CIRCLE_RADIUS, mPaint!!)
            }
        }
        //已连接的点之间的线
        curCircle.indices
                .filter { it + 1 < curCircle.size }
                .forEach { canvas.drawLine(positionArr[curCircle[it]].x, positionArr[curCircle[it]].y, positionArr[curCircle[it + 1]].x, positionArr[curCircle[it + 1]].y, mLinePaint!!) }
        //最后的点与触摸位置之间的线
        if (moveX != 0f && moveY != 0f && curCircle.size > 0 && curCircle.size < 9 && isTouch) {
            canvas.drawLine(positionArr[curCircle[curCircle.size - 1]].x, positionArr[curCircle[curCircle.size - 1]].y, moveX, moveY, mLinePaint!!)
        }
    }

    /**
     * 根据触摸区域判断触摸点并存入数组
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_UP -> checkPassword()
            MotionEvent.ACTION_MOVE -> {
                moveX = event.x
                moveY = event.y
                //判断触摸点下标
                val pos = whichCircle(event.x, event.y)
                //存入数组
                if (pos != -1 && !curCircle.contains(pos)) {
                    curCircle.add(pos)
                }
                postInvalidate()
            }
        }
        return true
    }

    /**
     * 检查密码是否正确
     */
    private fun checkPassword() {
        val pwd = StringBuilder()
        for (pos in curCircle) {
            pwd.append(pos).append("")
        }
        //连接点数小于等于2则忽略此次连接
        if (curCircle.size > 2) {
            listener!!.checkListener(password.contentEquals(pwd.toString()))
            if (!password.contentEquals(pwd.toString())) {
                mLinePaint!!.color = Color.RED
                mClickPaint!!.color = Color.RED
                postInvalidate()
            }
            isTouch = false
            postDelayed({ reset() }, 1000)
        } else {
            reset()
        }
    }

    /**
     * 重置锁屏页
     */
    private fun reset() {
        mLinePaint!!.color = Color.YELLOW
        mClickPaint!!.color = Color.GRAY
        curCircle.clear()
        postInvalidate()
        isTouch = true
    }

    /**
     * 判断触摸的是哪一个点
     */
    private fun whichCircle(x: Float, y: Float): Int {
        return positionArr.indices.lastOrNull { Math.abs(x - positionArr[it].x) < CIRCLE_RADIUS && Math.abs(y - positionArr[it].y) < CIRCLE_RADIUS } ?: -1
    }

    /**
     * 将密码回调，在activity里判断密码是否正确
     */
    interface OnDialogListener {
        fun checkListener(isRight: Boolean)
    }
}
