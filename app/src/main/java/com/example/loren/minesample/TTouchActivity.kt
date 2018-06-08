package com.example.loren.minesample

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
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
    private lateinit var mAdapter: TTTouchAdapter
    private var checkData: MutableList<TTouchBean> = arrayListOf()
    private var uncheckData: MutableList<TTouchBean> = arrayListOf()

    override fun initWidgets() {
        repeat(5) {
            checkData.add(TTouchBean("选择$it", 0))
        }
        repeat(30) {
            uncheckData.add(TTouchBean("未选$it", 1))
        }
        mAdapter = TTTouchAdapter(checkData, uncheckData)
        val layoutManager = androidx.recyclerview.widget.GridLayoutManager(this, 4)
        layoutManager.spanSizeLookup = object : androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
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