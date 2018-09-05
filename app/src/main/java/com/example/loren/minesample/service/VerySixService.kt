package com.example.loren.minesample.service

import android.accessibilityservice.AccessibilityService
import android.app.Notification
import android.content.ComponentName
import android.os.Handler
import android.os.Looper
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.example.loren.minesample.base.ext.log
import pers.victor.ext.toast


/**
 * Copyright © 27/12/2017 by loren
 * 部分机型TYPE_NOTIFICATION_STATE_CHANGED是监测toast通知
 * 此方式不能保证所有机型都好用
 * 另一种方式:NotificationListenerService(4.3)
 * 微信每个版本都会更新关键控件ID
 * 故抢红包插件要跟随微信升级控件ID
 */
class VerySixService : AccessibilityService() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onServiceConnected() {
        toast("SuperApp已开启AccessibilityService")
        super.onServiceConnected()
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            //显示当前Activity
            val cName = ComponentName(event.packageName.toString(), event.className.toString())
            ShowActivityService.setTv(event.packageName.toString(), cName.className)
            //抢红包
            //窗口
            val className = cName.className
            when (className) {
                "com.tencent.mm.ui.LauncherUI" -> openRedPacket()
                "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI" -> clickRedPacket()
                "com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyDetailUI" -> performBackClick()
            }
        }
        //监测通知
        if (event.eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED) {
            log("收到通知>>> " + event.text.toString())
            if (event.parcelableData != null && event.parcelableData is Notification) {
                val notification = event.parcelableData as Notification
//                val content = notification.tickerText.toString()
                val content = event.text.toString()
                if (content.contains("[微信红包]")) {
                    val pendingIntent = notification.contentIntent
                    pendingIntent.send()
                }
            }
        }
        //监测滚动
//        if (event.eventType == AccessibilityEvent.TYPE_VIEW_SCROLLED) {
//            if (event.className == "android.widget.ListView") {
//                openRedPacket()
//            }
//        }
    }

    private fun openRedPacket() {
        log(">>>openRedPacket")
        val rootNode = rootInActiveWindow
        if (rootNode != null) {
            val listNode = rootNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aem")
            if (null != listNode && listNode.size > 0) {
                val msgNode = listNode[0].findAccessibilityNodeInfosByViewId("com.tencent.mm:id/afo")
                if (msgNode != null && msgNode.size > 0) {
                    for (accessibilityNodeInfo in msgNode) {
                        val redPackage = accessibilityNodeInfo.findAccessibilityNodeInfosByText("领取红包")
                        if (null != redPackage && redPackage.size > 0) {
                            accessibilityNodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                            break
                        }
                    }
                }
            }
        }
    }

    private fun clickRedPacket() {
        log(">>>clickRedPacket")
        val rootNode = rootInActiveWindow
        val clickNode = rootNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/c85")
        if (null != clickNode && clickNode.size > 0) {
            clickNode[0].performAction(AccessibilityNodeInfo.ACTION_CLICK)
        } else {
            performBackClick()
        }
    }

    private fun performBackClick() {
        log(">>>performBackClick")
        handler.postDelayed({ performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK) }, 200L)
    }
}