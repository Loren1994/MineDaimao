package com.example.loren.minesample.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.loren.minesample.R
import com.example.loren.minesample.entity.WeatherBean
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.blur_list_header.*
import kotlinx.android.synthetic.main.blur_list_item.*
import pers.victor.ext.getStatusBarHeight
import pers.victor.ext.getVirNavBarHeight
import pers.victor.ext.inflate
import pers.victor.ext.screenHeight

/**
 * Copyright © 03/01/2018 by loren
 */
class BlurListAdapter(val data: MutableList<WeatherBean.Data.Forecast>) : RecyclerView.Adapter<BlurListAdapter.ViewHolder>() {

    private val HEADER = 0
    private val ITEM = 1

    override fun getItemCount() = data.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.itemViewType == ITEM) {
            holder.name_tv.text = "${data[position].low.split(" ")[1]} ~ ${data[position].high.split(" ")[1]}"
            holder.date_tv.text = data[position].date
            holder.wind_tv.text = data[position].fengxiang + data[position].fengli.split("[")[2].replace("]]>", "")
            holder.type_tv.text = data[position].type
        } else {
            val params = holder.itemView.layoutParams
            params.height = screenHeight - getVirNavBarHeight()
            holder.itemView.layoutParams = params
            holder.temp_tv.text = "${data[position].low.split(" ")[1]} ~ ${data[position].high.split(" ")[1]}"
            holder.head_wind_tv.text = data[position].fengxiang + data[position].fengli.split("[")[2].replace("]]>", "")
            holder.head_type_tv.text = data[position].type
            holder.healthy_tv.text = data[position].date
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