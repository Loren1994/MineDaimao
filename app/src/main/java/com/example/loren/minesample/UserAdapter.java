//package com.example.loren.minesample;
//
//import android.content.Context;
//import android.databinding.DataBindingUtil;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.loren.minesample.databinding.ItemUserBinding;
//
//import java.util.ArrayList;
//
//import bean.User;
//
///**
// * Copyright (c) 16-9-8 by loren
// */
//
//class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
//
//    private ArrayList<User> data;
//    private Context mContext;
//
//    UserAdapter(Context mContext, ArrayList<User> data) {
//        this.mContext = mContext;
//        this.data = data;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        ItemUserBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater
//                .from(mContext), R.layout.item_user, parent, false);
//        ViewHolder holder = new ViewHolder(viewDataBinding.getRoot());
//        holder.setViewDataBinding(viewDataBinding);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, final int position) {
//        if (position % 2 == 0) {
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.purple));
//        } else {
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey));
//        }
//        User user = data.get(position);
//        holder.getViewDataBinding().setVariable(BR.itemUser, user);
//        holder.getViewDataBinding().executePendingBindings();
//        //listener
//        holder.getViewDataBinding().itemName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, "click " + ((TextView) v).getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return data.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        private ItemUserBinding viewDataBinding;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//        }
//
//        ItemUserBinding getViewDataBinding() {
//            return viewDataBinding;
//        }
//
//        void setViewDataBinding(ItemUserBinding viewDataBinding) {
//            this.viewDataBinding = viewDataBinding;
//        }
//    }
//}
