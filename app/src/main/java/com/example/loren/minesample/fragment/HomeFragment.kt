package com.example.loren.minesample.fragment

import android.app.ActivityOptions
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.loren.minesample.*
import com.example.loren.minesample.base.ui.BaseFragment
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * Copyright © 2017/3/7 by loren
 */

class HomeFragment : BaseFragment() {
    override fun initWidgets() {
        for (i in 0 until container!!.childCount) {
            if (i % 2 == 0) {
                container!!.getChildAt(i).setBackgroundColor(ContextCompat.getColor(mContext, R.color.main))
            } else {
                container!!.getChildAt(i).setBackgroundColor(ContextCompat.getColor(mContext, R.color.match))
            }
        }
    }

    override fun useTitleBar() = false

    override fun setListeners() {
        click(move_view, fs_tv, flag_tv, remind_tv, wx_vedio, chat, open, vector)
    }

    override fun onWidgetsClick(v: View) {
        when (v.id) {
            R.id.move_view -> startActivity(Intent(mContext, MoveViewActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity, move_view, "share_tv").toBundle())
            R.id.flag_tv -> startActivity(Intent(mContext, FlagActivity::class.java))
            R.id.fs_tv -> startActivity(Intent(mContext, CustomViewActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
            R.id.remind_tv -> startActivity(Intent(mContext, RemindActivity::class.java))
            R.id.wx_vedio -> startActivity(Intent(mContext, CopyWxPullActivity::class.java))
            R.id.chat -> startActivity(Intent(mContext, ChatActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
            R.id.vector -> startActivity(Intent(mContext, VectorActivity::class.java))
            R.id.open -> {
                //                startActivity(new Intent(mContext, OpenScreenActivity.class),ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                startActivity(Intent(mContext, OpenScreenActivity::class.java))
                mContext.overridePendingTransition(0, 0)
            }
        }
    }

    override fun bindLayout() = R.layout.home_fragment
}
