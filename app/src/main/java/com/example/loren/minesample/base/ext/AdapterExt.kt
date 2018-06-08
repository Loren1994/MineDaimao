package com.example.loren.minesample.base.ext

import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import pers.victor.ext.inflate

/**
 * Created by Victor on 2017/7/4. (ง •̀_•́)ง
 */
class Holder(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer

abstract class CommonAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = Holder(inflate(bindLayout(), parent))

    abstract fun bindLayout(): Int
}