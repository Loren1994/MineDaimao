package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.sign_revert_activity.*

/**
 * Copyright © 09/01/2018 by loren
 */
class SignWithRevertActivity : BaseActivity() {
    override fun initWidgets() {
        confirm_btn.setOnClickListener {
            sign_iv.setImageBitmap(sign_view.getSignBitmap())
        }
        clear_btn.setOnClickListener { sign_view.resetSign() }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.sign_revert_activity
}