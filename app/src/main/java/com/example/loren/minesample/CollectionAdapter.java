package com.example.loren.minesample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.loren.minesample.databinding.RvCollectionListItemBinding;

import java.util.ArrayList;

import bean.CollectionBean;

/**
 * Copyright (c) 16-9-9 by loren
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private ArrayList<CollectionBean.ResultEntity.FavoritesListEntity.GoodsCommonlistEntity> data;
    private Context mContext;

    CollectionAdapter(Context mContext, ArrayList<CollectionBean.ResultEntity.FavoritesListEntity.GoodsCommonlistEntity> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RvCollectionListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.rv_collection_list_item, parent, false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CollectionBean.ResultEntity.FavoritesListEntity.GoodsCommonlistEntity goodsCommonlistEntity = data.get(position);
        holder.getBinding().setVariable(BR.goodsList, goodsCommonlistEntity);
        holder.getBinding().executePendingBindings();
        holder.getBinding().ivCollectionGoodsPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageUtil.bind((ImageView) v, "http://img0.imgtn.bdimg.com/it/u=821360456,2131198210&fm=21&gp=0.jpg");
                holder.getBinding().getGoodsList().setGoods_image_url("http://img0.imgtn.bdimg.com/it/u=821360456,2131198210&fm=21&gp=0.jpg");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RvCollectionListItemBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
        }

        RvCollectionListItemBinding getBinding() {
            return binding;
        }

        void setBinding(RvCollectionListItemBinding binding) {
            this.binding = binding;
        }

    }
}
