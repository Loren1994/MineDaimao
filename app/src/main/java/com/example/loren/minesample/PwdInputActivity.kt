package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.pwd_input_activity.*
import pers.victor.ext.toast

/**
 * Copyright © 05/01/2018 by loren
 */
class PwdInputActivity : BaseActivity() {
    override fun initWidgets() {
        key_board.onKBEvent = { str, type ->
            when (type) {
                key_board.KB_NUM -> input_edt.setText(str)
                key_board.KB_CONFIRM -> toast("确认")
                key_board.KB_CANCEL -> input_edt.setText(str)
            }
        }
        val list = arrayListOf<String>()
        repeat(10) {
            list.add(it.toString())
        }
        key_board.setKeyBoardText(list)
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.pwd_input_activity
}