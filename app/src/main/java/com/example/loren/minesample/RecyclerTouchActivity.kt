package com.example.loren.minesample

import android.graphics.Canvas
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import android.view.View
import com.example.loren.minesample.adapter.TouchAdapter
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.recycler_touch_activity.*
import java.util.*


/**
 * Copyright © 2018/2/11 by loren
 */
class RecyclerTouchActivity : BaseActivity() {
    private val data = arrayListOf<String>()

    override fun initWidgets() {
        setTitleBarText("ItemTouchHelper")
        repeat(20) {
            data.add("ITEM - $it")
        }
        val adapter = TouchAdapter(data)
        touch_rv.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        touch_rv.adapter = adapter
//        ItemTouchHelper(CustomTouchHelp(adapter)).attachToRecyclerView(touch_rv)
        val callback = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, target: androidx.recyclerview.widget.RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                if (fromPosition < toPosition) {
                    for (i in fromPosition until toPosition) {
                        Collections.swap(data, i, i + 1)
                    }
                } else {
                    for (i in fromPosition downTo toPosition + 1) {
                        Collections.swap(data, i, i - 1)
                    }
                }
                recyclerView.adapter!!.notifyItemMoved(fromPosition, toPosition)
                return false
            }

            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                data.removeAt(viewHolder.adapterPosition)
                touch_rv.adapter!!.notifyItemRemoved(viewHolder.adapterPosition)
            }

            override fun onChildDraw(c: Canvas, recyclerView: androidx.recyclerview.widget.RecyclerView, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val alpha = 1 - Math.abs(dX) / viewHolder.itemView.width
                    viewHolder.itemView.alpha = alpha
                    viewHolder.itemView.translationX = dX
                }
            }

        })
        callback.attachToRecyclerView(touch_rv)
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.recycler_touch_activity
}