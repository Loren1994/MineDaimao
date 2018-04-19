package com.example.loren.minesample

import android.annotation.SuppressLint
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.loren.minesample.adapter.ObjectBoxAdapter
import com.example.loren.minesample.annotation.AnimationItemClick
import com.example.loren.minesample.annotation.AnimationItemClickType
import com.example.loren.minesample.annotation.LorenAnn
import com.example.loren.minesample.annotation.LorenInject
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.entity.User
import com.example.loren.minesample.widget.ClickRecyclerView
import kotlinx.android.synthetic.main.object_box_layout.*
import pers.victor.ext.toast

/**
 * Copyright © 10/11/2017 by loren
 */
class ObjectBoxActivity : BaseActivity(), ClickRecyclerView.OnItemClickListener {

    private val DATA_COUNT = 20000
    @LorenAnn(name = "anno-test", age = 24)
    private var annoStr = ""

    @SuppressLint("SetTextI18n")
    override fun initWidgets() {
        LorenInject.into(this)
        log(">>>>" + annoStr)
        val user = boxStore.boxFor(User::class.java)
        val removeStartTime = System.currentTimeMillis()
        user.removeAll()
        val removeEndTime = System.currentTimeMillis()
        val userList = arrayListOf<User>()
        repeat(DATA_COUNT) { i ->
            userList.add(User(name = "name - $i", age = i))
        }
        val adapter = ObjectBoxAdapter(userList)
        test_rv.layoutManager = GridLayoutManager(this, 4)
        test_rv.adapter = adapter
        val insertStartTime = System.currentTimeMillis()
        user.put(userList)
        click_tv.text = "${DATA_COUNT}条数据 \n \n insert耗时${System.currentTimeMillis() - insertStartTime}ms \n \n removeAll耗时${removeEndTime - removeStartTime}ms"
    }

    //用注解则不能在activity里setListener,否则无效
    @AnimationItemClick(R.id.test_rv, AnimationItemClickType.SCALE)
    override fun onItemClick(vh: RecyclerView.ViewHolder, position: Int) {
        toast("$position - item click")
    }

    override fun onItemLongClick(vh: RecyclerView.ViewHolder, position: Int) {
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.object_box_layout
}