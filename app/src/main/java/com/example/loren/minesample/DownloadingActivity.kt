package com.example.loren.minesample

import android.os.SystemClock
import android.view.View
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.downloading_activity.*

/**
 * Copyright © 08/01/2018 by loren
 */
class DownloadingActivity : BaseActivity() {

    override fun initWidgets() {
        download_view.setOnClickListener {
            if (download_view.isFinish()) {
                download_view.resetDownload()
            }
            if (!download_view.isDownloading()) {
                Thread {
                    repeat(100) {
                        download_view.setProgress(it + 1f)
                        SystemClock.sleep(100)
                    }
                }.start()
            }
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.downloading_activity
}