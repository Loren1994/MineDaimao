package com.example.loren.minesample

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.loren.minesample.adapter.Entity
import com.example.loren.minesample.adapter.HeaderItemDecoration
import com.example.loren.minesample.adapter.TopListAdapter
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.stay_top_activity.*

/**
 * Copyright © 2018/2/1 by loren
 */
class StayTopActivity : BaseActivity() {
    override fun initWidgets() {
        val data = arrayListOf<Entity>()
        repeat(30) {
            data.add(Entity(it, "TITLE - ${(it % 4)}"))
        }
        data.sortBy { it.getTitle() }
        top_rv.layoutManager = LinearLayoutManager(this)
        top_rv.addItemDecoration(HeaderItemDecoration(data))
        top_rv.adapter = TopListAdapter(data)
        slide_view.setData(arrayListOf("A", "B", "C", "D", "E", "F", "G", "H"))
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.stay_top_activity
}