package com.example.loren.minesample.base.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.loren.minesample.R
import kotlinx.android.synthetic.main.title_bar.view.*
import pers.victor.ext.dp2px
import pers.victor.ext.findColor
import pers.victor.ext.findDrawable
import pers.victor.ext.getStatusBarHeight


/**
 * Created by victor on 15-9-22. (ง •̀_•́)ง
 */
class TitleBar : FrameLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    init {
        setBackgroundColor(findColor(R.color.background))
        addView(inflate(context, R.layout.title_bar, null), LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    fun useTitleImmersive() {
        setBackgroundColor(findColor(R.color.gray_dark))
        val param = title_ll.layoutParams as LinearLayout.LayoutParams
        param.topMargin = getStatusBarHeight()
        layoutParams = param
    }

    fun setTitleBarText(title: String?) {
        title_bar_title.text = title
    }

    fun setTitleBarColor(id: Int) = title_bar_title.setTextColor(id)

    fun setLeftDrawable(id: Int) {
        title_bar_left_icon.visibility = View.VISIBLE
        title_bar_left_text.visibility = View.GONE
        title_bar_left_icon.setImageDrawable(findDrawable(id))
    }

    fun setLeftText(leftText: String) {
        title_bar_left_text.visibility = View.VISIBLE
        title_bar_left_icon.visibility = View.GONE
        title_bar_left_text.text = leftText
    }

    fun setLeftTextColor(colorId: Int) = title_bar_left_text.setTextColor(findColor(colorId))

    fun setRightTextColor(colorId: Int) = title_bar_right_text.setTextColor(findColor(colorId))

    fun setLeftTextSize(size: Float) {
        title_bar_left_text.textScaleX = size
    }

    fun setRightTextSize(size: Float) {
        title_bar_right_text.textSize = size
    }

    fun setRightDrawable(id: Int) {
        title_bar_right_icon.visibility = View.VISIBLE
        title_bar_right_text.visibility = View.GONE
        title_bar_right_icon.setImageDrawable(findDrawable(id))
    }

    fun setRightText(leftText: String) {
        title_bar_right_text.visibility = View.VISIBLE
        title_bar_right_icon.visibility = View.GONE
        title_bar_right_text.text = leftText
    }

    fun setLeftVisibility(visibility: Int) {
        title_bar_left.visibility = visibility
    }

    fun setRightVisibility(visibility: Int) = toggleRight(visibility == View.VISIBLE)

    private fun toggleRight(state: Boolean) {
        if (state) {
            title_bar_right.visibility = View.VISIBLE
        } else {
            title_bar_right.postDelayed({ title_bar_right.visibility = View.INVISIBLE }, 300)
        }
    }

    fun setTitleBarLeftClick(func: (() -> Unit)?) = title_bar_left.setOnClickListener { func?.invoke() }

    fun setTitleBarTitleClick(func: () -> Unit) = title_bar_title.setOnClickListener { func.invoke() }

    fun setTitleBarRightClick(func: (() -> Unit)?) = title_bar_right.setOnClickListener { func?.invoke() }

    fun setLeftTextAndIcon(leftText: String, iconId: Int, iconAtLeft: Boolean) {
        title_bar_left_text.text = leftText
        title_bar_left_text.visibility = View.VISIBLE
        title_bar_left_icon.visibility = View.GONE
        val drawable = findDrawable(iconId)
        drawable?.setBounds(0, 0, dp2px(18), dp2px(18))
        if (iconAtLeft) {
            title_bar_left_text.setCompoundDrawablesRelative(drawable, null, null, null)
        } else {
            title_bar_left_text.setCompoundDrawablesRelative(null, null, drawable, null)
        }
        title_bar_left_text.compoundDrawablePadding = dp2px(5)
    }

    fun setRightTextAndIcon(rightText: String, iconId: Int, iconAtLeft: Boolean) {
        title_bar_right_text.text = rightText
        title_bar_right_text.visibility = View.VISIBLE
        title_bar_right_icon.visibility = View.GONE
        val drawable = findDrawable(iconId)
        drawable?.setBounds(0, 0, dp2px(18), dp2px(18))
        if (iconAtLeft) {
            title_bar_right_text.setCompoundDrawablesRelative(drawable, null, null, null)
        } else {
            title_bar_right_text.setCompoundDrawablesRelative(null, null, drawable, null)
        }
        title_bar_right_text.compoundDrawablePadding = dp2px(5)
    }

    fun hideDivider() {
        title_bar_divider.visibility = View.GONE
    }

    fun useElevation(v: Float = 1f) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            elevation = dp2px(v).toFloat()
            hideDivider()
        }
    }
}
