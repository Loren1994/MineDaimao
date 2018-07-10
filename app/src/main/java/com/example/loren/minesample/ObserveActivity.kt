package com.example.loren.minesample

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.observe.DataObservable
import com.example.loren.minesample.observe.DataObserve
import kotlinx.android.synthetic.main.observe_activity.*

/**
 * Copyright © 2018/7/9 by loren
 * 观察者模式描述了一对多的关系
 */
class ObserveActivity : BaseActivity() {

    private val observable = DataObservable()
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            observable.data = p0.toString()
        }

    }

    override fun initWidgets() {
        setTitleBarText("双向绑定")
        observable.addObserver(DataObserve { result_tv.text = it })
        input_edt.addTextChangedListener(textWatcher)
    }


    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.observe_activity
}