package com.example.loren.minesample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import bean.MessageBean;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 17-1-16 by loren
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MessageBean.DataBean> data;

    public ChatAdapter(Context mContext, ArrayList<MessageBean.DataBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_chat_list_other, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (data.get(position).getType().equals("1")) {//self
            holder.otherLl.setVisibility(View.GONE);
            holder.selfLl.setVisibility(View.VISIBLE);
            holder.selfMsgTv.setText(data.get(position).getMsg());
            holder.selfHeadSd.setImageURI(data.get(position).getUrl());
            holder.selfNameTv.setText(data.get(position).getName());
        } else {//other
            holder.selfLl.setVisibility(View.GONE);
            holder.otherLl.setVisibility(View.VISIBLE);
            holder.otherHeadSd.setImageURI(data.get(position).getUrl());
            holder.otherMsgTv.setText(data.get(position).getMsg());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.self_name_tv)
        TextView selfNameTv;
        @BindView(R.id.self_head_sd)
        SimpleDraweeView selfHeadSd;
        @BindView(R.id.self_msg_tv)
        TextView selfMsgTv;
        @BindView(R.id.other_ll)
        LinearLayout otherLl;
        @BindView(R.id.other_msg_tv)
        TextView otherMsgTv;
        @BindView(R.id.other_head_sd)
        SimpleDraweeView otherHeadSd;
        @BindView(R.id.self_ll)
        LinearLayout selfLl;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
