package com.example.loren.minesample.adapter

import android.content.Context
import android.view.View
import bean.MessageBean
import com.example.loren.minesample.R
import com.example.loren.minesample.base.ext.CommonAdapter
import com.example.loren.minesample.base.ext.Holder
import kotlinx.android.synthetic.main.item_chat_list_other.view.*
import java.util.*

/**
 * Copyright (c) 17-1-16 by loren
 */

class ChatAdapter(private val mContext: Context, private val data: ArrayList<MessageBean.DataBean>) : CommonAdapter() {
    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if (data[position].type == "1") {//self
            holder.itemView.other_ll!!.visibility = View.GONE
            holder.itemView.self_ll!!.visibility = View.VISIBLE
            holder.itemView.self_msg_tv!!.text = data[position].msg
            holder.itemView.self_head_sd!!.setImageURI(data[position].url)
            holder.itemView.self_name_tv!!.text = data[position].name
        } else {//other
            holder.itemView.self_ll!!.visibility = View.GONE
            holder.itemView.other_ll!!.visibility = View.VISIBLE
            holder.itemView.other_head_sd!!.setImageURI(data[position].url)
            holder.itemView.other_msg_tv!!.text = data[position].msg
        }
    }

    override fun bindLayout() = R.layout.item_chat_list_other

}
