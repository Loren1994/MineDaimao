//package com.example.loren.minesample;
//
//import android.databinding.BindingAdapter;
//import android.databinding.DataBindingUtil;
//import android.os.Bundle;
//import android.support.annotation.IdRes;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.widget.ImageView;
//
//import com.example.loren.minesample.databinding.MineCollectionActivityBinding;
//import com.google.gson.Gson;
//
//import java.util.ArrayList;
//
//import bean.CollectionBean;
//
///**
// * Copyright (c) 16-9-9 by loren
// */
//
//public class MineCollectionActivity extends AppCompatActivity {
//
//    private ArrayList<CollectionBean.ResultEntity.FavoritesListEntity.GoodsCommonlistEntity> data = new ArrayList<>();
//
//    @BindingAdapter({"imageurl"})
//    public static void ImageLoad(ImageView iv, String imageUrl) {
//        ImageUtil.bind(iv, imageUrl);
//    }
//
////    @BindingConversion
////    public static void MineFunction(View view) {
////
////    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        MineCollectionActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.mine_collection_activity);
//        CollectionAdapter mAdapter = new CollectionAdapter(this, data);
//        binding.rvCollectionList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        binding.rvCollectionList.setAdapter(mAdapter);
//        getCollectionList();
//        mAdapter.notifyDataSetChanged();
//
//
//        RecyclerView rv = $(R.id.rv_collection_list);
//    }
//
//    private void getCollectionList() {
//        CollectionBean collectionBean = new Gson().fromJson(Interface.JSONSTR, CollectionBean.class);
//        for (int i = 0; i < collectionBean.getResult().getFavorites_list().size(); i++) {
//            data.add(i, collectionBean.getResult().getFavorites_list().get(i).getGoods_commonlist().get(0));
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private <T> T $(@IdRes int id) {
//        return (T) findViewById(id);
//    }
//}
