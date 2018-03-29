package com.example.loren.minesample

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.example.loren.minesample.adapter.TTTouchAdapter
import com.example.loren.minesample.adapter.TTouchHelp
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.entity.TTouchBean
import kotlinx.android.synthetic.main.tt_touch_activity.*

/**
 * Copyright © 2018/3/29 by loren
 */
class TTouchActivity : BaseActivity() {
    private var data: MutableList<TTouchBean> = arrayListOf()
    private lateinit var mAdapter: TTTouchAdapter

    override fun initWidgets() {
        repeat(40) {
            var type = 2
            if (it < 15) {
                type = 0
            }
            if (it > 15) {
                type = 1
            }
            data.add(TTouchBean("ITEM$it", type))
        }
        mAdapter = TTTouchAdapter(data)
        val layoutManager = GridLayoutManager(this, 4)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mAdapter.isTitle(position)) 4 else 1
            }
        }
        news_rv.layoutManager = layoutManager
        news_rv.adapter = mAdapter
        val touchHelper = ItemTouchHelper(TTouchHelp(mAdapter))
        touchHelper.attachToRecyclerView(news_rv)
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.tt_touch_activity
}