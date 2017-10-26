package com.example.loren.minesample

import android.content.pm.ShortcutManager
import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity

class ShortCutsActivity : BaseActivity() {
    override fun initWidgets() {

    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.activity_short_cuts

    lateinit var shortCutsManager: ShortcutManager
    lateinit var mAdapter: ShortcutAdapter
    val mList = arrayListOf("张三 18661881639", "李四 18612341234", "王五 18661881234")

}
