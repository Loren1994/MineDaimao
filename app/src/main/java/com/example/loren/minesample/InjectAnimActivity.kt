package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.annotation.AnimationClick
import com.example.loren.minesample.annotation.AnimationClickType
import com.example.loren.minesample.annotation.LorenInject
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.anim_inject_activity.*
import pers.victor.ext.toast


/**
 * Copyright © 23/11/2017 by loren
 */
class InjectAnimActivity : BaseActivity(), View.OnClickListener {

    override fun initWidgets() {
        LorenInject.into(this)
//        test_tv3.setOnClickListener { toast("onWidgetsClick") }
//        test_tv3.setOnClickListener(this)

//        Thread {
//            test_tv1.text = "child thread setter"
//        }.start()
    }

    @AnimationClick(viewIds = intArrayOf(R.id.test_tv1, R.id.test_tv2), animType = AnimationClickType.SCALE)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.test_tv1 -> toast("tv1")
            R.id.test_tv2 -> toast("tv2")
            R.id.test_tv3 -> toast("tv3")
        }
    }

    override fun setListeners() {
        click(test_tv3)
    }

    override fun onWidgetsClick(v: View) {
//        toast("onWidgetsClick")
    }

    override fun bindLayout() = R.layout.anim_inject_activity
}