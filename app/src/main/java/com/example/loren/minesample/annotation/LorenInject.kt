package com.example.loren.minesample.annotation

import android.view.View
import com.example.loren.minesample.widget.ClickRecyclerView

/**
 * Copyright © 22/11/2017 by loren
 */
object LorenInject {
    fun into(obj: Any) {
        val clazz = obj.javaClass
        val findMethod = clazz.getMethod("findViewById", Int::class.java)
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
                val listener = DynamicOnClickListener(obj as View.OnClickListener, annValue.animType)
                findMethod.isAccessible = true
                viewIds.forEach {
                    val itemView = findMethod.invoke(obj, it) as View
                    val setListenerMethod = itemView.javaClass.getMethod(setter, targetType.java)
                    setListenerMethod.isAccessible = true
                    setListenerMethod.invoke(itemView, listener)
                }
            }
            //itemClick
            if (it.isAnnotationPresent(AnimationItemClick::class.java)) {
                val annValue = it.getAnnotation(AnimationItemClick::class.java)
                val recyclerViewId = annValue.clickRecyclerViewId
                val targetType = ClickRecyclerView.OnItemClickListener::class
                val setter = "setOnItemClickListener"
                val listener = DynamicOnItemClickListener(obj as ClickRecyclerView.OnItemClickListener, annValue.animType)
                val recyclerView = findMethod.invoke(obj, recyclerViewId) as ClickRecyclerView
                val setListenerMethod = recyclerView.javaClass.getMethod(setter, targetType.java)
                setListenerMethod.isAccessible = true
                setListenerMethod.invoke(recyclerView, listener)
            }
        }

    }
}