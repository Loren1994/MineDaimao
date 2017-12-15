package com.example.loren.minesample.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import com.example.loren.minesample.R
import com.example.loren.minesample.base.ext.CommonAdapter
import com.example.loren.minesample.base.ext.Holder
import com.example.loren.minesample.entity.AppBean
import kotlinx.android.synthetic.main.item_app_list.*
import pers.victor.ext.date
import pers.victor.ext.no
import pers.victor.ext.yes

/**
 * Copyright © 15/12/2017 by loren
 */
class AppListAdapter(val context: Context, private val list: List<AppBean>,val onItemClick: ((pos: Int) -> Unit)) : CommonAdapter() {

    override fun bindLayout() = R.layout.item_app_list

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.app_iv.setImageDrawable(list[position].applicationInfo.loadIcon(context.packageManager))
        holder.app_name_tv.text = list[position].applicationInfo.loadLabel(context.packageManager)
        holder.app_package_tv.text = list[position].packageName
        holder.update_time_tv.text = "修改时间:${list[position].lastUpdateTime.date()}"
        holder.process_tv.text = "PID:${list[position].pid}"
        holder.process_tv.visibility = list[position].pid.isEmpty().yes { View.GONE }.no { View.VISIBLE }
        holder.itemView.setOnClickListener { onItemClick.invoke(position) }
    }

    override fun getItemCount() = list.size
}