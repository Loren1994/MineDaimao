package com.example.loren.minesample.base.view

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import com.example.loren.minesample.R
import kotlinx.android.synthetic.main.loading_dialog.view.*
import pers.victor.ext.inflate

/**
 * Created by victor on 16-6-13. (ง •̀_•́)ง
 */
class LoadingDialog(aty: Activity) : AlertDialog(aty, R.style.LoadingDialog) {
    private val customView = inflate(R.layout.loading_dialog)

    init {
        setView(customView)
        ownerActivity = aty
    }

    override fun dismiss() {
        if (ownerActivity != null && !ownerActivity.isDestroyed) {
            super.dismiss()
        }
    }

    fun setText(message: CharSequence?) {
        customView.tv_loading_dialog_title.text = message
    }

    fun show(message: CharSequence?) {
        customView.tv_loading_dialog_title.text = message
        super.show()
        val lp = window.attributes
        lp.dimAmount = 0f
        onWindowAttributesChanged(lp)
    }
}