package com.example.loren.minesample

import android.annotation.SuppressLint
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.entity.User
import kotlinx.android.synthetic.main.object_box_layout.*

/**
 * Copyright © 10/11/2017 by loren
 */
class ObjectBoxActivity : BaseActivity() {

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

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.object_box_layout
}