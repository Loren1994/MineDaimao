package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.search_activity.*
import pers.victor.ext.toast

/**
 * Copyright © 2018/1/29 by loren
 */
class SearchActivity : BaseActivity() {
    override fun initWidgets() {
        val list = arrayListOf("iPhone 8 Plus",
                "键鼠套装", "iPhone X", "键鼠", "pixel", "iPhone",
                "键鼠套装至尊超强版", "套餐", "麦片", "圣诞节", "", "王者荣耀", "波利亚", "哔哩哔哩会员",
                "这是一条测试最大长度的文字别问我为什么我就是这么的NB")
        search_layout.setData(list)
        search_layout.onItemListener = { pos, tv -> toast("点击 $pos - ${tv.text}") }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.search_activity
}