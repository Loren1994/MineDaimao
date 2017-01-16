package com.example.loren.minesample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_loren.*

class LorenActivity : AppCompatActivity() {

//    val int: Int = 0xffffffff // error
//    val anotherInt: Int = 0xffffffff.toInt() // correct

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loren)
        textview.text=intent.getStringExtra("message")
    }
}
