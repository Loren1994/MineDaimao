package com.example.loren.minesample.base.view

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.loren.minesample.R

class AutoRecyclerView(val mContext: Context, attrs: AttributeSet) : androidx.recyclerview.widget.RecyclerView(mContext, attrs), Runnable {
    private val mHeaderViews = arrayListOf<View>()
    private val mFootViews = arrayListOf<View>()
    private var mAdapter: Adapter<ViewHolder>? = null
    private var isLoadingData = false
    private var mLoadDataListenerKotlin: (() -> Unit)? = null

    init {
        overScrollMode = View.OVER_SCROLL_NEVER
        post(this)
    }

    override fun run() {
        val manager = layoutManager
        if (manager is androidx.recyclerview.widget.GridLayoutManager) {
            layoutGridAttach(manager)
        } else if (manager is androidx.recyclerview.widget.StaggeredGridLayoutManager) {
            layoutStaggeredGridHeadAttach(manager)
        }
        if (mAdapter != null) {
            if ((mAdapter as WrapAdapter).footersCount > 0) {
                mFootViews[0].visibility = View.GONE
            }
        }
    }

    fun addHeaderView(view: View) {
        mHeaderViews.clear()
        mHeaderViews.add(view)
        if (mAdapter != null) {
            if (mAdapter !is WrapAdapter) {
                mAdapter = WrapAdapter(mHeaderViews, mFootViews, mAdapter)
            }
        }
    }

    fun addFootView(view: View) {
        mFootViews.clear()
        mFootViews.add(view)
        if (mAdapter != null) {
            if (mAdapter !is WrapAdapter) {
                mAdapter = WrapAdapter(mHeaderViews, mFootViews, mAdapter)
            }
        }
    }

    fun showFootView() {
        postDelayed({
            if (mFootViews.size > 0) {
                mFootViews[0].visibility = View.VISIBLE
            }
        }, 500)
    }

    /**
     * 设置加载更多数据的监听
     */

    fun setOnLoadingListener(listener: (() -> Unit)) {
        mLoadDataListenerKotlin = listener
    }

    /**
     * 加载更多数据完成后调用，必须在UI线程中
     */
    fun hasNextPage(hasNextPage: Boolean) {
        isLoadingData = false

        if (mFootViews.size > 0) {
            if (!hasNextPage) {
                postDelayed({
                    (mFootViews[0] as LinearLayout).getChildAt(0).visibility = View.GONE
                    (mFootViews[0] as LinearLayout).getChildAt(1).visibility = View.VISIBLE
                    mFootViews[0].visibility = View.VISIBLE
                    mLoadDataListenerKotlin = null
                }, 333)
            } else {
                (mFootViews[0] as LinearLayout).getChildAt(1).visibility = View.GONE
                (mFootViews[0] as LinearLayout).getChildAt(0).visibility = View.VISIBLE
                mFootViews[0].visibility = View.GONE
            }
        }
    }

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        var adapterT = adapter
        if (mFootViews.isEmpty()) {
            val view = LayoutInflater.from(mContext).inflate(R.layout.rv_loadmore_footer, this, false)
            mFootViews.add(view)
        }
        adapterT = WrapAdapter(mHeaderViews, mFootViews, adapterT)
        super.setAdapter(adapterT)
        mAdapter = adapterT
    }

    private fun layoutGridAttach(manager: androidx.recyclerview.widget.GridLayoutManager) {
        val originLookUp = manager.spanSizeLookup
        manager.spanSizeLookup = object : androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if ((mAdapter as WrapAdapter).isHeader(position) || (mAdapter as WrapAdapter).isFooter(position))
                    manager.spanCount
                else
                    originLookUp.getSpanSize(position)
            }
        }
        requestLayout()
    }

    private fun layoutStaggeredGridHeadAttach(manager: androidx.recyclerview.widget.StaggeredGridLayoutManager) {
        for (i in 0..mAdapter!!.itemCount - 1) {
            if ((mAdapter as WrapAdapter).isHeader(i)) {
                val view = getChildAt(i)
                (view.layoutParams as androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
                view.requestLayout()
            } else {
                break
            }
        }
    }


    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        // 当前不滚动，且不是正在刷新或加载数据
        if (state == SCROLL_STATE_IDLE && mLoadDataListenerKotlin != null && !isLoadingData) {
            val layoutManager = layoutManager
            val lastVisibleItemPosition: Int
            // 获取最后一个正在显示的Item的位置
            if (layoutManager is androidx.recyclerview.widget.GridLayoutManager) {
                lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            } else if (layoutManager is androidx.recyclerview.widget.StaggeredGridLayoutManager) {
                val into = IntArray(layoutManager.spanCount)
                layoutManager.findLastVisibleItemPositions(into)
                lastVisibleItemPosition = findMax(into)
            } else {
                lastVisibleItemPosition = (layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findLastVisibleItemPosition()
            }

            if (layoutManager.childCount > 0 && lastVisibleItemPosition >= layoutManager.itemCount - 1) {
                if (mFootViews.size > 0) {
                    mFootViews[0].visibility = View.VISIBLE
                }
                // 加载更多
                isLoadingData = true
                mLoadDataListenerKotlin!!()
            }
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        val max = lastPositions.max() ?: lastPositions[0]
        return max
    }

    /**
     * 自定义带有头部/脚部的适配器
     */
    private inner class WrapAdapter(mHeaderViews: MutableList<View>?, mFootViews: MutableList<View>?, private val originAdapter: Adapter<ViewHolder>?) : Adapter<ViewHolder>() {
        private var mHeaderViews: MutableList<View>? = null
        private var mFootViews: MutableList<View>? = null
        internal val EMPTY_INFO_LIST = arrayListOf<View>()
        private var headerPosition = 0

        init {
            if (mHeaderViews == null) {
                this.mHeaderViews = EMPTY_INFO_LIST
            } else {
                this.mHeaderViews = mHeaderViews
            }
            if (mFootViews == null) {
                this.mFootViews = EMPTY_INFO_LIST
            } else {
                this.mFootViews = mFootViews
            }
        }

        /**
         * 当前布局是否为Header
         */
        fun isHeader(position: Int): Boolean {
            return position >= 0 && position < mHeaderViews!!.size
        }

        /**
         * 当前布局是否为Footer
         */
        fun isFooter(position: Int): Boolean {
            return position < itemCount && position >= itemCount - mFootViews!!.size
        }

        /**
         * Footer的数量
         */
        val footersCount: Int
            get() = mFootViews!!.size

        override fun getItemViewType(position: Int): Int {
            if (isHeader(position)) {
                return INVALID_TYPE
            }
            val adjPosition = position - headerPosition
            if (originAdapter != null) {
                val adapterCount = originAdapter.itemCount
                if (adjPosition < adapterCount) {
                    return originAdapter.getItemViewType(adjPosition)
                }
            }
            return INVALID_TYPE - 1
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            if (viewType == INVALID_TYPE) {
                return HeaderViewHolder(mHeaderViews!![headerPosition++])
            } else if (viewType == INVALID_TYPE - 1) {
                val params = androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams(
                        androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams.MATCH_PARENT,
                        androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams.WRAP_CONTENT)
                params.isFullSpan = true
                mFootViews!![0].layoutParams = params
                return HeaderViewHolder(mFootViews!![0])
            }
            return originAdapter!!.onCreateViewHolder(parent, viewType)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (isHeader(holder.adapterPosition)) {
                return
            }
            val adjPosition = holder.adapterPosition - mHeaderViews!!.size
            if (originAdapter != null) {
                val adapterCount = originAdapter.itemCount
                if (adjPosition < adapterCount) {
                    originAdapter.onBindViewHolder(holder, adjPosition)
                }
            }
        }

        override fun getItemCount(): Int {
            if (originAdapter != null) {
                return mHeaderViews!!.size + mFootViews!!.size + originAdapter.itemCount
            } else {
                return mHeaderViews!!.size + mFootViews!!.size
            }
        }

        inner class HeaderViewHolder(itemView: View) : ViewHolder(itemView)
    }
}
