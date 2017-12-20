package com.example.loren.minesample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.loren.minesample.R


/**
 * Created by victor on 16-8-10. (ง •̀_•́)ง
 */
class FixedFlowLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet) {
    private var columns: Int
    private var isSingleSelected: Boolean
    private var horizontalSpacing: Int
    private var verticalSpacing: Int
    private var eachWidth = 0
    private var eachHeight = 0
    private var childGravity = -1
    private var func: ((Int) -> Boolean)? = null
    private var hasSelected = false
    private var preClickPos = 0
    private var ratioHeight: Int
    private var ratioWidth: Int

    init {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.FixedFlowLayout)
        columns = typedArray.getInt(R.styleable.FixedFlowLayout_columns_per_line, 1)
        isSingleSelected = typedArray.getBoolean(R.styleable.FixedFlowLayout_single_selected, false)
        horizontalSpacing = typedArray.getDimensionPixelOffset(R.styleable.FixedFlowLayout_horizontal_spacing, 0)
        verticalSpacing = typedArray.getDimensionPixelOffset(R.styleable.FixedFlowLayout_vertical_spacing, 0)
        ratioHeight = typedArray.getInt(R.styleable.FixedFlowLayout_child_ratio_height, 0)
        ratioWidth = typedArray.getInt(R.styleable.FixedFlowLayout_child_ratio_width, 0)
        childGravity = typedArray.getInt(R.styleable.FixedFlowLayout_child_gravity, Gravity.TOP or Gravity.START)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
            return
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        eachWidth = (View.MeasureSpec.getSize(widthMeasureSpec) - paddingStart - paddingEnd - (columns - 1) * horizontalSpacing) / columns
        eachHeight = if (ratioHeight * ratioWidth != 0) {
            eachWidth * ratioHeight / ratioWidth
        } else {
            getChildAt(0).measuredHeight
        }
        var curColumn = 0
        var curRow = 1
        for (i in 0 until childCount) {
            if (curColumn == columns) {
                curColumn = 0
                curRow++
            }
            val child = getChildAt(i)
            val lp = child.layoutParams as LayoutParams
            var span = lp.columnSpan
            if (span > columns - curColumn) {
                lp.columnSpan = columns - curColumn
                span = lp.columnSpan
            }
            lp.height = eachHeight
            lp.width = span * eachWidth + (span - 1) * horizontalSpacing
            curColumn += span
            child.layoutParams = lp
        }
        val height = curRow * eachHeight + (curRow - 1) * verticalSpacing + paddingTop + paddingBottom
        val measuredHeightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY)
        setMeasuredDimension(widthMeasureSpec, measuredHeightSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount == 0)
            return
        var curY = paddingTop
        var curX = paddingStart
        var itemsCountEachLine = 0
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val lp = child.layoutParams as LayoutParams
            if (isSingleSelected) {
                if (!hasSelected) {
                    child.isSelected = true
                    hasSelected = true
                }
                child.setOnClickListener { it.selectMe() }
            }
            if (itemsCountEachLine == columns) {
                itemsCountEachLine = 0
                curY += eachHeight + verticalSpacing
                curX = paddingStart
            }
            if (getChildAt(i) is TextView) {
                (child as TextView).gravity = childGravity
            }
            child.layout(curX, curY, curX + lp.width, curY + eachHeight)
            curX += lp.width + horizontalSpacing
            itemsCountEachLine += lp.columnSpan
        }
    }

    private fun View.selectMe() {
        for (i in 0 until childCount) {
            if (this == getChildAt(i)) {
                preClickPos = i
                if (func == null) {
                    getChildAt(i).isSelected = true
                } else {
                    if (func!!(i)) {
                        getChildAt(i).isSelected = true
                    } else {
                        return
                    }
                }
            }
        }
        (0 until childCount)
                .filter { this != getChildAt(it) }
                .forEach { getChildAt(it).isSelected = false }
    }

    fun setColumns(columns: Int) {
        this.columns = columns
        layout(0, 0, measuredWidth, measuredHeight)
    }

    fun setOnSelectedChangeListener(func: ((Int) -> Boolean)) {
        this.func = func
    }

    fun selectChildAt(index: Int, performClick: Boolean = true) {
        if (index == preClickPos) {
            return
        }
        getChildAt(preClickPos).isSelected = false
        getChildAt(index).isSelected = true
        preClickPos = index
        if (performClick) {
            getChildAt(index).performClick()
        }
    }


    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(attrs: AttributeSet): LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): LayoutParams {
        return LayoutParams(p)
    }


    class LayoutParams : ViewGroup.LayoutParams {
        var columnSpan = 1

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs) {
            init(c, attrs)
        }

        constructor(width: Int, height: Int) : super(width, height)

        constructor(source: ViewGroup.LayoutParams) : super(source)

        private fun init(c: Context, attrs: AttributeSet) {
            val typedArray = c.obtainStyledAttributes(attrs, R.styleable.FixedFlowLayout_Layout)
            columnSpan = typedArray.getInteger(R.styleable.FixedFlowLayout_Layout_span_column, 1)
            typedArray.recycle()
        }
    }
}