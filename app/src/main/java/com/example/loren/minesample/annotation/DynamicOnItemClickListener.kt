package com.example.loren.minesample.annotation

import android.support.v7.widget.RecyclerView
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.loren.minesample.R
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.widget.ClickRecyclerView

/**
 * Copyright © 24/11/2017 by loren
 */
class DynamicOnItemClickListener(var onClickListener: ClickRecyclerView.OnItemClickListener, var type: AnimationItemClickType) : ClickRecyclerView.OnItemClickListener by onClickListener {
    override fun onItemClick(v: RecyclerView.ViewHolder, position: Int) {
        val animations: Array<Animation> = when (type) {
            AnimationItemClickType.ALPHA ->
                arrayOf(AnimationUtils.loadAnimation(v.itemView.context, R.anim.anim_alpha_100_50),
                        AnimationUtils.loadAnimation(v.itemView.context, R.anim.anim_alpha_50_100))
            AnimationItemClickType.SCALE ->
                arrayOf(AnimationUtils.loadAnimation(v.itemView.context, R.anim.anim_scale_100_95),
                        AnimationUtils.loadAnimation(v.itemView.context, R.anim.anim_scale_95_100))
        }
        animations[0].setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                v.itemView.startAnimation(animations[1])
            }

            override fun onAnimationStart(p0: Animation?) {
                v.itemView.isClickable = false
            }

        })
        animations[1].setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                v.itemView.isClickable = true
                log("onAnimationEnd click")
                onClickListener.onItemClick(v, position)
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
        animations[1].fillAfter = false
        v.itemView.startAnimation(animations[0])
    }
}