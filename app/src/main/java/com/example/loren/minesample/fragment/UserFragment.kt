package com.example.loren.minesample.fragment

import android.content.Intent
import android.provider.Settings
import android.view.View
import com.example.loren.minesample.*
import com.example.loren.minesample.base.ui.BaseFragment
import kotlinx.android.synthetic.main.user_fragment.*


/**
 * Created by loren on 2017/3/7.
 */

class UserFragment : BaseFragment(), View.OnClickListener {
    override fun initWidgets() {

    }

    override fun useTitleBar() = false

    override fun setListeners() {
        click(amazing_tv, translation_tv, phone_tv, clip_tv, access_tv)
    }

    override fun onWidgetsClick(v: View) {
        when (v.id) {
            R.id.amazing_tv -> startActivity(Intent(mContext, AmazingActivity::class.java))
            R.id.translation_tv -> startActivity(Intent(mContext, TranslationActivity::class.java))
            R.id.phone_tv -> startActivity(Intent(mContext, AppManagerActivity::class.java))
            R.id.clip_tv -> startActivity(Intent(mContext, ClipActivity::class.java))
            R.id.access_tv -> {
                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                startActivity(intent)
            }

        }
    }

    override fun bindLayout() = R.layout.user_fragment

}
