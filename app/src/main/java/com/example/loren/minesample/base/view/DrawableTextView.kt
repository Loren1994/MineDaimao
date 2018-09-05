package com.example.loren.minesample.base.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.example.loren.minesample.R

/**
 * Created by fionera on 15-12-7
 */
class DrawableTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    companion object {
        private val DRAWABLE_START = 0
        private val DRAWABLE_TOP = 1
        private val DRAWABLE_END = 2
        private val DRAWABLE_BOTTOM = 3
    }

    private var startHeight = 0
    private var startWidth = 0
    private var endHeight = 0
    private var endWidth = 0
    private var topHeight = 0
    private var topWidth = 0
    private var bottomHeight = 0
    private var bottomWidth = 0

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.DrawableTextView)
        startHeight = a.getDimensionPixelSize(R.styleable.DrawableTextView_start_height, 0)
        startWidth = a.getDimensionPixelSize(R.styleable.DrawableTextView_start_width, 0)
        endHeight = a.getDimensionPixelSize(R.styleable.DrawableTextView_end_height, 0)
        endWidth = a.getDimensionPixelSize(R.styleable.DrawableTextView_end_width, 0)
        topHeight = a.getDimensionPixelSize(R.styleable.DrawableTextView_top_height, 0)
        topWidth = a.getDimensionPixelSize(R.styleable.DrawableTextView_top_width, 0)
        bottomHeight = a.getDimensionPixelSize(R.styleable.DrawableTextView_bottom_height, 0)
        bottomWidth = a.getDimensionPixelSize(R.styleable.DrawableTextView_bottom_width, 0)
        val drawables = compoundDrawablesRelative
        setCompoundDrawablesRelative(drawables[0], drawables[1], drawables[2], drawables[3])
        a.recycle()
    }

    override fun setCompoundDrawablesRelative(start: Drawable?, top: Drawable?, end: Drawable?, bottom: Drawable?) {
        setImageSize(start, DRAWABLE_START)
        setImageSize(top, DRAWABLE_TOP)
        setImageSize(end, DRAWABLE_END)
        setImageSize(bottom, DRAWABLE_BOTTOM)
        super.setCompoundDrawablesRelative(start, top, end, bottom)
    }

    private fun setImageSize(d: Drawable?, dir: Int) {
        d?.let {
            var height = 0
            var width = 0
            when (dir) {
                DRAWABLE_START -> {
                    height = startHeight
                    width = startWidth
                }
                DRAWABLE_TOP -> {
                    height = topHeight
                    width = topWidth
                }
                DRAWABLE_END -> {
                    height = endHeight
                    width = endWidth
                }
                DRAWABLE_BOTTOM -> {
                    height = bottomHeight
                    width = bottomWidth
                }
            }
            d.setBounds(0, 0, width, height)
        }
    }
}
