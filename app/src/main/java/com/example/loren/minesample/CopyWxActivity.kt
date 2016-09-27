package com.example.loren.minesample

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.copy_wx_activity.*

/**
 *                            _ooOoo_
 *                           o8888888o
 *                           88" . "88
 *                           (| -_- |)
 *                            O\ = /O
 *                        ____/`---'\____
 *                      .   ' \\| |// `.
 *                       / \\||| : |||// \
 *                     / _||||| -:- |||||- \
 *                       | | \\\ - /// | |
 *                     | \_| ''\---/'' | |
 *                      \ .-\__ `-` ___/-. /
 *                   ___`. .' /--.--\ `. . __
 *                ."" '< `.___\_<|>_/___.' >'"".
 *               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 *                 \ \ `-. \_ __\ /__ _/ .-` / /
 *         ======`-.____`-.___\_____/___.-`____.-'======
 *                            `=---='
 *
 *         .............................................
 *                  佛祖保佑             永无BUG
 *
 *                Copyright (c) 16-9-27 by loren
 */
class CopyWxActivity : AppCompatActivity() {

    private var isFinish = true
    private var mContext: Context? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.copy_wx_activity)
        mContext = this
        shoot_button!!.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action === MotionEvent.ACTION_DOWN) {
                movieRecorderView!!.record { handler.sendEmptyMessage(1) }
            } else if (motionEvent.action === MotionEvent.ACTION_UP) {
                if (movieRecorderView!!.timeCount > 1)
                    handler.sendEmptyMessage(1)
                else {
                    if (movieRecorderView!!.getmVecordFile() != null)
                        movieRecorderView!!.getmVecordFile().delete()
                    movieRecorderView!!.stop()
                    Toast.makeText(mContext, "视频录制时间太短", Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnTouchListener true
        }
    }

    override fun onResume() {
        super.onResume()
        isFinish = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        isFinish = false
        movieRecorderView!!.stop()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            finishActivity()
        }
    }

    private fun finishActivity() {
        if (isFinish) {
            movieRecorderView!!.stop()
//            startActivity(this, movieRecorderView!!.getmVecordFile().toString())
            Log.d("loren", "path=" + movieRecorderView!!.getmVecordFile().toString())
        }
    }

    /**
     * 录制完成回调

     * @author liuyinjun
     * *
     * *
     * @date 2015-2-9
     */
    interface OnShootCompletionListener {
        fun OnShootSuccess(path: String, second: Int)
        fun OnShootFailure()
    }
}