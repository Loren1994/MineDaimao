package com.example.loren.minesample.service

import android.accessibilityservice.AccessibilityService
import android.app.ActivityManager
import android.content.ComponentName
import android.view.accessibility.AccessibilityEvent
import com.example.loren.minesample.service.ShowActivityService
import pers.victor.ext.toast


/**
 * Copyright © 27/12/2017 by loren
 */
class VerySixService : AccessibilityService() {
    private lateinit var am: ActivityManager

    override fun onServiceConnected() {
        toast("SuperApp已开启AccessibilityService")
        am = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        super.onServiceConnected()
    }

    override fun onInterrupt() {

    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val cName = ComponentName(event.packageName.toString(), event.className.toString())
            ShowActivityService.setTv(event.packageName.toString(), cName.className)
        }
    }
}