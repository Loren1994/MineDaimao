package com.example.loren.minesample

import android.app.SearchManager
import android.content.*
import android.content.ClipboardManager.OnPrimaryClipChangedListener
import android.net.Uri
import android.text.TextUtils
import android.view.View
import com.example.loren.minesample.annotation.AnimationClick
import com.example.loren.minesample.annotation.AnimationClickType
import com.example.loren.minesample.annotation.LorenInject
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.widget.ChoosePopupWindow
import kotlinx.android.synthetic.main.clip_activity.*
import pers.victor.ext.no
import pers.victor.ext.yes


/**
 * Copyright © 19/12/2017 by loren
 */
class ClipActivity : BaseActivity() {

    private lateinit var popupWindow: ChoosePopupWindow
    private lateinit var cm: ClipboardManager
    private var model = true

    override fun initWidgets() {
        LorenInject.into(this)
        model.yes { setTitleBarRight("监听模式") }.no { setTitleBarRight("普通模式") }
        setTitleBarRightClick {
            model = !model
            model.yes { setTitleBarRight("监听模式") }.no { setTitleBarRight("普通模式") }
        }
        popupWindow = ChoosePopupWindow(this, View.OnClickListener {
            when (it.id) {
                R.id.tv_common -> setClip(1)
                R.id.tv_intent -> setClip(2)
                R.id.tv_url -> setClip(3)
            }
            popupWindow.dismiss()
        })
        cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.addPrimaryClipChangedListener(onPrimaryClipChangedListener)
    }

    private var onPrimaryClipChangedListener: OnPrimaryClipChangedListener = OnPrimaryClipChangedListener {
        if (!model) {
            val text = cm.primaryClip.getItemAt(0).coerceToText(applicationContext).toString()
            val intent = Intent.parseUri(text, 0)
            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        } else {
            popupWindow.show(window.decorView)
        }
    }

    private fun setClip(type: Int) {
        val intent: Intent
        val clipData: ClipData
        when (type) {
            1 -> {
                intent = Intent()
                intent.action = null
                clipData = ClipData.newPlainText("text", "测试粘贴文本")
            }
            2 -> {
                var searchKey = cm.primaryClip.getItemAt(0).text.toString()
                searchKey = TextUtils.isEmpty(searchKey).yes { "关键字为空" }.no { searchKey }
                intent = Intent()
                intent.action = Intent.ACTION_WEB_SEARCH
                intent.putExtra(SearchManager.QUERY, searchKey)
                clipData = ClipData.newIntent("intent", intent)
            }
            else -> {
                var content = cm.primaryClip.getItemAt(0).text.toString()
                content = TextUtils.isEmpty(content).yes { "剪贴板为空" }.no { content }
                val uri = Uri.parse("smsto:10086")
                intent = Intent(Intent.ACTION_SENDTO, uri)
                intent.putExtra("sms_body", content)
                clipData = ClipData.newIntent("intent", intent)
            }
        }
        if (model) {
            if (type == 1)
                cm.primaryClip = clipData
            intent.action?.let { startActivity(intent) }
        } else {
            cm.primaryClip = clipData
        }

        //剪贴板中只存在一个ClipData对象
        //val clipItem = ClipData.Item("ITEM测试粘贴文本")
        //clipData.addItem(clipItem)
        //cm.primaryClip.addItem(clipItem)
    }

    private fun getClip() {
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val sb = StringBuilder()
        repeat(cm.primaryClip.itemCount) { i ->
            sb.append(cm.primaryClip.getItemAt(i).text)
        }
        text_edt.setText(sb.toString())
    }

    @AnimationClick(viewIds = [R.id.set_clip_tv, R.id.get_clip_tv], animType = AnimationClickType.SCALE)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.set_clip_tv -> model.yes { setClip(1) }.no { popupWindow.show(window.decorView) }
            R.id.get_clip_tv -> getClip()
        }
    }

    override fun onDestroy() {
        cm.removePrimaryClipChangedListener(onPrimaryClipChangedListener)
        super.onDestroy()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.clip_activity
}