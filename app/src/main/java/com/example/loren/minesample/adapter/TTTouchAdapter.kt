package com.example.loren.minesample.adapter

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loren.minesample.R
import com.example.loren.minesample.entity.TTouchBean
import kotlinx.android.synthetic.main.item_ttouch_checked.view.*
import kotlinx.android.synthetic.main.item_ttouch_uncheck.view.*
import pers.victor.ext.dp2px
import java.util.*

/**
 * Copyright © 2018/3/29 by loren
 */
class TTTouchAdapter(val data: MutableList<TTouchBean>) : RecyclerView.Adapter<TTTouchAdapter.ViewHolder>() {

    private val ITEM_CHECK = 0
    private val ITEM_UNCHECK = 1
    private val ITEM_TITLE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_CHECK -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ttouch_checked, parent, false))
            ITEM_UNCHECK -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ttouch_uncheck, parent, false))
            else -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_ttouch_title, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].itemType
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (data[position].itemType) {
        //已选
            ITEM_CHECK -> holder.itemView.check_tv.text = data[position].content
        //未选
            ITEM_UNCHECK -> holder.itemView.uncheck_tv.text = data[position].content
        //未选标题
            else -> {
            }
        }
    }

    fun isTitle(pos: Int): Boolean {
        return data[pos].itemType == ITEM_TITLE
    }

    fun itemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(data, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

class TTouchHelp(val mAdapter: TTTouchAdapter) : ItemTouchHelper.Callback() {

    private var mPaint: Paint = Paint()

    init {
        mPaint.isAntiAlias = true
        mPaint.pathEffect = DashPathEffect(floatArrayOf(12f, 10f, 8f, 10f), 0f)
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
    }

    //返回拖拽和滑动的方向
    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        if (mAdapter.isTitle(viewHolder.layoutPosition)) {
            return makeMovementFlags(0, 0)
        }
        return makeMovementFlags(dragFlags, 0)
    }

    //拖动item进行移动时回调
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val fromPosition = viewHolder.adapterPosition   //拖动的position
        val toPosition = target.adapterPosition     //释放的position
        if (mAdapter.isTitle(toPosition)) {
            return false
        }
        mAdapter.itemMove(fromPosition, toPosition)
        return true
    }

    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        if (dX != 0f && dY != 0f || isCurrentlyActive) {
            c.drawRoundRect(viewHolder.itemView.left.toFloat(), viewHolder.itemView.top.toFloat(),
                    viewHolder.itemView.right.toFloat(), viewHolder.itemView.bottom.toFloat(),
                    dp2px(10).toFloat(), dp2px(10).toFloat(), mPaint)
        }
    }

    //拖拽或滑动时回调
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ACTION_STATE_DRAG) {
            val holder = viewHolder as TTTouchAdapter.ViewHolder
            holder.itemView.alpha = 0.5f
            holder.itemView.elevation = 10f
        }
    }

    //交互完成后回调
    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        super.clearView(recyclerView, viewHolder)
        val holder = viewHolder as TTTouchAdapter.ViewHolder
        holder.itemView.alpha = 1f
        holder.itemView.elevation = 0f
    }

    //滑动
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
    }

}