package com.example.loren.minesample.widget;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.loren.minesample.R;

import java.util.ArrayList;

public class AutoRecyclerView extends RecyclerView implements Runnable {

    private static Context mContext;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFootViews = new ArrayList<>();
    private Adapter mAdapter;

    private boolean isLoadingData = false;

    private LoadDataListener mLoadDataListener;

    public AutoRecyclerView(Context context) {
        this(context, null);
    }

    public AutoRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setOverScrollMode(OVER_SCROLL_NEVER);
        post(this);
    }

    @Override
    public void run() {

        LayoutManager manager = getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            layoutGridAttach((GridLayoutManager) manager);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            layoutStaggeredGridHeadAttach((StaggeredGridLayoutManager) manager);
        }
        if (mAdapter != null) {
            if (((WrapAdapter) mAdapter).getFootersCount() > 0) {
                mFootViews.get(0).setVisibility(GONE);
            }
        }
    }

    /**
     * 添加头部视图
     *
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderViews.clear();
        mHeaderViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof WrapAdapter)) {
                mAdapter = new WrapAdapter(mHeaderViews, mFootViews, mAdapter);
            }
        }
    }

    /**
     * 添加脚部视图
     *
     * @param view
     */
    public void addFootView(final View view) {
        mFootViews.clear();
        mFootViews.add(view);
        if (mAdapter != null) {
            if (!(mAdapter instanceof WrapAdapter)) {
                mAdapter = new WrapAdapter(mHeaderViews, mFootViews, mAdapter);
            }
        }
    }

    public void showFootView() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mFootViews.size() > 0) {
                    mFootViews.get(0).setVisibility(VISIBLE);
                }
            }
        }, 500);
    }

    /**
     * 设置加载更多数据的监听
     *
     * @param listener
     */
    public void setLoadDataListener(LoadDataListener listener) {
        mLoadDataListener = listener;
    }

    /**
     * 加载更多数据完成后调用，必须在UI线程中
     */
    public void loadMoreComplete(boolean isOver) {
        isLoadingData = false;

        if (mFootViews.size() > 0) {
            if (isOver) {
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((LinearLayout) mFootViews.get(0)).getChildAt(0).setVisibility(GONE);
                        ((LinearLayout) mFootViews.get(0)).getChildAt(1).setVisibility(VISIBLE);
                        mFootViews.get(0).setVisibility(VISIBLE);
                        mLoadDataListener = null;
                    }
                }, 333);
            } else {
                ((LinearLayout) mFootViews.get(0)).getChildAt(1).setVisibility(GONE);
                ((LinearLayout) mFootViews.get(0)).getChildAt(0).setVisibility(VISIBLE);
                mFootViews.get(0).setVisibility(GONE);
            }
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mFootViews.isEmpty()) {
            // 新建脚部
            View view = LayoutInflater.from(mContext).inflate(R.layout.rv_loadmore_footer, null);
            mFootViews.add(view);
        }
        // 使用包装了头部和脚部的适配器
        adapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
        super.setAdapter(adapter);
        mAdapter = adapter;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    /**
     * 给GridLayoutManager附加头部脚部和滑动过度监听
     *
     * @param manager
     */
    private void layoutGridAttach(final GridLayoutManager manager) {
        // GridView布局
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return ((WrapAdapter) mAdapter).isHeader(
                        position) || ((WrapAdapter) mAdapter).isFooter(
                        position) ? manager.getSpanCount() : 1;
            }
        });
        requestLayout();
    }

    /**
     * 给StaggeredGridLayoutManager附加头部和滑动过度监听
     *
     * @param manager
     */
    private void layoutStaggeredGridHeadAttach(StaggeredGridLayoutManager manager) {
        // 从前向后查找Header并设置为充满一行
        View view;
        for (int i = 0; i < mAdapter.getItemCount(); i++) {
            if (((WrapAdapter) mAdapter).isHeader(i)) {
                view = getChildAt(i);
                ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).setFullSpan(
                        true);
                view.requestLayout();
            } else {
                break;
            }
        }
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        // 当前不滚动，且不是正在刷新或加载数据
        if (state == RecyclerView.SCROLL_STATE_IDLE && mLoadDataListener != null &&
                !isLoadingData) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;
            // 获取最后一个正在显示的Item的位置
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
            }

            if (layoutManager.getChildCount() > 0 && lastVisibleItemPosition >= layoutManager
                    .getItemCount() - 1) {
                if (mFootViews.size() > 0) {
                    mFootViews.get(0).setVisibility(VISIBLE);
                }
                // 加载更多
                isLoadingData = true;
                mLoadDataListener.onLoadMore();
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * 刷新和加载更多数据的监听接口
     */
    public interface LoadDataListener {

        /**
         * 执行加载更多
         */
        void onLoadMore();

    }

    /**
     * 自定义带有头部/脚部的适配器
     */
    private class WrapAdapter extends Adapter<ViewHolder> {

        final ArrayList<View> EMPTY_INFO_LIST = new ArrayList<>();
        private Adapter mAdapter;
        private ArrayList<View> mHeaderViews;
        private ArrayList<View> mFootViews;
        private int headerPosition = 0;

        public WrapAdapter(ArrayList<View> mHeaderViews, ArrayList<View> mFootViews,
                           Adapter mAdapter) {
            this.mAdapter = mAdapter;
            if (mHeaderViews == null) {
                this.mHeaderViews = EMPTY_INFO_LIST;
            } else {
                this.mHeaderViews = mHeaderViews;
            }
            if (mFootViews == null) {
                this.mFootViews = EMPTY_INFO_LIST;
            } else {
                this.mFootViews = mFootViews;
            }
        }

        /**
         * 当前布局是否为Header
         *
         * @param position
         * @return
         */
        public boolean isHeader(int position) {
            return position >= 0 && position < mHeaderViews.size();
        }

        /**
         * 当前布局是否为Footer
         *
         * @param position
         * @return
         */
        public boolean isFooter(int position) {
            return position < getItemCount() && position >= getItemCount() - mFootViews.size();
        }

        /**
         * Header的数量
         *
         * @return
         */
        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        /**
         * Footer的数量
         *
         * @return
         */
        public int getFootersCount() {
            return mFootViews.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == RecyclerView.INVALID_TYPE) {
                return new HeaderViewHolder(mHeaderViews.get(headerPosition++));
            } else if (viewType == RecyclerView.INVALID_TYPE - 1) {

                /**
                 * 单独设置Footer
                 */
                StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager
                        .LayoutParams(
                        StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT,
                        StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT);
                params.setFullSpan(true);
                mFootViews.get(0).setLayoutParams(params);

                return new HeaderViewHolder(mFootViews.get(0));
            }
            return mAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int numHeaders = getHeadersCount();
            if (position < numHeaders) {
                return;
            }
            int adjPosition = position - numHeaders;
            int adapterCount;
            if (mAdapter != null) {
                adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    mAdapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }

        @Override
        public int getItemCount() {
            if (mAdapter != null) {
                return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
            } else {
                return getHeadersCount() + getFootersCount();
            }
        }

        @Override
        public int getItemViewType(int position) {
            int numHeaders = getHeadersCount();
            if (position < numHeaders) {
                return RecyclerView.INVALID_TYPE;
            }
            int adjPosition = position - numHeaders;
            int adapterCount;
            if (mAdapter != null) {
                adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemViewType(adjPosition);
                }
            }
            return RecyclerView.INVALID_TYPE - 1;
        }

        @Override
        public long getItemId(int position) {
            int numHeaders = getHeadersCount();
            if (mAdapter != null && position >= numHeaders) {
                int adjPosition = position - numHeaders;
                int adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemId(adjPosition);
                }
            }
            return -1;
        }

        private class HeaderViewHolder extends ViewHolder {
            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
