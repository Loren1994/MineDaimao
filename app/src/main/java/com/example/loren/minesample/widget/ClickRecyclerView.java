package com.example.loren.minesample.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Copyright © 24/11/2017 by loren
 */

public class ClickRecyclerView extends RecyclerView {

    public ClickRecyclerView(Context context) {
        this(context, null);
    }

    public ClickRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private OnItemClickListener mItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return mItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        removeOnItemTouchListener(mOnRecyclerViewItemClickListener);
        addOnItemTouchListener(mOnRecyclerViewItemClickListener);
    }

    public interface OnItemClickListener {

        public void onItemClick(RecyclerView.ViewHolder vh, int position);

        public void onItemLongClick(RecyclerView.ViewHolder vh, int position);
    }

    private OnRecyclerViewItemClickListener mOnRecyclerViewItemClickListener = new OnRecyclerViewItemClickListener(this);
    private class OnRecyclerViewItemClickListener implements
            RecyclerView.OnItemTouchListener {

        private RecyclerView mRecyclerView;
        private GestureDetectorCompat mGestureDetectorCompat;

        public OnRecyclerViewItemClickListener(RecyclerView recyclerView) {
            mRecyclerView = recyclerView;
            mGestureDetectorCompat = new GestureDetectorCompat(
                    recyclerView.getContext(),
                    new ItemTouchGestureDetectorListener());
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView,
                                             MotionEvent motionEvent) {
            mGestureDetectorCompat.onTouchEvent(motionEvent);
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView,
                                 MotionEvent motionEvent) {
            mGestureDetectorCompat.onTouchEvent(motionEvent);
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }

        private class ItemTouchGestureDetectorListener extends
                GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mItemClickListener == null) {
                    return false;
                }
                View clickedChild = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (clickedChild != null) {
                    RecyclerView.ViewHolder vh = mRecyclerView.getChildViewHolder(clickedChild);
                    if (vh != null) {
                        mItemClickListener.onItemClick(vh, vh.getAdapterPosition());
                    }
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                if (mItemClickListener == null) {
                    return;
                }
                View clickedChild = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                if (clickedChild != null) {
                    RecyclerView.ViewHolder vh = mRecyclerView .getChildViewHolder(clickedChild);
                    if (vh != null) {
                        mItemClickListener.onItemLongClick(vh, vh.getAdapterPosition());
                    }
                }
            }
        }

    }

} 
