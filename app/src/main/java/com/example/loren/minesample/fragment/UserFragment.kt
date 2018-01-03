package com.example.loren.minesample.fragment

import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import android.view.View
import com.example.loren.minesample.*
import com.example.loren.minesample.base.ui.BaseFragment
import kotlinx.android.synthetic.main.user_fragment.*
import pers.victor.ext.toast
import socket.SocketClientActivity
import socket.SocketServerActivity


/**
 * Created by loren on 2017/3/7.
 */

class UserFragment : BaseFragment(), View.OnClickListener {
    private lateinit var dialog: AlertDialog.Builder
    private var clickPos = 0
    override fun initWidgets() {
        val list = arrayOf("TopActivity悬浮框", "吸附式悬浮框")
        dialog = AlertDialog.Builder(activity)
                .setTitle("请选择悬浮框")
                .setSingleChoiceItems(list, 0, { _, p -> clickPos = p })
                .setPositiveButton("嗯", { _, _ -> startWindowService() })
    }

    override fun useTitleBar() = false

    override fun setListeners() {
        click(amazing_tv, phone_tv, clip_tv, access_tv, server_tv, client_tv, blur_tv)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (!hasPermission()) {
                toast("未开启权限")
                startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1)
            }
        }
    }

    private fun hasPermission(): Boolean {
        return Settings.Secure.getInt(mContext.applicationContext.contentResolver,
                android.provider.Settings.Secure.ACCESSIBILITY_ENABLED) == 1
    }

    override fun onWidgetsClick(v: View) {
        when (v.id) {
//            R.id.translation_tv -> startActivity(Intent(mContext, TranslationActivity::class.java))
            R.id.amazing_tv -> startActivity(Intent(mContext, AmazingActivity::class.java))
            R.id.phone_tv -> startActivity(Intent(mContext, AppManagerActivity::class.java))
            R.id.clip_tv -> startActivity(Intent(mContext, ClipActivity::class.java))
            R.id.access_tv -> {
                if (!hasPermission()) {
                    startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1)
                } else {
                    dialog.show()
                }
            }
            R.id.server_tv -> startActivity<SocketServerActivity>()
            R.id.client_tv -> startActivity<SocketClientActivity>()
            R.id.blur_tv -> startActivity<BlurListActivity>()
        }
    }

    private fun startWindowService() {
        when (clickPos) {
            0 -> activity!!.startService(Intent(activity, ShowActivityService::class.java))
            1 -> activity!!.startService(Intent(activity, WindowsService::class.java))
        }
    }

    override fun bindLayout() = R.layout.user_fragment

}
