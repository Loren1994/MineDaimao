package com.example.loren.minesample.base.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet
import com.example.loren.minesample.R

/**
 * Copyright © 2017/9/12 by loren
 */

class RoundImageView(context: Context, attrs: AttributeSet?) : AppCompatImageView(context, attrs) {
    private var radius = 0f
    private lateinit var path: Path
    private lateinit var rectF: RectF

    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView)
        radius = typedArray.getDimension(R.styleable.RoundImageView_img_radius, 0f)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path = Path()
        val rect = Rect(0, 0, width, height)
        rectF = RectF(rect)
    }


    override fun onDraw(canvas: Canvas) {
        path.addRoundRect(rectF, radius, radius, Path.Direction.CCW)
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

}
