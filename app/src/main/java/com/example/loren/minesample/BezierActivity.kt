package com.example.loren.minesample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.bezier_activity.*

/**
 * Copyright © 2017/10/13 by loren
 */
class BezierActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bezier_activity)
        bezier_view.showSubline = false
        bezier_view.setOnClickListener { bezier_view.transformation() }
    }
}