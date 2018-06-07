package com.example.loren.minesample

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.example.loren.minesample.entity.MyObjectBox
import com.facebook.drawee.backends.pipeline.Fresco
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import io.objectbox.BoxStore
import io.objectbox.android.AndroidObjectBrowser
import pers.victor.ext.Ext


/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * .   ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 *
 *
 * .............................................
 * 佛祖保佑             永无BUG
 *
 *
 * Copyright (c) 17-1-23 by loren
 */

class App : Application() {
    lateinit var boxStore: BoxStore
        private set

    override fun onCreate() {
        super.onCreate()
        application = applicationContext
        Ext.ctx = this
        initLogger()
        Fresco.initialize(this)
        getDisplayMetrics()

        boxStore = MyObjectBox.builder().androidContext(this).build()
        if (BuildConfig.DEBUG)
            AndroidObjectBrowser(boxStore).start(this)

//        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
//        activityManager.memoryClass
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)
                .methodCount(0)
                .tag("HiSmart")
                .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    /*获取高度、宽度、密度、缩放比例*/
    private fun getDisplayMetrics() {
        val metric = this.resources.displayMetrics
        SCREEN_WIDTH = metric.widthPixels
        SCREEN_HEIGHT = metric.heightPixels
        SCREEN_DENSITY = metric.density
        SCALED_DENSITY = metric.scaledDensity
    }

    companion object {
        var SCREEN_WIDTH: Int = 0
        var SCREEN_HEIGHT: Int = 0
        var SCREEN_DENSITY: Float = 0.toFloat()
        var SCALED_DENSITY: Float = 0.toFloat()
        lateinit var application: Context
    }
}
