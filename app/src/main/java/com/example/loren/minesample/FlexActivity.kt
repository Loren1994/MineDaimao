package com.example.loren.minesample

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import com.example.loren.minesample.threadPool.Priority
import com.example.loren.minesample.threadPool.PriorityExecutor
import com.example.loren.minesample.threadPool.PriorityRunnable
import com.example.loren.minesample.wallpaper.CustomWallPaper
import kotlinx.android.synthetic.main.flex_activity.*
import pers.victor.ext.dp2px
import pers.victor.ext.findDrawable

/**
 * Copyright © 2018/4/17 by loren
 */
class FlexActivity : BaseActivity() {

    private val threadExecuter = PriorityExecutor()

    @SuppressLint("SetTextI18n")
    override fun initWidgets() {
        repeat(20) {
            val tv = TextView(this)
            tv.text = "ITEM-${it * 99}"
            tv.background = findDrawable(R.drawable.bg_green_coner)
            tv.setTextColor(Color.WHITE)
            tv.setPadding(dp2px(8), 10, dp2px(8), 10)
            val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            param.setMargins(dp2px(4), dp2px(4), dp2px(4), dp2px(4))
            tv.layoutParams = param
            flex_fl.addView(tv)
        }
        video_btn.setOnClickListener { setWallpaper() }
        thread_btn.setOnClickListener { testThreadPool() }
    }

    private fun setWallpaper() {
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, ComponentName(this, CustomWallPaper::class.java))
        startActivity(intent)
        //弹壁纸选项
//        val pickWallpaper = Intent(Intent.ACTION_SET_WALLPAPER)
//        val chooser = Intent.createChooser(pickWallpaper, getString(R.string.app_name))
//        startActivity(chooser)
    }

    private fun testThreadPool() {
        repeat(25) {
            val runnable = when (it % 3) {
                0 -> PriorityRunnable(Priority.HIGH, Runnable {
                    Thread.sleep(1000)
                    log("${Thread.currentThread().name}优先级HIGH线程 - $it 执行")
                })
                1 -> PriorityRunnable(Priority.NORMAL, Runnable {
                    Thread.sleep(1000)
                    log("${Thread.currentThread().name}优先级NORMAL线程 - $it 执行")
                })
                else -> PriorityRunnable(Priority.LOW, Runnable {
                    Thread.sleep(1000)
                    log("${Thread.currentThread().name}优先级LOW线程 - $it 执行")
                })
            }
            threadExecuter.execute(runnable)
        }
    }

    override fun onDestroy() {
        threadExecuter.shutdown()
        super.onDestroy()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.flex_activity
}