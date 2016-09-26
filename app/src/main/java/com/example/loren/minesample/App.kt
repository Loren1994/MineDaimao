package com.example.loren.minesample

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.videogo.constant.Config
import com.videogo.openapi.EZOpenSDK

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

    private val EZ_APP_KEY = "8534bc02e8414a31afa9ac3ec6715e2d"
    private val EZ_APP_SECRET = "06e37a49817cd209c537f59eb4585a75"

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        Config.LOGGING = true
        EZOpenSDK.initLib(this, EZ_APP_KEY, "")
    }
}