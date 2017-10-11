package com.example.loren.minesample

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.flag_activity.*
import java.util.*

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
 *                Copyright (c) 16-9-19 by loren
 */
class FlagActivity : AppCompatActivity() {

    private lateinit var mThread: Thread
    private var randomArr = arrayOf(1000, 1500, 1800, 2000, 2200)
    private var songArr = arrayOf("预备～", "唱！", "五星红旗迎风飘扬～", "胜利歌声多么响亮～", "歌唱我们亲爱的祖国～", "从今走向繁荣富强！", "不要停", "再来一遍！")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flag_activity)
        mThread = Thread { AutoCycleStr() }
        mThread.start()
    }

    private fun AutoCycleStr() {
        while (true) {
            songArr.forEachIndexed { i, s ->
                SystemClock.sleep(randomArr[Random().nextInt(randomArr.size - 1)].toLong())
                runOnUiThread { htv.animateText(s) }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mThread.interrupt()
    }
}