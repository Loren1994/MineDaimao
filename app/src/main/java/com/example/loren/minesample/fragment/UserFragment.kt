package com.example.loren.minesample.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.loren.minesample.AmazingActivity
import com.example.loren.minesample.R
import com.example.loren.minesample.TranslationActivity
import kotlinx.android.synthetic.main.user_fragment.*


/**
 * Created by loren on 2017/3/7.
 */

class UserFragment : Fragment(), View.OnClickListener {
    private var mContext: Context? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.user_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mContext = activity
        amazing_tv.setOnClickListener(this)
        translation_tv.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.amazing_tv -> startActivity(Intent(mContext, AmazingActivity::class.java))
            R.id.translation_tv -> startActivity(Intent(mContext, TranslationActivity::class.java))
        }
    }
}
