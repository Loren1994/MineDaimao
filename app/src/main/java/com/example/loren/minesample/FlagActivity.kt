package com.example.loren.minesample

import android.view.View
import com.example.loren.minesample.base.ext.log
import com.example.loren.minesample.base.ui.BaseActivity
import kotlinx.android.synthetic.main.flag_activity.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI
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
class FlagActivity : BaseActivity() {

    private var randomArr = arrayOf(1000, 1500, 1800, 2000, 2200)
    private var songArr = arrayOf("预备～", "唱！", "五星红旗迎风飘扬～", "胜利歌声多么响亮～", "歌唱我们亲爱的祖国～", "从今走向繁荣富强！", "不要停", "再来一遍！")
    //    private lateinit var mThread: Thread
    lateinit var cycle: Job

    override fun initWidgets() {
//        mThread = Thread { AutoCycleStr() }
//        mThread.start()
        cycle = launch(UI) {
            async {
                autoCycleStr()
            }.await()
        }
    }

    private suspend fun autoCycleStr() {
        while (true) {
            log(">>>AutoCycleStr")
            songArr.forEachIndexed { _, s ->
                delay(randomArr[Random().nextInt(randomArr.size - 1)].toLong())
                runOnUiThread { htv.animateText(s) }
            }
            yield()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        mThread.interrupt()
        cycle.cancel()
    }

    override fun setListeners() {
    }

    override fun onWidgetsClick(v: View) {
    }

    override fun bindLayout() = R.layout.flag_activity

}