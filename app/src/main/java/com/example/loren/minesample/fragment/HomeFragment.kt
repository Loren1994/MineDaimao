package com.example.loren.minesample.fragment

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.loren.minesample.*
import kotlinx.android.synthetic.main.home_fragment.*

/**
 * Created by loren on 2017/3/7.
 */

class HomeFragment : Fragment(), View.OnClickListener {
    private var mContext: Context? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.home_fragment, container, false)
        return view

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = activity
        for (i in 0 until container!!.childCount) {
            if (i % 2 == 0) {
                container!!.getChildAt(i).setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.main))
            } else {
                container!!.getChildAt(i).setBackgroundColor(ContextCompat.getColor(mContext!!, R.color.match))
            }
        }
        move_view!!.setOnClickListener(this)
        fs_tv!!.setOnClickListener(this)
        flag_tv!!.setOnClickListener(this)
        remind_tv!!.setOnClickListener(this)
        wx_vedio!!.setOnClickListener(this)
        chat!!.setOnClickListener(this)
        open!!.setOnClickListener(this)
        vector!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
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
                activity.overridePendingTransition(0, 0)
            }

            else -> {
            }
        }
    }
}
