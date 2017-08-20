package com.example.loren.minesample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.facebook.drawee.view.SimpleDraweeView

import java.util.ArrayList

import bean.MessageBean
import kotlinx.android.synthetic.main.item_chat_list_other.view.*

/**
 * Copyright (c) 17-1-16 by loren
 */

class ChatAdapter(private val mContext: Context, private val data: ArrayList<MessageBean.DataBean>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_chat_list_other, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
