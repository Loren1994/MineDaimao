package com.example.loren.minesample.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.loren.minesample.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.blur_list_header.*
import kotlinx.android.synthetic.main.blur_list_item.*
import pers.victor.ext.inflate
import pers.victor.ext.screenHeight

/**
 * Copyright © 03/01/2018 by loren
 */
class BlurListAdapter(val data: MutableList<String>) : RecyclerView.Adapter<BlurListAdapter.ViewHolder>() {

    private val HEADER = 0
    private val ITEM = 1

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == ITEM) {
            holder.name_tv.text = data[position]
        } else {
            val params = holder.header_parent.layoutParams
            params.height = (screenHeight * 0.8).toInt()
            holder.header_parent.layoutParams = params
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return if (viewType == HEADER) {
            ViewHolder(inflate(R.layout.blur_list_header, parent, false))
        } else {
            ViewHolder(inflate(R.layout.blur_list_item, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEADER
        } else {
            ITEM
        }
    }


    class ViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer
}