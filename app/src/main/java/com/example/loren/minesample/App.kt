package com.example.loren.minesample

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard

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
 *                Copyright (c) 16-9-26 by loren
 */
class App : Application() {
    companion object {
        lateinit var videoPlayer: JCVideoPlayerStandard
    }

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        videoPlayer=JCVideoPlayerStandard(this)
    }
}