package com.example.loren.minesample

/**
 * Copyright © 22/11/2017 by loren
 */
object LorenInject {
    fun into(obj: Any) {
        val clazz = obj.javaClass
        clazz.declaredFields.forEach {
            if (it.isAnnotationPresent(LorenAnn::class.java)) {
                val values = it.getAnnotation(LorenAnn::class.java)
                it.isAccessible = true
                it.set(obj, values.name + " - " + values.age)
            }
        }
    }
}