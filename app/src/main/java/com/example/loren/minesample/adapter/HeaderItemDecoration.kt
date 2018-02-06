package com.example.loren.minesample.adapter

import android.graphics.*
import android.support.v7.widget.RecyclerView
import android.view.View
import pers.victor.ext.dp2px

/**
 * Copyright © 2018/2/2 by loren
 */
class HeaderItemDecoration(private val headerList: List<IndexHeader>,val onTitleChange: ((titleIndex: Int) -> Unit)) : RecyclerView.ItemDecoration() {
    private val HEADER_HEIGHT = 150f //小于等于ITEM的高度
    private val HEADER_PADDING = dp2px(8).toFloat()
    private val paint = Paint()
    private val textPaint = Paint()
    val indexMap = hashMapOf<String, Int>()
    private var titleList = arrayListOf<String>()
    private var currentTitleIndex = -1

    init {
        paint.isAntiAlias = true
        paint.color = Color.GREEN
        textPaint.isAntiAlias = true
        textPaint.color = Color.RED
        textPaint.textSize = HEADER_HEIGHT / 3
        headerList.forEachIndexed { index, indexHeader ->
            if (!indexMap.containsKey(indexHeader.getTitle())) {
                indexMap[indexHeader.getTitle()] = index
            }
            if (!titleList.contains(indexHeader.getTitle())) {
                titleList.add(indexHeader.getTitle())
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) in indexMap.values) {
            outRect.top = HEADER_HEIGHT.toInt()
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        repeat(parent.childCount) {
            val child = parent.getChildAt(it)
            if (parent.getChildAdapterPosition(child) in indexMap.values) {
                c.drawRect(child.left.toFloat(), child.top.toFloat() - HEADER_HEIGHT, child.right.toFloat(), child.bottom.toFloat() - child.measuredHeight, paint)
                val baseline = (child.bottom.toFloat() - child.measuredHeight + child.top - HEADER_HEIGHT) / 2 + (textPaint.fontMetrics.descent - textPaint.fontMetrics.ascent) / 2 - textPaint.fontMetrics.descent
                c.drawText(headerList[parent.getChildAdapterPosition(child)].getTitle(), HEADER_PADDING, baseline, textPaint)
            }
        }
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val curChild = parent.getChildAt(0)
        val firstPos = parent.getChildAdapterPosition(curChild)
        val rect = RectF(0f, 0f, parent.measuredWidth.toFloat(), HEADER_HEIGHT)
        if (firstPos + 1 in indexMap.values) {
            if (curChild.bottom.toFloat() <= HEADER_HEIGHT && firstPos + 1 in indexMap.values) {
                rect.top = -(HEADER_HEIGHT - curChild.bottom.toFloat())
            }
            rect.bottom = Math.min(curChild.bottom.toFloat(), HEADER_HEIGHT)
        }
        c.drawRect(rect, paint)
        var baseline = (rect.height()) / 2 + (textPaint.fontMetrics.descent - textPaint.fontMetrics.ascent) / 2 - textPaint.fontMetrics.descent
        if (curChild.bottom.toFloat() <= HEADER_HEIGHT && firstPos + 1 in indexMap.values) {
            baseline -= (HEADER_HEIGHT - curChild.bottom.toFloat())
        }
        c.drawText(headerList[firstPos].getTitle(), HEADER_PADDING, baseline, textPaint)

        if (currentTitleIndex != titleList.indexOf(headerList[firstPos].getTitle()) || currentTitleIndex == -1) {
            currentTitleIndex = titleList.indexOf(headerList[firstPos].getTitle())
            onTitleChange.invoke(currentTitleIndex)
        }
    }
}

interface IndexHeader {
    fun getTitle(): String
}

//example
data class Entity(val age: String, val name: String) : IndexHeader {
    override fun getTitle() = name
}