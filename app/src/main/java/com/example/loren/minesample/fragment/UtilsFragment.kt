package com.example.loren.minesample.fragment

import android.Manifest
import android.view.View
import com.example.loren.minesample.*
import com.example.loren.minesample.annotation.AnimationClick
import com.example.loren.minesample.annotation.AnimationClickType
import com.example.loren.minesample.annotation.LorenInject
import com.example.loren.minesample.base.ui.BaseFragment

/**
 * Copyright © 2017/3/7 by loren
 */

class UtilsFragment : BaseFragment() {
    override fun initWidgets() {
        LorenInject.into(this)
    }

    @AnimationClick([R.id.face_tv, R.id.clear_tv, R.id.flex_tv, R.id.record_tv, R.id.high_tv, R.id.ble_tv, R.id.sign_revert_tv, R.id.touch_tv, R.id.sign_tv, R.id.bezier_tv, R.id.download_tv, R.id.screen_shot_tv, R.id.test_tv, R.id.inject_tv, R.id.remote_tv], AnimationClickType.ALPHA)
    override fun onClick(v: View) {
        when (v.id) {
            R.id.bezier_tv -> startActivity<BezierActivity>()
            R.id.screen_shot_tv -> startActivity<ScreenShotActivity>()
            R.id.test_tv -> startActivity<ObjectBoxActivity>()
            R.id.inject_tv -> startActivity<InjectAnimActivity>()
            R.id.remote_tv -> startActivity<RemoteViewActivity>()
            R.id.download_tv -> startActivity<DownloadingActivity>()
            R.id.sign_tv -> startActivity<SignActivity>()
            R.id.touch_tv -> startActivity<RecyclerTouchActivity>()
            R.id.sign_revert_tv -> startActivity<SignWithRevertActivity>()
            R.id.ble_tv -> startActivity<BleActivity>()
            R.id.high_tv -> startActivity<TTouchActivity>()
            R.id.record_tv -> requestPermission(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    granted = { startActivity<RecordScreenActivity>() }, denied = {})
            R.id.flex_tv -> startActivity<FlexActivity>()
            R.id.clear_tv -> startActivity<橡皮擦页面>()
            R.id.face_tv -> startActivity<FaceActivity>()
        }
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {

    }

    override fun useTitleBar() = false

    override fun bindLayout() = R.layout.util_fragment

}
