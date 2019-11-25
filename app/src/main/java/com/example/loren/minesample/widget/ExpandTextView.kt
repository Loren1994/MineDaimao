package com.example.loren.minesample.widget

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import com.example.loren.minesample.base.ext.log
import pers.victor.ext.dp2px
import pers.victor.ext.screenWidth

/**
 * Copyright © 2018/3/14 by loren
 */
class ExpandTextLayout(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    private var isExpand = false // false:收回状态 || true:展开状态
    private lateinit var contentTv: TextView
    private lateinit var expandTv: TextView
    var VIEW_WIDTH = screenWidth
    var MAX_LINE = 2
    var CONTENT = "党的上级组织要经常听取下级组织和党员群众的意见，及时解决他们提出的问题。党的下级组织既要向上级组织请示和报告工作，又要独立负责地解决自己职责范围内的问题。上下级组织之间要互通情报、互相支持和互相监督。党的各级组织要按规定实行党务公开，使党员对党内事务有更多的了解和参与。"
    var EXPAND_TEXT = "全文"
    var SHRINK_TEXT = "收回"

    init {
        init()
    }

    private fun init() {
        gravity = Gravity.CENTER_VERTICAL
        contentTv = TextView(context)
        contentTv.textSize = 20f
        contentTv.text = CONTENT
        contentTv.maxLines = MAX_LINE
        contentTv.ellipsize = TextUtils.TruncateAt.END
        contentTv.setTextColor(Color.WHITE)
        contentTv.includeFontPadding = false //不设置会导致textView居中但文字不居中
        contentTv.gravity = Gravity.CENTER_VERTICAL
        expandTv = TextView(context)
        expandTv.text = EXPAND_TEXT
        expandTv.textSize = 18f
        expandTv.gravity = Gravity.CENTER_VERTICAL
        expandTv.includeFontPadding = false
        expandTv.setPadding(0, dp2px(5), 0, 0)
        setExpandListener()
        addView(contentTv)
        addView(expandTv)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        refreshHeight()
    }

    private fun refreshHeight() {
        val contentHeight = computeLineCount()
        setMeasuredDimension(VIEW_WIDTH, paddingTop + paddingBottom + contentHeight
                + (if (indexOfChild(expandTv) != -1) expandTv.measuredHeight + expandTv.paddingTop + expandTv.paddingBottom else 0))
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        contentTv.layout(paddingStart, paddingTop, paddingStart + contentTv.measuredWidth, paddingTop + contentTv.measuredHeight)
        if (indexOfChild(expandTv) != -1) {
            expandTv.layout(paddingStart, paddingTop + contentTv.measuredHeight, paddingStart + expandTv.measuredWidth, paddingTop + contentTv.measuredHeight + expandTv.measuredHeight)
        }
    }

    private fun computeLineCount(): Int {
        val staticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder.obtain(contentTv.text, 0, contentTv.text.length, contentTv.paint, VIEW_WIDTH - paddingStart - paddingEnd)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .setLineSpacing(contentTv.lineSpacingExtra, contentTv.lineSpacingMultiplier)
                    .setIncludePad(contentTv.includeFontPadding)
                    .build()
        } else {
            StaticLayout(contentTv.text, contentTv.paint, VIEW_WIDTH - paddingStart - paddingEnd, Layout.Alignment.ALIGN_NORMAL, contentTv.lineSpacingMultiplier, contentTv.lineSpacingExtra, contentTv.includeFontPadding)
        }
        var cHeight = staticLayout.height
        if (MAX_LINE < staticLayout.lineCount) {
//            cHeight = staticLayout.height / staticLayout.lineCount * MAX_LINE
            cHeight = (contentTv.paint.fontMetrics.bottom - contentTv.paint.fontMetrics.top).toInt() * MAX_LINE
        } else {
            removeView(expandTv)
        }
        if (isExpand) {
//            cHeight = staticLayout.height
            cHeight = 8 * (contentTv.paint.fontMetrics.bottom - contentTv.paint.fontMetrics.top).toInt()
        }
        log("cHeight:${cHeight} - tvHeight:${contentTv.measuredHeight} " +
                " font:${8 * (contentTv.paint.fontMetrics.bottom - contentTv.paint.fontMetrics.top).toInt()}" +
                " viewMode:${MeasureSpec.getMode(contentTv.measuredHeight) == MeasureSpec.UNSPECIFIED}")
        return cHeight
    }

    private fun setExpandListener() {
        expandTv.setOnClickListener {
            isExpand = !isExpand
            expandTv.text = if (isExpand) SHRINK_TEXT else EXPAND_TEXT
            contentTv.maxLines = if (isExpand) Integer.MAX_VALUE else MAX_LINE
        }
    }
}