package com.example.loren.minesample.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.example.loren.minesample.R
import com.example.loren.minesample.annotation.AnimationClickType
import com.example.loren.minesample.annotation.DynamicOnClickListener

/**
 * Copyright © 05/01/2018 by loren
 */
class KeyBoardView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs), View.OnClickListener {

    val KB_NUM = 0
    val KB_CONFIRM = 1
    val KB_CANCEL = 2
    private var inputResult = StringBuilder()
    var onKBEvent: ((str: String, type: Int) -> Unit)? = null
    private val viewIds = arrayListOf(R.id.zero_tv, R.id.one_tv, R.id.two_tv, R.id.three_tv, R.id.four_tv, R.id.five_tv, R.id.six_tv, R.id.seven_tv, R.id.eight_tv, R.id.nine_tv)
    private var view: View? = null

    init {
        initView()
    }

    //设置顺序为零键到九键
    fun setKeyBoardText(contentList: List<String>) {
        view?.let {
            contentList.forEachIndexed { index, s ->
                it.findViewById<TextView>(viewIds[index]).text = s
            }
        }
    }

    private fun initView() {
        view = View.inflate(context, R.layout.key_board_view, null)
        setKBListener(view!!)
        addView(view)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.confirm_ll -> onKBEvent?.invoke("", KB_CONFIRM)
            R.id.cancel_ll -> {
                inputResult.deleteCharAt(inputResult.lastIndex)
                onKBEvent?.invoke(inputResult.toString(), KB_CANCEL)
            }
            else -> {
                inputResult.append((v as TextView).text)
                onKBEvent?.invoke(inputResult.toString(), KB_NUM)
            }
        }
    }

    private fun setKBListener(view: View) {
        val listener = DynamicOnClickListener(this, AnimationClickType.ALPHA)
        viewIds.forEachIndexed { _, id -> view.findViewById<TextView>(id).setOnClickListener(listener) }
        view.findViewById<LinearLayout>(R.id.cancel_ll).setOnClickListener(listener)
        view.findViewById<LinearLayout>(R.id.confirm_ll).setOnClickListener(listener)
    }
}