package com.example.loren.minesample.fragment

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.View
import com.example.loren.minesample.*
import com.example.loren.minesample.annotation.AnimationClick
import com.example.loren.minesample.annotation.AnimationClickType
import com.example.loren.minesample.annotation.LorenInject
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseFragment
import com.example.loren.minesample.eventbus.EventMsg
import com.example.loren.minesample.eventbus.FEventbus
import com.example.loren.minesample.eventbus.OnEventListener
import com.example.loren.minesample.service.ShowActivityService
import com.example.loren.minesample.service.WindowsService
import pers.victor.ext.toast
import socket.SocketClientActivity
import socket.SocketServerActivity


/**
 * Created by loren on 2017/3/7.
 */

class UserFragment : BaseFragment(), OnEventListener<EventMsg.LorenEventMsg1> {
    override fun onEvent(event: EventMsg.LorenEventMsg1) {
        log("UserFragment收到消息:${event.type} - ${event.msg}")
    }

    private lateinit var dialog: AlertDialog.Builder
    private var clickPos = 0
    override fun initWidgets() {
        FEventbus.register(this)
        LorenInject.into(this)
        val list = arrayOf("TopActivity悬浮框", "吸附式悬浮框")
        dialog = AlertDialog.Builder(activity)
                .setTitle("请选择悬浮框")
                .setSingleChoiceItems(list, 0) { _, p -> clickPos = p }
                .setPositiveButton("嗯") { _, _ -> startWindowService() }
    }

    override fun useTitleBar() = false

    override fun setListeners() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (!hasPermission()) {
                toast("未开启权限")
                startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1)
            }
        }
        if (requestCode == 10) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!Settings.canDrawOverlays(activity)) {
                    toast("无悬浮窗权限")
                }
            }
        }
    }

    private fun hasPermission(): Boolean {
        return Settings.Secure.getInt(
                mContext.applicationContext.contentResolver,
                android.provider.Settings.Secure.ACCESSIBILITY_ENABLED) == 1
    }

    @AnimationClick([R.id.eventbus_tv, R.id.red_package_tv, R.id.expand_tv, R.id.path_tv, R.id.top_tv, R.id.search_tv, R.id.放大镜_tv, R.id.hw_tv, R.id.html_tv, R.id.input_tv, R.id.amazing_tv, R.id.phone_tv, R.id.clip_tv, R.id.access_tv, R.id.server_tv, R.id.client_tv, R.id.blur_tv], AnimationClickType.SCALE)
    override fun onClick(v: View) {
        when (v.id) {
//            R.id.translation_tv -> startActivity(Intent(mContext, TranslationActivity::class.java))
            R.id.amazing_tv -> startActivity(Intent(mContext, AmazingActivity::class.java))
            R.id.phone_tv -> startActivity(Intent(mContext, AppManagerActivity::class.java))
            R.id.clip_tv -> startActivity(Intent(mContext, ClipActivity::class.java))
            R.id.access_tv -> {
                if (!hasPermission()) {
                    startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1)
                } else {
                    clickPos = 0
                    dialog.show()
                }
            }
            R.id.server_tv -> startActivity<SocketServerActivity>()
            R.id.client_tv -> startActivity<SocketClientActivity>()
            R.id.blur_tv -> startActivity<BlurListActivity>()
            R.id.input_tv -> startActivity<PwdInputActivity>()
            R.id.html_tv -> startActivity<HtmlFileActivity>()
            R.id.hw_tv -> startActivity(Intent(activity, HWLockActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
            R.id.放大镜_tv -> startActivity(Intent(activity, 放大镜页面::class.java))
            R.id.search_tv -> startActivity(Intent(activity, SearchActivity::class.java))
            R.id.top_tv -> startActivity(Intent(activity, StayTopActivity::class.java))
            R.id.path_tv -> startActivity(Intent(activity, PathViewActivity::class.java))
            R.id.expand_tv -> startActivity(Intent(activity, ExpandActivity::class.java))
            R.id.red_package_tv -> startRedPackage()
            R.id.eventbus_tv -> startActivity<EventBusActivity>()
        }
    }

    private fun startRedPackage() {
        if (!hasPermission()) {
            startActivityForResult(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1)
        } else {
            toast("开启抢红包")
        }
    }

    override fun onWidgetsClick(v: View) {

    }

    private fun startWindowService() {
        when (clickPos) {
            0 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(activity!!.applicationContext)) {
                        val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + activity!!.packageName))
                        activity!!.startActivityForResult(intent, 10)
                        return
                    } else {
                        activity!!.startService(Intent(activity, ShowActivityService::class.java))
                    }
                }
            }
            1 -> activity!!.startService(Intent(activity, WindowsService::class.java))
        }
    }

    override fun bindLayout() = R.layout.user_fragment

}
