package com.example.loren.minesample.annotation

import androidx.annotation.IdRes
import android.view.View

/**
 * Copyright © 23/11/2017 by loren
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class AnimationClick(@IdRes val viewIds: IntArray = intArrayOf(View.NO_ID), val animType: AnimationClickType = AnimationClickType.ALPHA)

enum class AnimationClickType {
    ALPHA,
    SCALE
}