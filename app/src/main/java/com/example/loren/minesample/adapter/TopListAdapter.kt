package com.example.loren.minesample.adapter

import com.example.loren.minesample.R
import com.example.loren.minesample.base.ext.CommonAdapter
import com.example.loren.minesample.base.ext.Holder
import kotlinx.android.synthetic.main.top_list_item.*

/**
 * Copyright © 2018/2/2 by loren
 */
class TopListAdapter(val data: List<Entity>) : CommonAdapter() {

    override fun bindLayout() = R.layout.top_list_item

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.item_tv.text = data[position].age.toString()
    }
}

