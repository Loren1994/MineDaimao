package com.example.loren.minesample

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.loren.minesample.base.ext.log
import kotlinx.android.synthetic.main.item_test.view.*
import pers.victor.ext.inflate

/**
 * Copyright © 10/11/2017 by loren
 */
class TestAdapter(val list: MutableList<String>) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            = ViewHolder(inflate(R.layout.item_test, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        log("onBindViewHolder - $position")
        holder.itemView.test_tv.text = list[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}