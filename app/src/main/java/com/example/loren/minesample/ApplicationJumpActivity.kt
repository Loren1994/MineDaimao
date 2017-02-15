package com.example.loren.minesample

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Slide
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.application_jump_activity.*

class ApplicationJumpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.application_jump_activity)
        setAnim()
        app_tv.setOnClickListener {
            try {
                //首页
                //startActivity(Intent(this.packageManager.getLaunchIntentForPackage("com.centling.honny")))
                //关于页面 exported=true
                startActivity(Intent(Intent.ACTION_MAIN)
                        .addCategory(Intent.CATEGORY_LAUNCHER)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        .setComponent(ComponentName("com.centling.honny", "com.activity.AboutUsActivity")))
            } catch (e: Exception) {
                Toast.makeText(this, "未安装此应用", Toast.LENGTH_SHORT).show()
                Log.e("loren", "exception: " + e.toString())
            }
        }
        wx_tv.setOnClickListener {
            try {
                val intent: Intent = Intent(Intent.ACTION_MAIN)
                val cmp: ComponentName = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.component = cmp
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "未安装此应用", Toast.LENGTH_LONG).show()
            }
        }
        val qqStr = "188239420"
        qq_tv.setOnClickListener {
            try {
                startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin=$qqStr")))
            } catch (e: Exception) {
                Toast.makeText(this, "未安装此应用", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun setAnim() {
        window.enterTransition = Slide().setDuration(1000)
        window.exitTransition = Slide().setDuration(1000)
    }
}