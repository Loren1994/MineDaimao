package com.example.loren.minesample.annotation

import android.view.View

/**
 * Copyright © 24/11/2017 by loren
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AnimationItemClick(val clickRecyclerViewId: Int = View.NO_ID, val animType: AnimationItemClickType = AnimationItemClickType.SCALE)

enum class AnimationItemClickType {
    ALPHA,
    SCALE
}