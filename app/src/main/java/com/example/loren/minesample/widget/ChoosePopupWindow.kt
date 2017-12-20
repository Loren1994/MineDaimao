package com.example.loren.minesample.widget

import android.animation.ValueAnimator
import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView

import com.example.loren.minesample.R

/**
 * Copyright © 17/10/2017 by loren
 */

class ChoosePopupWindow(context: Activity, itemsOnClick: View.OnClickListener) : PopupWindow(context) {
    private val animator = ValueAnimator()

    init {
        val mMenuView = View.inflate(context, R.layout.choose_popup, null)
        val tvUrl = mMenuView.findViewById<TextView>(R.id.tv_url)
        val tvIntent = mMenuView.findViewById<TextView>(R.id.tv_intent)
        val tvCommon = mMenuView.findViewById<TextView>(R.id.tv_common)
        val btnCancel = mMenuView.findViewById<TextView>(R.id.tv_cancel)
        btnCancel.setOnClickListener { dismiss() }
        tvUrl.setOnClickListener(itemsOnClick)
        tvCommon.setOnClickListener(itemsOnClick)
        tvIntent.setOnClickListener(itemsOnClick)
        this.contentView = mMenuView
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        this.isFocusable = true
        this.animationStyle = android.R.style.Animation_InputMethod
        val dw = ColorDrawable(0)
        this.setBackgroundDrawable(dw)
        this.isOutsideTouchable = true
        val params = context.window.attributes
        animator.duration = 300
        animator.addUpdateListener {
            params.alpha = animator.animatedValue as Float
            if (animator.animatedValue as Float == 1.0f) {
                context.window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            } else {
                context.window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            }
            context.window.attributes = params
        }
        update()
    }

    override fun dismiss() {
        animator.setFloatValues(0.3f, 1f)
        animator.start()
        super.dismiss()
    }

    fun show(parent: View) {
        animator.setFloatValues(1f, 0.3f)
        animator.start()
        super.showAtLocation(parent, Gravity.BOTTOM, 0, 0)
    }
}
