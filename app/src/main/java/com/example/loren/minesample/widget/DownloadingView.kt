package com.example.loren.minesample.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.MeasureSpec.AT_MOST
import com.example.loren.minesample.base.ext.log
import pers.victor.ext.dp2px
import pers.victor.ext.sp2px

/**
 * Copyright © 08/01/2018 by loren
 */
class DownloadingView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var bgPaint: Paint = Paint()
    private var bgRect = RectF()
    private var textRect = Rect()
    private var textPaint = Paint()
    private var proPaint = Paint()
    private var proCanvas = Canvas()
    private var RADIUS = 0f
    private var DEFAULT_HEIGHT = dp2px(40)
    private var text = ""
    private var curProgress = 0f
    private var maxProgress = 100f
    private val loadingColor = Color.YELLOW //进度条颜色和初始文字颜色
    private val initColor = Color.GRAY //初始背景颜色
    private val initTextColor = Color.BLUE //进度条中的文字颜色
    private lateinit var bgBitmap: Bitmap
    private lateinit var proShader: BitmapShader

    init {
        init()
    }

    private fun init() {
        bgPaint.isAntiAlias = true
        bgPaint.color = initColor
        bgPaint.style = Paint.Style.FILL
        textPaint.isAntiAlias = true
        textPaint.color = loadingColor
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = sp2px(14).toFloat()
        proPaint.isAntiAlias = true
        proPaint.color = loadingColor
        text = "开始下载"
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mWidth = MeasureSpec.getSize(widthMeasureSpec)
        var viewHeight = MeasureSpec.getSize(heightMeasureSpec)
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        RADIUS = mWidth / 2f
        if (mode == AT_MOST) {
            viewHeight = DEFAULT_HEIGHT
        }
        setMeasuredDimension(mWidth, viewHeight)
        initView()
    }

    private fun initView() {
        bgRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
        bgBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
        proCanvas = Canvas(bgBitmap)
    }

    private fun drawProgress(canvas: Canvas) {
        val right = (curProgress / maxProgress) * measuredWidth.toFloat()
        proCanvas.save()
        proCanvas.clipRect(0f, 0f, right, measuredHeight.toFloat())
        proCanvas.drawColor(loadingColor)
        proCanvas.restore()
        //设置显示区域(平铺:边缘拉伸)
        proShader = BitmapShader(bgBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        proPaint.shader = proShader
        canvas.drawRoundRect(bgRect, RADIUS, RADIUS, proPaint)
    }

    private fun drawTextColor(canvas: Canvas) {
        textPaint.color = initTextColor
        val right = (curProgress / maxProgress) * measuredWidth.toFloat()
        val tW = textRect.width()
        val starW = (measuredWidth - tW) / 2
        val baseline = measuredHeight / 2 + (textPaint.fontMetrics.descent - textPaint.fontMetrics.ascent) / 2 - textPaint.fontMetrics.descent
        if (right > starW) {
            canvas.save()
            canvas.clipRect(0f, 0f, right, measuredHeight.toFloat())
            canvas.drawText(getProgressText(), (measuredWidth - tW) / 2.0f, baseline, textPaint)
            canvas.restore()
        }
    }

    private fun drawText(canvas: Canvas) {
        textPaint.color = loadingColor
        textPaint.getTextBounds(getProgressText(), 0, text.length, textRect)
        val tW = textRect.width()
        val baseline = measuredHeight / 2 + (textPaint.fontMetrics.descent - textPaint.fontMetrics.ascent) / 2 - textPaint.fontMetrics.descent
        //*注:drawText时,y的基准线为baseline,x的基准线为中心点
        canvas.drawText(getProgressText(), (measuredWidth - tW) / 2.0f, baseline, textPaint)
    }

    private fun drawBackground(canvas: Canvas) {
        bgPaint.color = initColor
        canvas.drawRoundRect(bgRect, RADIUS, RADIUS, bgPaint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBackground(canvas)
        drawText(canvas)
        drawProgress(canvas)
        drawTextColor(canvas)
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP) {
            log(">>>>>>>>点击暂停")
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setProgress(progress: Float) {
        curProgress = progress
        postInvalidate()
    }

    fun resetDownload() {
        curProgress = 0f
        text = ""
        initView()
    }

    fun isFinish(): Boolean {
        return curProgress == maxProgress
    }

    fun isDownloading(): Boolean {
        return curProgress != 0f && curProgress != maxProgress
    }

    private fun getProgressText(): String {
        text = if (curProgress > 0 && curProgress < maxProgress) {
            "正在下载 $curProgress%"
        } else if (curProgress == maxProgress) {
            "下载完成"
        } else {
            "开始下载"
        }
        return text
    }
}