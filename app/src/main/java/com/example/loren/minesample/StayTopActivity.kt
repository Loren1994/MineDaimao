package com.example.loren.minesample

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.loren.minesample.adapter.Entity
import com.example.loren.minesample.adapter.HeaderItemDecoration
import com.example.loren.minesample.adapter.TopListAdapter
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.stay_top_activity.*
import pers.victor.ext.toast

/**
 * Copyright © 2018/2/1 by loren
 */
class StayTopActivity : BaseActivity() {

    private lateinit var headerItemDecoration: HeaderItemDecoration
    private var isMove = false

    override fun initWidgets() {
        val data = arrayListOf<Entity>()
        repeat(50) {
            data.add(Entity("ITEM - $it", "${(it % 10)}"))
        }
        data.sortBy { it.getTitle() }
        top_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        headerItemDecoration = HeaderItemDecoration(data, {
            slide_view.setCurrentCheck(it)
        })
        top_rv.addItemDecoration(headerItemDecoration)
        top_rv.adapter = TopListAdapter(data)
        slide_view.setData(headerItemDecoration.indexMap.keys.sortedBy { it }.toMutableList())
        slide_view.onSlideItemListener = { index, text ->
            toast("$index - $text")
            val firstPos = (top_rv.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findFirstVisibleItemPosition()
            val lastPos = (top_rv.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findLastVisibleItemPosition()
            val curPos = headerItemDecoration.indexMap[text]!!
            val curChild = (top_rv.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findViewByPosition(curPos)
            when {
                curPos >= lastPos -> {
                    top_rv.scrollToPosition(curPos)
                    isMove = true
                }
                curPos <= firstPos -> top_rv.scrollToPosition(curPos)
                else -> top_rv.scrollBy(0, curChild!!.top)
            }
        }
        top_rv.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isMove && dy == 0) {
                    isMove = false
                    val lastPos = (recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findLastVisibleItemPosition()
                    val lastChild = (recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findViewByPosition(lastPos)
                    recyclerView.scrollBy(0, lastChild!!.top)
                }
            }
        })
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.stay_top_activity
}