package com.example.loren.minesample.widget

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.loren.minesample.R
import com.example.loren.minesample.annotation.AnimationClickType
import com.example.loren.minesample.annotation.DynamicOnClickListener
import pers.victor.ext.dp2px
import pers.victor.ext.findDrawable
import pers.victor.ext.screenWidth
import java.util.*

/**
 * Copyright © 2018/1/29 by loren
 */
class SearchLayout(context: Context, attributeSet: AttributeSet) : ViewGroup(context, attributeSet), View.OnClickListener {

    private val TEXT_PADDING = dp2px(12)
    private val COLUMNS_SPACE = 20
    private val ROWS_SPACE = 20
    private val allLines = arrayListOf<ArrayList<View>>()
    private var itemHeight = 0
    var onItemListener: ((pos: Int, tv: TextView) -> Unit)? = null

    init {
        setPadding(ROWS_SPACE, COLUMNS_SPACE, ROWS_SPACE, COLUMNS_SPACE)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        allLines.clear()
        val lineViews = arrayListOf<View>()
        repeat(childCount) { item ->
            val child = getChildAt(item)
            var curLineWidth = 0
            if (!lineViews.isEmpty()) {
                lineViews.forEach {
                    curLineWidth += (it.measuredWidth + ROWS_SPACE)
                }
            }
            if (measuredWidth - paddingStart - paddingEnd - curLineWidth >= child.measuredWidth) {
                lineViews.add(child)
            } else {
                allLines.add(copyList(lineViews))
                lineViews.clear()
                lineViews.add(child)
            }
            if (item == childCount - 1 && !lineViews.isEmpty()) {
                allLines.add(copyList(lineViews))
            }
        }
        refreshHeight()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        allLines.forEachIndexed { index, item ->
            renderLineViews(item, index + 1)
        }
    }

    private fun refreshHeight() {
        var height = 0
        if (childCount > 0) {
            itemHeight = getChildAt(0).measuredHeight
            height = (allLines.size - 1) * COLUMNS_SPACE + itemHeight * allLines.size + paddingTop + paddingBottom
        }
        setMeasuredDimension(screenWidth, height)
    }

    private fun copyList(views: ArrayList<View>): ArrayList<View> {
        val temp = arrayListOf<View>()
        temp.addAll(views)
        return temp
    }

    private fun renderLineViews(views: ArrayList<View>, line: Int) {
        var left = 0
        views.forEachIndexed { index, it ->
            it.layout(paddingStart + index * ROWS_SPACE + left,
                    paddingTop + (line - 1) * COLUMNS_SPACE + (line - 1) * itemHeight,
                    paddingStart + index * ROWS_SPACE + left + it.measuredWidth,
                    paddingBottom + (line - 1) * COLUMNS_SPACE + (line - 1) * itemHeight + it.measuredHeight)
            left += it.measuredWidth
        }
    }

    fun setData(data: ArrayList<String>) {
        removeAllViews()
        data.filter { !it.isEmpty() }.forEachIndexed { index, it ->
            val tv = TextView(context)
            tv.text = it
            tv.setTextColor(Color.WHITE)
            tv.background = findDrawable(R.drawable.bg_green_coner)
            tv.gravity = Gravity.CENTER
            tv.maxLines = 1
            tv.maxWidth = screenWidth - paddingStart - paddingEnd
            tv.ellipsize = TextUtils.TruncateAt.END
            tv.setPadding(TEXT_PADDING, TEXT_PADDING / 2, TEXT_PADDING, TEXT_PADDING / 2)
            setItemListener(tv, index)
//            setItemListenerWithAnim(tv)
            addView(tv)
        }
    }

    override fun onClick(v: View) {
        onItemListener?.invoke(-1, v as TextView)
    }

    //无点击动画
    private fun setItemListener(tv: TextView, pos: Int) {
        tv.setOnClickListener { onItemListener?.invoke(pos, tv) }
    }

    //带点击动画
    private fun setItemListenerWithAnim(tv: TextView) {
        val listener = DynamicOnClickListener(this, AnimationClickType.SCALE)
        tv.setOnClickListener(listener)
    }

}