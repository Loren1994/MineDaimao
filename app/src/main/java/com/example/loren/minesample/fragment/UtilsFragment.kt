package com.example.loren.minesample.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.loren.minesample.BezierActivity
import com.example.loren.minesample.R
import kotlinx.android.synthetic.main.util_fragment.*

/**
 * Created by loren on 2017/3/7.
 */

class UtilsFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.util_fragment, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bezier_tv.setOnClickListener { startActivity(Intent(activity, BezierActivity::class.java)) }
    }
}
