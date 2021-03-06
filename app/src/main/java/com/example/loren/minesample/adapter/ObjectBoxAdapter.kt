package com.example.loren.minesample.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.loren.minesample.R
import com.example.loren.minesample.entity.User
import kotlinx.android.synthetic.main.item_test.view.*
import pers.victor.ext.findColor
import pers.victor.ext.inflate

/**
 * Copyright © 10/11/2017 by loren
 */
class ObjectBoxAdapter(val list: MutableList<User>) : RecyclerView.Adapter<ObjectBoxAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(inflate(R.layout.item_test, parent, false))

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.test_tv.text = list[position].name
        if (position in 28..31) {
            holder.itemView.setBackgroundColor(findColor(R.color.transparent))
        } else {
            holder.itemView.setBackgroundColor(findColor(R.color.red))
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}