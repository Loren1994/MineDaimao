package com.example.loren.minesample

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.test_layout.*

/**
 * Copyright © 10/11/2017 by loren
 */
class TestActivity : BaseActivity() {
    override fun initWidgets() {
        val list = arrayListOf<String>()
        val test = arrayListOf<String>()
        repeat(20) { i ->
            list.add("$i - item")
            if (i == 3) {
                test.add("test")
            }else{
                test.add("$i - item")
            }
        }
        val adapter = TestAdapter(list)
        test_rv.layoutManager = GridLayoutManager(this, 1)
        test_rv.adapter = adapter
        click_tv.setOnClickListener {
            list.clear()
            list.addAll(test)
            adapter.notifyItemChanged(3)
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.test_layout
}