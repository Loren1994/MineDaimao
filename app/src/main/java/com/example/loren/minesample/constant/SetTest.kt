package com.example.loren.minesample.constant

import android.support.annotation.IntDef

/**
 * Copyright © 2019-05-24 by loren
 */
object SetTest {
    const val MAN = 1
    const val WOMAN = 0

    @Sex
    private var sex = MAN

    fun setSex(@Sex sex: Int) {
        this.sex = sex
    }

    @IntDef(MAN, WOMAN)
    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
    @Retention(AnnotationRetention.SOURCE)
    internal annotation class Sex
}
