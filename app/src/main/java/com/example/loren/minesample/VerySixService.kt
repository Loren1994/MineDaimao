package com.example.loren.minesample

import android.accessibilityservice.AccessibilityService
import android.app.ActivityManager
import android.view.accessibility.AccessibilityEvent
import pers.victor.ext.toast

/**
 * Copyright © 27/12/2017 by loren
 */
class VerySixService : AccessibilityService() {
    private lateinit var am: ActivityManager

    override fun onServiceConnected() {
        toast("已开启AccessibilityService")
        am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        super.onServiceConnected()
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(eventType: AccessibilityEvent) {
        val list = am.getRunningTasks(1)
        ShowActivityService.setTv(eventType.packageName.toString(),
                eventType.className.toString(),
                list[0].topActivity.packageName,
                list[0].topActivity.className)
    }
}