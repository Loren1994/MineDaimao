package com.example.loren.minesample.annotation

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.loren.minesample.R


/**
 * Copyright © 23/11/2017 by loren
 */
class DynamicOnClickListener(var onClickListener: View.OnClickListener, var type: AnimationClickType) : View.OnClickListener by onClickListener {

    override fun onClick(v: View) {
        val animations: Array<Animation> = when (type) {
            AnimationClickType.ALPHA ->
                arrayOf(AnimationUtils.loadAnimation(v.context, R.anim.anim_alpha_100_50),
                        AnimationUtils.loadAnimation(v.context, R.anim.anim_alpha_50_100))
            AnimationClickType.SCALE ->
                arrayOf(AnimationUtils.loadAnimation(v.context, R.anim.anim_scale_100_95),
                        AnimationUtils.loadAnimation(v.context, R.anim.anim_scale_95_100))
        }
        animations[0].setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                v.startAnimation(animations[1])
            }

            override fun onAnimationStart(p0: Animation?) {
                v.isClickable = false
            }

        })
        animations[1].setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {
                v.isClickable = true
                onClickListener.onClick(v)
            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })
        animations[1].fillAfter = false
        v.startAnimation(animations[0])
    }
}

//class DynamicHandler(obj: Any, var type: AnimationItemClickType) : InvocationHandler {
//    private val methodMap = HashMap<String, Method>(1)
//    private val handlerRef: WeakReference<Any> = WeakReference(obj)
//
//    fun addMethod(name: String, method: Method) {
//        methodMap.put(name, method)
//    }
//
//    @Throws(Throwable::class)
//    override operator fun invoke(o: Any, method: Method?, objects: Array<Any>): Any? {
//        val handler = handlerRef.get()
//        handler?.let {
//            val methodName = method!!.name
//            methodMap[methodName]?.invoke(it, objects)
//        }
//        return null
//    }
//}

