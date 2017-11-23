package com.example.loren.minesample.annotation

import android.view.View

/**
 * Copyright © 22/11/2017 by loren
 */
object LorenInject {
    fun into(obj: Any) {
        val clazz = obj.javaClass
        //field
        clazz.declaredFields.forEach {
            if (it.isAnnotationPresent(LorenAnn::class.java)) {
                val values = it.getAnnotation(LorenAnn::class.java)
                it.isAccessible = true
                it.set(obj, values.name + " - " + values.age)
            }
        }

        //method
        val methods = clazz.declaredMethods
        methods.forEach {
            if (it.isAnnotationPresent(AnimationClick::class.java)) {
                val annValue = it.getAnnotation(AnimationClick::class.java)
                val viewIds = annValue.viewIds
                val setter = "setOnClickListener"
                val targetType = View.OnClickListener::class
                val findMethod = clazz.getMethod("findViewById", Int::class.java)
                val listener = DynamicOnClickListener(obj as View.OnClickListener, annValue.animType)
//                val handler = DynamicHandler(obj)
//                val listener = Proxy.newProxyInstance(obj.javaClass.classLoader, arrayOf<Class<*>>(targetType.java), handler)
//                handler.addMethod("onClick", it)
                findMethod.isAccessible = true
                viewIds.forEach {
                    val itemView = findMethod.invoke(obj, it) as View
                    val setListenerMethod = itemView.javaClass.getMethod(setter, targetType.java)
                    setListenerMethod.isAccessible = true
                    setListenerMethod.invoke(itemView, listener)
                }
            }
        }


    }
}