package com.example.loren.minesample.base.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.example.loren.minesample.R

/**
 * Created by victor on 16-5-25. (ง •̀_•́)ง
 */

class FlexibleImageView(context: Context, attrs: AttributeSet) : ImageView(context, attrs) {
    private val mProportionHeight: Int
    private val mProportionWidth: Int

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlexibleImageView)
        mProportionHeight = typedArray.getInt(R.styleable.FlexibleImageView_proportion_height, 0)
        mProportionWidth = typedArray.getInt(R.styleable.FlexibleImageView_proportion_width, 0)
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        scaleType = ScaleType.CENTER_CROP
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (MeasureSpec.getMode(heightMeasureSpec) != MeasureSpec.EXACTLY && mProportionHeight * mProportionWidth != 0) {
            val width = MeasureSpec.getSize(widthMeasureSpec)
            val height = width * mProportionHeight / mProportionWidth
            val newWidthSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.getMode(widthMeasureSpec))
            val newHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
            setMeasuredDimension(newWidthSpec, newHeightSpec)
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        }
    }
}