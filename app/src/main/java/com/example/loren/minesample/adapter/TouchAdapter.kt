package com.example.loren.minesample.adapter

import com.example.loren.minesample.R
import com.example.loren.minesample.base.ext.CommonAdapter
import com.example.loren.minesample.base.ext.Holder
import kotlinx.android.synthetic.main.item_touch_list.*

/**
 * Copyright © 2018/2/11 by loren
 */
class TouchAdapter(val data: MutableList<String>) : CommonAdapter() {

    override fun bindLayout() = R.layout.item_touch_list

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.item_tv.text = data[position]
    }
}

//class CustomTouchHelp(val mAdapter: TouchAdapter) : ItemTouchHelper.Callback() {
//
//    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
//
//    }
//
//    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
//    }
//
//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
//    }
//
//}